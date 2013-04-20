ALTER TABLE TradeCardsGiving DROP CONSTRAINT tradecardsgiving_pkey;
ALTER TABLE TradeCardsReceiving DROP CONSTRAINT tradecardsreceiving_pkey;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (10, '010_alter_table_tradecardsgiving_drop_constraint.sql', current_user, current_timestamp);
