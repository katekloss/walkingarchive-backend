ALTER TABLE DeckCards DROP COLUMN count;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (4, '004_alter_table_deckcards_drop_column_count.sql', current_user, current_timestamp);