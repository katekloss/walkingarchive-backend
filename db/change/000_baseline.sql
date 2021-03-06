CREATE EXTENSION hstore;

CREATE TYPE manacolor AS ENUM('white', 'blue', 'black', 'red', 'green', 'colorless',
	'whiteblue', 'whiteblack', 'blueblack', 'bluered', 'blackred',
	'blackgreen', 'redgreen', 'redwhite', 'greenwhite', 'greenblue',
	'2white', '2blue', '2black', '2red', '2green');
ALTER TYPE manacolor OWNER TO walkingarchive;

CREATE TYPE type AS ENUM('artifact', 'creature', 'enchantment', 'instant', 'interrupt',
	'land', 'mana source', 'phenomenon', 'plane', 'planeswalker', 'scheme', 'sorcery',
	'summon', 'tribal', 'vanguard');
ALTER TYPE type OWNER TO walkingarchive;

CREATE TYPE tradedirection AS ENUM('giving', 'receiving');
ALTER TYPE tradedirection OWNER TO walkingarchive;

CREATE TABLE MetaSchemaHistory (
	Version integer NOT NULL,
	Name text NOT NULL,
	Role text NOT NULL,
	Date timestamp with time zone NOT NULL,

	PRIMARY KEY (Version)
);
ALTER TABLE MetaSchemaHistory OWNER TO walkingarchive;

CREATE TABLE Sets (
	setid serial NOT NULL,
	setname character varying(50),

	PRIMARY KEY (setid)
);
ALTER TABLE Sets OWNER TO walkingarchive;

CREATE TABLE Cards (
	cardid serial NOT NULL,
	name character varying(150) NOT NULL,
	mana hstore NOT NULL,
	type type NOT NULL,
	subtype character varying(40),
	cardtext text,
	flavortext text,
	extid integer NOT NULL,

	PRIMARY KEY (cardid)
);
ALTER TABLE Cards OWNER TO walkingarchive;

CREATE TABLE CardSets (
	cardid integer NOT NULL,
	setid integer NOT NULL,

	PRIMARY KEY (cardid, setid),
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE,
	FOREIGN KEY (setid) REFERENCES Sets ON DELETE CASCADE
);
ALTER TABLE CardSets OWNER TO walkingarchive;

CREATE TABLE CardVectors (
	cardid integer NOT NULL,
	textvector tsvector,

	PRIMARY KEY (cardid),
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);
ALTER TABLE CardVectors OWNER TO walkingarchive;

CREATE TABLE Users (
	userid serial NOT NULL,
	name character varying(30) NOT NULL,
	email character varying(40) NOT NULL,
	password character(60) NOT NULL,

	PRIMARY KEY (userid)
);
ALTER TABLE Users OWNER TO walkingarchive;

CREATE TABLE Decks (
	deckid serial NOT NULL,
	userid integer NOT NULL,
	deckname character varying(60) NOT NULL,

	PRIMARY KEY (deckid),
	FOREIGN KEY (userid) REFERENCES Users ON DELETE CASCADE
);
ALTER TABLE Decks OWNER TO walkingarchive;

CREATE TABLE DeckCards (
	deckid integer NOT NULL,
	cardid integer NOT NULL,
	count smallint NOT NULL,

	PRIMARY KEY (deckid, cardid),
	FOREIGN KEY (deckid) REFERENCES Decks ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);
ALTER TABLE DeckCards OWNER TO walkingarchive;

CREATE TABLE Trades (
	tradeid serial NOT NULL,
	tradedate date NOT NULL,
	userid integer NOT NULL,
	active boolean NOT NULL,

	PRIMARY KEY (tradeid),
	FOREIGN KEY (userid) REFERENCES Users ON DELETE CASCADE
);
ALTER TABLE Trades OWNER TO walkingarchive;

CREATE TABLE TradeCards (
	tradeid integer NOT NULL,
	cardid integer NOT NULL,
	count smallint NOT NULL,
	direction tradedirection NOT NULL,

	PRIMARY KEY (tradeid, cardid),
	FOREIGN KEY (tradeid) REFERENCES Trades ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);
ALTER TABLE TradeCards OWNER TO walkingarchive;

CREATE VIEW RawDictionary (word, count) AS
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
ALTER VIEW RawDictionary OWNER TO walkingarchive;

CREATE VIEW TokenDictionary (token, count) AS
SELECT word AS token, nentry AS count
FROM ts_stat('SELECT textvector FROM CardVectors')
ORDER BY count DESC;
ALTER VIEW TokenDictionary OWNER TO walkingarchive;

CREATE INDEX idx_cardvectors_textvector ON CardVectors USING gin(textvector);

CREATE FUNCTION PerformSearch(query text)
	RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT cardid, ts_rank_cd(textvector, plainto_tsquery(query)) AS rank
FROM CardVectors
WHERE plainto_tsquery(query) @@ textvector
ORDER BY rank DESC
$$
LANGUAGE SQL;
ALTER FUNCTION PerformSearch(text) OWNER TO walkingarchive;

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
ALTER FUNCTION BuildCardVector() OWNER TO walkingarchive;

CREATE TRIGGER tr_cards_update_cardvectors
	AFTER INSERT OR UPDATE OF name, type, subtype, cardtext, flavortext
	ON Cards
	FOR EACH ROW
	EXECUTE PROCEDURE BuildCardVector();

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (0, '000_baseline.sql', current_user, current_timestamp);