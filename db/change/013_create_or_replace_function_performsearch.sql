DROP FUNCTION PerformSearch(text);

CREATE OR REPLACE FUNCTION PerformSearch(query text)
        RETURNS TABLE(cardid integer, rank integer) AS
$$
SELECT cardid, ts_rank_intersect(strip(textvector)::text, querytree(to_tsquery(query))) AS rank
FROM CardVectors
WHERE to_tsquery(query) @@ textvector
ORDER BY rank DESC
$$
LANGUAGE SQL;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (13, '013_create_or_replace_function_performsearch.sql', current_user, current_timestamp);
