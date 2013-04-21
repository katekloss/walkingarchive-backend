CREATE EXTENSION pg_trgm;

CREATE TABLE RawDictionaryMaterialized (
    word text,
    count bigint
);
ALTER TABLE RawDictionaryMaterialized OWNER TO walkingarchive;

CREATE INDEX idx_rawdictionarymaterialized_word ON RawDictionaryMaterialized USING gin(word gin_trgm_ops);

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (11, '011_create_extension_pg_trgm.sql', current_user, current_timestamp);
