import unittest
from main.WebScraper import WebScraper


class TestAirBnBScraper(unittest.TestCase):
    '''
    A simple testing suite. Tests valid and invalid request handling.

    author: Joshua Nathan Mugerwa
    version: 5/5/20
    '''

    def setUp(self):
        """
        Sets the site we'll send our request to and the scraper all tests will use.
        """
        self.test_url = "http://toscrape.com/"
        self.scraper = WebScraper()

    def test_bad_request_handling(self):
        """
        Testing how our scraper deals with faulty requests or runtime errors.
        """
        bad_url = self.test_url + "faulty/url"
        response = self.scraper.get(bad_url)
        self.assertIsNone(response)

    def test_good_request(self):
        """
        Testing how our scraper deals with faulty requests or runtime errors.
        """
        self.assertIsNotNone(self.test_url)


if __name__ == '__main__':
    unittest.main()
