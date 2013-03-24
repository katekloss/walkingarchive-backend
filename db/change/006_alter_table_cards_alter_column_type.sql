DROP TRIGGER tr_cards_update_cardvectors ON Cards;
DROP VIEW RawDictionary;

ALTER TABLE Cards ALTER COLUMN type TYPE character varying (12);

CREATE TRIGGER tr_cards_update_cardvectors
	AFTER INSERT OR UPDATE OF name, type, subtype, cardtext, flavortext
	ON Cards
	FOR EACH ROW
	EXECUTE PROCEDURE BuildCardVector();

CREATE VIEW RawDictionary (word, count) AS
SELECT word, count FROM
(
	SELECT regexp_split_to_table(
		lower(
			concat_ws(' ', name, type, subtype, cardtext, flavortext)
		), E'[^a-zA-Z]+'
	) AS word,
	COUNT(*) AS count
	FROM Cards
	GROUP BY word
	ORDER BY count DESC
) AS X
WHERE word != '';
ALTER VIEW RawDictionary OWNER TO walkingarchive;

INSERT INTO MetaSchemaHistory (Version, Name, Role, Date) VALUES (6, '006_alter_table_cards_alter_column_type.sql', current_user, current_timestamp);