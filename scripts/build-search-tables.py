#!/usr/bin/env python
import psycopg2

def main():

	conn = psycopg2.connect("dbname=walkingarchive user=walkingarchive password=walkingarchive")
	cursor = conn.cursor()

	query = """
INSERT INTO CardVectors
SELECT cardid, to_tsvector(cardtext) AS vector
FROM Cards;"""
	cursor.execute(query)

if __name__ == "__main__":
	main()