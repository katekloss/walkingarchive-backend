CREATE INDEX idx_cards_name ON Cards (name);
CREATE INDEX idx_cards_type ON Cards (type);

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (8, '008_create_index_idx_cards_name.sql', current_user, current_timestamp);