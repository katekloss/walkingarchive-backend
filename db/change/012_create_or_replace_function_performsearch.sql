CREATE OR REPLACE FUNCTION PerformSearch(query text)
    RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT cardid, ts_rank_cd(textvector, to_tsquery(query)) AS rank
FROM CardVectors
WHERE to_tsquery(query) @@ textvector
ORDER BY rank DESC
$$
LANGUAGE SQL;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (12, '012_create_or_replace_function_performsearch.sql', current_user, current_timestamp);
