DROP FUNCTION PerformSearch(text);

CREATE OR REPLACE FUNCTION PerformSearch(query text)
    RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT cardid, ts_rank_intersect(strip(textvector)::text, strip(to_tsquery(query))::text) AS rank
FROM CardVectors
WHERE to_tsquery(query) @@ textvector
ORDER BY rank DESC
$$
LANGUAGE SQL;

CREATE TABLE TokenDictionaryMaterialized (
    token text,
    count integer
);
ALTER TABLE TokenDictionaryMaterialized OWNER TO walkingarchive;

CREATE INDEX idx_tokendictionarymaterialized_token ON TokenDictionaryMaterialized (token);

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (14, '014_create_or_replace_function_performsearch.sql', current_user, current_timestamp);
