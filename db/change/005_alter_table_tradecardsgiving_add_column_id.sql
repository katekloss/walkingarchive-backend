ALTER TABLE TradeCardsGiving ADD COLUMN id SERIAL NOT NULL;
ALTER TABLE TradeCardsReceiving ADD COLUMN id SERIAL NOT NULL;

ALTER TABLE TradeCardsGiving ADD PRIMARY KEY (id);
ALTER TABLE TradeCardsReceiving ADD PRIMARY KEY (id);

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (5, '005_alter_table_tradecardsgiving_add_column_id.sql', current_user, current_timestamp);