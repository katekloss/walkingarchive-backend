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

---
--- Tables
----

CREATE TABLE Sets (
	setid serial NOT NULL,
	setname character varying(50),

	PRIMARY KEY (setid)
);

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

CREATE TABLE CardSets (
	cardid integer NOT NULL,
	setid integer NOT NULL,

	PRIMARY KEY (cardid, setid),
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE,
	FOREIGN KEY (setid) REFERENCES Sets ON DELETE CASCADE
);