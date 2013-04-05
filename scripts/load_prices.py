#!/usr/bin/env python
import psycopg2, sys, time, urllib2
from bs4 import BeautifulSoup

def main(start = 1):

	conn = psycopg2.connect("host=dev.mtgwalkingarchive.com sslmode=require dbname=walkingarchive-dev user=walkingarchive password=walkingarchive")
	cursor = conn.cursor()
	query = "SELECT cardid, name FROM Cards WHERE cardid >= %(cardid)s ORDER BY cardid"
	parameters = {
		'cardid': start
	}
	cursor.execute(query, parameters)

	for row in cursor:
		url = "http://deckbox.org/mtg/%s" % row[1]
		html = urllib2.urlopen(url).read()
		soup = BeautifulSoup(html)
		editions = soup.findAll(class_="edition_price", style="display:none")
		prices = {}
		for edition in editions:
			name = edition.img["data-title"].strip()
			price = edition.find(class_="tcgplayer_price_avg").string
			prices[name] = price
		cursor2 = conn.cursor()
		for name in prices:
			query = "SELECT setid FROM Sets WHERE setname = %(name)s"
			parameters = {
				'name': name
			}
			cursor2.execute(query, parameters)
			try:
				setid = cursor2.fetchone()[0]
			except TypeError:
				print "WARNING: Set '%s' does not exist (found on card '%s') in the database but exists in DeckBox (database is out of date)." \
					% (name, row[1])
				continue
			query = "INSERT INTO Prices (setid, price, source, date) VALUES (%(setid)s, %(price)s, %(source)s, CURRENT_TIMESTAMP) RETURNING priceid"
			parameters = {
				'setid': setid,
				'price': prices[name],
				'source': url
			}
			cursor2.execute(query, parameters)
			priceid = cursor2.fetchone()[0]
			query = "INSERT INTO CardPrices (cardid, priceid) VALUES (%(cardid)s, %(priceid)s)"
			parameters = {
				'cardid': row[0],
				'priceid': priceid
			}
			cursor2.execute(query, parameters)
			conn.commit()

			print "Updated price for '%s' set '%s' to %s (cardid = %i)" % (row[1], name, prices[name], row[0])
		time.sleep(5)

if __name__ == "__main__":
	if(len(sys.argv) == 2):
		main(sys.argv[1])
	else:
		main()