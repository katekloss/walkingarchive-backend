---
--- Extensions
---
CREATE EXTENSION hstore;

---
--- Types
---

CREATE TYPE manacolor AS ENUM('white', 'blue', 'black', 'red', 'green', 'colorless',
	'whiteblue', 'whiteblack', 'blueblack', 'bluered', 'blackred',
	'blackgreen', 'redgreen', 'redwhite', 'greenwhite', 'greenblue',
	'2white', '2blue', '2black', '2red', '2green');

CREATE TYPE type AS ENUM('artifact', 'creature', 'enchantment', 'instant', 'interrupt',
	'land', 'mana source', 'phenomenon', 'plane', 'planeswalker', 'scheme', 'sorcery',
	'summon', 'tribal', 'vanguard');

CREATE TYPE tradedirection AS ENUM('giving', 'receiving');

---
--- Tables
----

CREATE TABLE MetaSchemaHistory (
	Version integer NOT NULL,
	Name text NOT NULL,
	Role text NOT NULL,
	Date timestamp with time zone NOT NULL,

	PRIMARY KEY (Version)
);

CREATE TABLE Sets (
	setid serial NOT NULL,
	setname character varying(50),

	PRIMARY KEY (setid)
);

CREATE TABLE Cards (
	cardid serial NOT NULL,
	name character varying(150) NOT NULL,
	mana hstore NOT NULL,
	type character varying(12) NOT NULL,
	subtype character varying(40),
	cardtext text,
	flavortext text,
	extid integer NOT NULL,

	PRIMARY KEY (cardid)
);

CREATE TABLE CardSets (
	cardid integer NOT NULL,
	setid integer NOT NULL,

	PRIMARY KEY (cardid, setid),
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE,
	FOREIGN KEY (setid) REFERENCES Sets ON DELETE CASCADE
);

CREATE TABLE CardVectors (
	cardid integer NOT NULL,
	textvector tsvector,

	PRIMARY KEY (cardid),
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);

CREATE TABLE Users (
	userid serial NOT NULL,
	name character varying(30) NOT NULL,
	email character varying(40) NOT NULL,
	password character(60) NOT NULL,

	PRIMARY KEY (userid)
);

CREATE TABLE Decks (
	deckid serial NOT NULL,
	userid integer NOT NULL,
	deckname character varying(60) NOT NULL,

	PRIMARY KEY (deckid),
	FOREIGN KEY (userid) REFERENCES Users ON DELETE CASCADE
);

CREATE TABLE DeckCards (
	deckcardid serial NOT NULL,
	deckid integer NOT NULL,
	cardid integer NOT NULL,

	PRIMARY KEY (deckcardid),
	FOREIGN KEY (deckid) REFERENCES Decks ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);

CREATE TABLE Trades (
	tradeid serial NOT NULL,
	tradedate date NOT NULL,
	userid integer NOT NULL,
	active boolean NOT NULL,

	PRIMARY KEY (tradeid),
	FOREIGN KEY (userid) REFERENCES Users ON DELETE CASCADE
);

CREATE TABLE TradeCardsGiving (
	id integer NOT NULL,
	tradeid integer NOT NULL,
	cardid integer NOT NULL,

	FOREIGN KEY (tradeid) REFERENCES Trades ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);

CREATE TABLE TradeCardsReceiving (
	id integer NOT NULL,
	tradeid integer NOT NULL,
	cardid integer NOT NULL,

	FOREIGN KEY (tradeid) REFERENCES Trades ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);

CREATE TABLE CardPrices (
	cardid integer NOT NULL,
	priceid integer NOT NULL,

	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE,
	FOREIGN KEY (priceid) REFERENCES Prices ON DELETE CASCADE
);

CREATE TABLE Prices (
	priceid serial NOT NULL,
	setid integer NOT NULL,
	price money NOT NULL,
	source text,
	date date NOT NULL,

	PRIMARY KEY (priceid),
	FOREIGN KEY (setid) REFERENCES Sets ON DELETE CASCADE
);

---
--- Views
---

CREATE OR REPLACE VIEW RawDictionary (word, count) AS
SELECT word, count FROM
(
	SELECT regexp_split_to_table(
		lower(
			concat_ws(' ', name, type, subtype, cardtext, flavortext)
		), E'[^a-zA-Z]+'
	) AS word,
	COUNT(*) AS count
	FROM Cards
	GROUP BY word
	ORDER BY count DESC
) AS X
WHERE word != '';

CREATE OR REPLACE VIEW TokenDictionary (token, count) AS
SELECT word AS token, nentry AS count
FROM ts_stat('SELECT textvector FROM CardVectors')
ORDER BY count DESC;

---
--- Indexes
---

CREATE INDEX idx_cardvectors_textvector ON CardVectors USING gin(textvector);
CREATE INDEX idx_cards_name ON Cards (name);
CREATE INDEX idx_cards_type ON Cards (type);

---
--- Functions
---

CREATE OR REPLACE FUNCTION PerformSearch(query text)
	RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT cardid, ts_rank_cd(textvector, plainto_tsquery(query)) AS rank
FROM CardVectors
WHERE plainto_tsquery(query) @@ textvector
ORDER BY rank DESC
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION BuildCardVector()
	RETURNS TRIGGER AS
$$
	DECLARE
		doc text;
	BEGIN
		doc := concat_ws(' ', NEW.name, NEW.type, NEW.subtype, NEW.cardtext, NEW.flavortext);
		UPDATE CardVectors SET textvector = to_tsvector(doc) WHERE cardid = NEW.cardid;
		IF NOT FOUND THEN
			INSERT INTO CardVectors (cardid, textvector) VALUES (NEW.cardid, to_tsvector(doc));
		END IF;
		RETURN NEW;
	END;
$$
LANGUAGE PLPGSQL;

---
--- Triggers
---

CREATE TRIGGER tr_cards_update_cardvectors
	AFTER INSERT OR UPDATE OF name, type, subtype, cardtext, flavortext
	ON Cards
	FOR EACH ROW
	EXECUTE PROCEDURE BuildCardVector();
