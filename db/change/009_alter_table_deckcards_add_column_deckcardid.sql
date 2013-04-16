ALTER TABLE DeckCards ADD COLUMN deckcardid SERIAL NOT NULL;
ALTER TABLE DeckCards DROP CONSTRAINT deckcards_pkey;
ALTER TABLE DeckCards ADD PRIMARY KEY (deckcardid);

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (9, '009_alter_table_deckcards_add_column_deckcardid.sql', current_user, current_timestamp);
