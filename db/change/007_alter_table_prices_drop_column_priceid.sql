ALTER TABLE Prices DROP COLUMN cardid;
ALTER TABLE Prices ADD COLUMN priceid serial NOT NULL;
ALTER TABLE Prices ADD PRIMARY KEY (priceid);

CREATE TABLE CardPrices (
	cardid integer NOT NULL,
	priceid integer NOT NULL,

	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE,
	FOREIGN KEY (priceid) REFERENCES Prices ON DELETE CASCADE
);
ALTER TABLE CardPrices OWNER TO walkingarchive;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (7, '007_alter_table_prices_drop_column_priceid.sql', current_user, current_timestamp);