CREATE TABLE Prices (
	cardid integer NOT NULL,
	setid integer NOT NULL,
	price money NOT NULL,
	source text,

	PRIMARY KEY (cardid, setid),
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE,
	FOREIGN KEY (setid) REFERENCES Sets ON DELETE CASCADE
);
ALTER TABLE Prices OWNER TO walkingarchive;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (1, '001_create_table_prices.sql', current_user, current_timestamp);