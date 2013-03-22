ALTER TABLE Prices ADD COLUMN date date NOT NULL;
ALTER TABLE Prices DROP CONSTRAINT prices_pkey;
ALTER TABLE Prices ADD PRIMARY KEY (cardid, setid, date);

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (2, '002_alter_table_prices_add_column_date.sql', current_user, current_timestamp);