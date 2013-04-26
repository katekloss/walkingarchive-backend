CREATE OR REPLACE FUNCTION PerformSearch(query text, count integer)
    RETURNS TABLE(cardid integer, rank real) AS
$$
SELECT C.cardid, (ts_rank_idf(strip(textvector)::text, strip(to_tsvector(query))::text) * X.rank)::real AS rank
FROM CardVectors AS C
JOIN
(
    SELECT cardid, ts_rank_intersect(strip(textvector)::text, strip(to_tsvector(query))::text) AS rank
    FROM CardVectors
    ORDER BY rank DESC
    LIMIT count
) AS X ON X.cardid = C.cardid
ORDER BY rank DESC
$$
LANGUAGE SQL;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (17, '017_create_function_performsearch.sql', current_user, current_timestamp);
