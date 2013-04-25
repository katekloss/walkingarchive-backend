--
-- Rebuild IDF table
--
TRUNCATE IDF;
INSERT INTO IDF
SELECT token, (count::real / 14925) AS idf FROM TokenDictionary;
