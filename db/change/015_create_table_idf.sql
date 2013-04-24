CREATE TABLE IDF (
    token text,
    idf real
);
ALTER TABLE IDF OWNER TO walkingarchive;
CREATE INDEX IDF_token ON IDF (token);

CREATE OR REPLACE FUNCTION PerformSearch(query text)
    RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT cardid, ts_rank_intersect(strip(textvector)::text, strip(to_tsvector(query))::text) AS rank
FROM CardVectors
WHERE to_tsquery(query) @@ textvector
ORDER BY rank DESC
$$
LANGUAGE SQL;

DROP TABLE TokenDictionaryMaterialized;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (15, '015_create_table_idf.sql', current_user, current_timestamp);
