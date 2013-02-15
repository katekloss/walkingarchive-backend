#!/usr/bin/env python
import re, sys, time, urllib, urllib2
from bs4 import BeautifulSoup

# HTML element matchers
is_type_node = lambda tag: tag.string == "Type"
is_rules_node = lambda tag: tag.string == "Rules"
is_flavor_node = lambda tag: tag.string == "Flavor"
is_description_node = lambda tag: tag.has_key("name") and tag["name"] == "description"
is_rulings_node = lambda tag: tag.string == "Rulings"

# Mappings between DeckBox mana types and more verbose ones
manaConversions = {
	"W": "white",
	"U": "blue",
	"B": "black",
	"R": "red",
	"G": "green",
	"WU": "whiteblue",
	"WB": "whiteblack",
	"UB": "blueblack",
	"UR": "bluered",
	"BR": "blackred",
	"BG": "blackgreen",
	"RG": "redgreen",
	"RW": "redwhite",
	"GW": "greenwhite",
	"GU": "greenblue",
	"X": "colorless",
	"2W": "2white",
	"2U": "2blue",
	"2B": "2black",
	"2R": "2red",
	"2G": "2green"
}

def convertMana(string):
	"""
	Turns a DeckBox mana string like "RW" into its human-readable equivalent
	such as "redwhite". This converts variable mana cost to a fixed cost of 1.
	"""

	try:
		return (1, manaConversions[string],)
	except KeyError:
		pass
	try:
		count = int(string)
		return (count, "colorless")
	except ValueError:
		raise

def parseCardPage(url):
	"""
	Downloads the given URL and parses it into a dictionary of card metadata.
	Dictionary keys are as follows:

	name - The name of the card
	type - Abstract card type (e.g. "Enchantment" or "Creature")
	subtype - Specific card type (e.g. "Aura" or "Zombie")
	text - Full rules/usage text of the card
	flavortext - Writers' flavor text on the card (the italicized part)
		or None if there is no flavor text
	editions - A list of the names of every edition that this card appears in.
		There are no duplicates for cards reprinted with different
		photos in the same edition.
	mana - A dictionary containing the mana cost of the card. A card requiring two
		black mana and 3 colorless mana would appear as {'black': 2, 'colorless': 3}.
		Variable mana cost is converted to 1 colorless mana.
	extid - The unique ID of the card on WOTC's Gatherer tool
	"""

	ret = {}
	html = urllib2.urlopen(url).read()
	soup = BeautifulSoup(html)
	ret["name"] = soup.find(id="card_image")["alt"]
	properties = soup.find(class_="card_properties")

	# Convert a type like "Enchantment - Aura" into a type of "Enchantment"
	# and a subtype of "Aura"
	full_type = properties.find(is_type_node).next_sibling.string.split('-')
	ret["type"] = full_type[0].strip()
	try:
		ret["subtype"] = full_type[1].strip()
	except IndexError:
		ret["subtype"] = None

	# Join all of the non-flavor text on the card
	ret["text"] = ' '.join(
		[x.string for x in properties.find(is_rules_node).next_sibling.strings]
		)

	try:
		ret["flavortext"] = properties.find(is_flavor_node).next_sibling.string
	except AttributeError:
		ret["flavortext"] = None

	# Create a unique list of all editions this card appears in
	ret["editions"] = set()
	for edition in properties.findAll(class_="edition_price"):
		ret["editions"].add(edition.img["data-title"])

	# Transforms a mana string like "{1}{B}" into 1 colorless mana and 1 black mana
	ret["mana"] = {}
	mana_text = soup.find(is_description_node)["content"].split(';')[1]
	for mana in re.findall('\{(.+?)\}', mana_text):
		(count, color) = convertMana(mana)
		if color in ret["mana"]:
			ret["mana"][color] += count
		else:
			ret["mana"][color] = count
	
	try:
		gatherer_url = soup.find(is_rulings_node).next_sibling.next_sibling.a["href"]
		ret["extid"] = re.search("multiverseid=(\d+)", gatherer_url).group(1)
	except AttributeError:
		ret["extid"] = None

	return ret

def main(start_page = 1):
	for i in range(int(start_page), 445):
		html = urllib2.urlopen("http://deckbox.org/games/mtg/cards?p=%i" % i).read()
		soup = BeautifulSoup(html)
		for card_url in [x.find('a')['href'] for x in soup.find(class_="set_cards").findAll("td", class_="card_name")]:
			print parseCardPage(urllib.quote(card_url, ':/'))
			time.sleep(5)
	print parseCardPage(url)

if __name__ == "__main__":
	if(len(sys.argv) == 2):
		main(sys.argv[1])
	else:
		main()