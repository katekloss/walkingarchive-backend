DROP TABLE TradeCards CASCADE;

CREATE TABLE TradeCardsGiving (
	tradeid integer NOT NULL,
	cardid integer NOT NULL,

	FOREIGN KEY (tradeid) REFERENCES Trades ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);
ALTER TABLE TradeCardsGiving OWNER TO walkingarchive;

CREATE TABLE TradeCardsReceiving (
	tradeid integer NOT NULL,
	cardid integer NOT NULL,

	FOREIGN KEY (tradeid) REFERENCES Trades ON DELETE CASCADE,
	FOREIGN KEY (cardid) REFERENCES Cards ON DELETE CASCADE
);
ALTER TABLE TradeCardsReceiving OWNER TO walkingarchive;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (3, '003_drop_table_tradecards.sql', current_user, current_timestamp);
