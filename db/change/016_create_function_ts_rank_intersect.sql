DROP TABLE IDF;
CREATE TABLE TokenDictionaryMaterialized (
    token text,
    count integer
);
ALTER TABLE TokenDictionaryMaterialized OWNER TO walkingarchive;
CREATE INDEX idx_tokendictionarymaterialized_token ON TokenDictionaryMaterialized (token);

DROP FUNCTION IF EXISTS ts_rank_intersect(text, text);
CREATE FUNCTION ts_rank_intersect(text, text) RETURNS integer AS '/usr/lib64/pgsql/ts_rank_intersect.so', 'ts_rank_intersect' LANGUAGE C STRICT;
CREATE FUNCTION ts_rank_idf(text, text) RETURNS real AS '/usr/lib64/pgsql/ts_rank_intersect.so', 'ts_rank_idf' LANGUAGE C STRICT;

CREATE OR REPLACE FUNCTION PerformSearch(query text, count integer)
    RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT C.cardid, (ts_rank_idf(strip(textvector)::text, strip(to_tsvector(query))::text) * X.rank)::real AS rank
FROM CardVectors AS C
JOIN
(
    SELECT cardid, ts_rank_intersect(strip(textvector)::text, strip(to_tsvector(query))::text) AS rank
    FROM CardVectors
    WHERE to_tsquery(query) @@ textvector
    ORDER BY rank DESC
    LIMIT count
) AS X ON X.cardid = C.cardid
ORDER BY rank DESC
$$
LANGUAGE SQL;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (16, '016_create_function_ts_rank_intersect.sql', current_user, current_timestamp);
