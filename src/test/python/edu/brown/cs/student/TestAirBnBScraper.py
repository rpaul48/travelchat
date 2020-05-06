import unittest

from main.AirBnBScraper import AirBnBScraper


class TestAirBnBScraper(unittest.TestCase):
    '''
    A simple testing suite. Testing our ability to catch common input errors, and basic scraping.

    author: Joshua Nathan Mugerwa
    version: 5/5/20
    '''

    def setUp(self):
        """
        Creates a few ground-truth stays (*NOTE: these will expire after their booking date).
        """
        self.query_params = ("200.00", "2020-06-08", "2020-06-09", ("brooklyn", "ny"), "2")
        self.scraper = AirBnBScraper()

    def test_query(self):
        """
        Testing a ground-truth query.
        """
        stays = self.scraper.scrape(*self.query_params)
        self.assertNotEqual(len(stays), 0)

    def test_param_validation_date_bad1(self):
        """
        Invalidates a improper date.
        """
        params = (200.00, "202-06-08", "2020-06-09", ("brooklyn", "ny"), 2)
        self.assertFalse(self.scraper.params_are_valid(*params))

    def test_param_validation_date_bad2(self):
        """
        Invalidates a improper date.
        """
        params = (200.00, "2020-06-08", "2020-06-0", ("brooklyn", "ny"), 2)
        self.assertFalse(self.scraper.params_are_valid(*params))

    def test_param_validation_date_good(self):
        """
        Validates a proper date.
        """
        params = (200.00, "2020-06-08", "2025-12-31", ("brooklyn", "ny"), 2)
        self.assertTrue(self.scraper.params_are_valid(*params))

    def test_param_validation_location(self):
        """
        Testing our location parameter check.
        """
        paramsBad1 = (200.00, "2020-06-08", "2020-06-09", "brooklyn", 2)  # Too few
        paramsBad2 = (200.00, "2020-06-08", "2020-06-09", ("brooklyn", "ny", "USA"), 2)  # Too many
        paramsGood = (200.00, "2020-06-08", "2020-06-09", ("brooklyn", "ny"), 2)

        check_all_at_once = not (
                    self.scraper.params_are_valid(*paramsBad1) or self.scraper.params_are_valid(*paramsBad2)) \
                            and self.scraper.params_are_valid(*paramsGood)

        self.assertTrue(check_all_at_once)

    def test_param_validation_price_and_rooms(self):
        """
        Testing if we catch bad price and room inputs.
        """
        paramsBad1 = (200.00, "2020-06-08", "2020-06-09", ("brooklyn", "ny"), -1)
        paramsBad2 = (-1, "2020-06-08", "2020-06-09", ("brooklyn", "ny"), 2)

        check_all_at_once = self.scraper.params_are_valid(*paramsBad1) or self.scraper.params_are_valid(*paramsBad2)
        self.assertFalse(check_all_at_once)


if __name__ == '__main__':
    unittest.main()
