from main.AirBnBStay import AirBnBStay
from main.WebScraper import WebScraper
from bs4 import BeautifulSoup as soup
import re


class AirBnBScraper(WebScraper):
    """
    A scraper for AirBnB data. Specifically, retrieves the first page of a queried stays and filters results.

    author: Joshua Nathan Mugerwa
    """

    def __init__(self):
        """
        Initializes the scraper with an empty set of filters.
        """
        super().__init__()
        self.filter = {}
        """ dict: A map of filters to apply to the queried stays."""
        self.date_regex = re.compile(".{4}-.{2}-.{2}")
        """ regex: A regex mask for date inputs"""

    def scrape(self, max_price, checkin, checkout, location, num_rooms):
        """
        Scrapes AirBnB.com for stays with the given parameters then filters the results using the scrapers filter map.
        :param max_price: float
            The maximum price-per-night of a stay (added to filter then used to filter, after query)
        :param checkin: string
            Check-in date
        :param checkout: string
            Check-out date
        :param location: tuple(string)
            Location of stay; **the form is (city, state) and must be in USA**
        :param num_rooms: int
            Number of rooms required (specifically, the number of adults who will stay)
        :return: A set of stays which match the query and filter params
        """

        try:
            max_price, num_rooms = float(max_price), int(num_rooms)
        except ValueError:
            print("ERROR: The max price and/or number of rooms passed in were not valid. Please try again.")
            return None

        if not self.params_are_valid(max_price, checkin, checkout, location, num_rooms):
            # Error message will be printed in method, no need to print here -- just return None.
            return None

        self.filter["max_price"] = max_price  # Adding filter

        url = self.build_search_url(checkin, checkout, location, num_rooms)
        response = super().get(url)  # Get first 20 stay options (number of stays on first page)
        page_soup = soup(response, "html.parser")
        all_stays_div = page_soup.find("div", {"class": "_fhph4u"}).contents  # Grab all divs in HTML body

        if not all_stays_div:
            print("No AirBnB's matched the given preferences.")
            return None

        stays = []
        for stay_div in all_stays_div:
            try:
                booking_url = self.get_booking_url(stay_div)
            except AttributeError:
                booking_url = None
            try:
                photo_url = self.get_photo_url(stay_div)
            except AttributeError:
                photo_url = None
            try:
                description = self.get_description(stay_div)
            except AttributeError:
                description = None
            try:
                price = self.get_price(stay_div)
            except AttributeError:
                price = None
            try:
                rating = self.get_rating(stay_div)
            except AttributeError:
                rating = None

            stays.append(AirBnBStay(booking_url, photo_url, description, price, rating))  # Add stay to query results

        stays = self.filter_stays(stays)  # Filter queried results
        return stays

    def params_are_valid(self, max_price, checkin, checkout, location, num_rooms):
        """
        Validates scraping parameters.
        :param max_price: float
            The maximum price-per-night of a stay (added to filter then used to filter, after query)
        :param checkin: string
            Check-in date
        :param checkout: string
            Check-out date
        :param location: tuple(string)
            Location of stay; **the form is (city, state) and must be in USA**
        :param num_rooms: int
            Number of rooms required (specifically, the number of adults who will stay)
        :return: True iff all parameters are valid
        """
        if max_price <= 0:
            print("ERROR: Invalid max price.")
            return False
        if type(location) != tuple or len(location) != 2:
            print("ERROR: The location must be a tuple of size 2.")
            return False
        if num_rooms <= 0:
            print("ERROR: The number of rooms must be a positive, non-zero integer.")
            return False
        if not (self.date_regex.match(checkin) and len(checkin) == 10 and self.date_regex.match(checkout)
                and len(checkout) == 10):
            return False
        return True

    def build_search_url(self, checkin, checkout, location, num_rooms):
        """
        Builds the URL that we will scrape from.
        :param checkin: string
            Check-in date
        :param checkout: string
            Check-out date
        :param location: tuple(string)
            Location of stay; **the form is (city, state) and must be in USA**
        :param num_rooms: string
            Number of rooms required (specifically, the number of adults who will stay)
        :return:
        """
        if type(num_rooms) != str:
            num_rooms = str(num_rooms)
        city, state = location
        base = "https://www.airbnb.com/s/homes?tab_id=all_tab&refinement_paths%5B%5D=%2Fhomes&query="
        query = f"{city}%2C%20{state}&checkin={checkin}&checkout={checkout}&adults={num_rooms}&source=structured_search_input_header&search_type=search_query"
        return base + query

    def filter_stays(self, stays):
        """
        Filters queried stays via the scraper's filter map (here, we simply filter based on max price).
        :param stays: list[AirBnBStay]
            The set of scraped stays
        :return: filtered_stays: list[AirBnBStay]
            The set of scraped stays, filtered
        """
        filtered_stays = []
        for filter in self.filter:
            if filter == "max_price":
                for stay in stays:
                    if stay._price:
                        stay_price = float(stay._price)
                        if stay_price <= self.filter[filter]:
                            filtered_stays.append(stay)
        return filtered_stays

    def get_booking_url(self, stay_div):
        """
        Retrieves booking URL from scraped page.
        :param stay_div: List[BeautifulSoup Div]
            The stay's list of div's
        :return: url: string
            The stay's booking URL
        """
        all_divs = stay_div.find("div", {"class": "_gig1e7"}).find("div", itemprop="itemListElement")
        url_div = all_divs.find("meta", itemprop="url")
        url = "airbnb.com" + url_div["content"].replace("undefined", "")
        return url

    def get_photo_url(self, stay_div):
        """
        Retrieves photo URL from the scraped page.
        :param stay_div: List[BeautifulSoup Div]
            The stay's list of div's
        :return: parsed_photo_url: string
            The stay's photo URL
        """
        all_divs = stay_div.find("div", {"class": "_gig1e7"}).find("div", itemprop="itemListElement")
        child = all_divs.find("div", {"class": "_1nz9l7j"}).find("div", {"class": "_2n7voam"}) \
            .find("div", {"class": "_gjw2an"}).find("div", {"class": "_10xjrv2u"})
        s = child['style']
        parsed_photo_url = s[s.find('(') + 1: s.find(')')]
        return parsed_photo_url

    def get_description(self, stay_div):
        """
        Retrieves description from the scraped page.
        :param stay_div: List[BeautifulSoup Div]
            The stay's list of div's
        :return: parsed_description: string
            The stay's description
        """
        all_divs = stay_div.find("div", {"class": "_gig1e7"}).find("div", itemprop="itemListElement")
        description_div = all_divs.find("meta", itemprop="name")
        unparsed_description = description_div["content"]
        parsed_description = unparsed_description[:unparsed_description.find('null') - 2]
        return parsed_description

    def get_price(self, stay_div):
        """
        Retrieves price-per-night from the scraped page.
        :param stay_div: List[BeautifulSoup Div]
            The stay's list of div's
        :return: parsed_price: string
            The price-per-night of the stay
        """
        all_divs = stay_div.find("div", {"class": "_gig1e7"}).find("div", itemprop="itemListElement")
        price_div = all_divs.find("div", {"class": "_tmwq9g"}).find("div", {"class": "_1bbeetd"}) \
            .find("div", {"class": "_ls0e43"}).find("div", {"class": "_l2ulkt8"}).find("div", {"class": "_vsjqit"})
        unparsed_price = price_div.find("button", {"class": "_ebe4pze"}).text
        parsed_price = "".join(re.findall('\d+', unparsed_price))
        return parsed_price

    def get_rating(self, stay_div):
        """
        Retrieves rating from the scraped page.
        :param stay_div: List[BeautifulSoup Div]
            The stay's list of div's
        :return: rating: string
            The rating (out of 5) of the stay
        """
        all_divs = stay_div.find("div", {"class": "_gig1e7"}).find("div", itemprop="itemListElement")
        rating_div = all_divs.find("div", {"class": "_tmwq9g"}).find("div", {"class": "_1bbeetd"}) \
            .find("span", {"class": "_10fy1f8"})
        rating = rating_div.text
        return rating
