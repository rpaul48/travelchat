import unittest
from main.AirBnBStay import AirBnBStay


class TestAirBnBStay(unittest.TestCase):
    '''
    A simple testing suite. Tests our overridden methods and construction.

    author: Joshua Nathan Mugerwa
    version: 5/5/20
    '''

    def setUp(self):
        """
        Creates a few ground-truth stays (*NOTE: these will expire after their booking date).
        """
        self.same_stay_1 = AirBnBStay("https://www.airbnb.com/rooms/21884096?adults=2&check_in=2020-06-08&"
                                      "check_out=2020-06-09&source_impression_id=p3_1588716046_ybADoMobHM4%2FVYW8&"
                                      "guests=1&cancellation_policy_id=51")
        self.same_stay_2 = AirBnBStay("https://www.airbnb.com/rooms/21884096?adults=2&check_in=2020-06-08&check_out="
                                      "2020-06-09&source_impression_id=p3_1588716046_ybADoMobHM4%2FVYW8&guests=1&"
                                      "cancellation_policy_id=51")
        self.repr_and_str_stay = AirBnBStay("https://www.airbnb.com/rooms/34071681?adults=2&check_in=2020-06-08&"
                                            "check_out=2020-06-09&source_impression_id=p3_1588716487_"
                                            "Vd%2BR6uZlUgK9k9rx", price="100.50", rating="4.85")

    def test_str(self):
        """
        Testing __str__.
        """
        expected = "This link contains all information on the stay: https://www.airbnb.com/rooms/34071681?adults=2&" \
                   "check_in=2020-06-08&check_out=2020-06-09&source_impression_id=p3_1588716487_Vd%2BR6uZlUgK9k9rx"
        actual = str(self.repr_and_str_stay)
        self.assertEqual(expected, actual)

    def test_repr(self):
        """
        Testing __repr__.
        """
        expected = "A stay that costs 100.50 USD per night, is rated 4.85 out of 5 stars, and canbe booked at " \
                   "https://www.airbnb.com/rooms/34071681?adults=2&check_in=2020-06-08&check_out=2020-06-09&source_" \
                   "impression_id=p3_1588716487_Vd%2BR6uZlUgK9k9rx"
        actual = repr(self.repr_and_str_stay)
        print(expected, actual)

    def test_eq(self):
        """
        Testing how our scraper deals with faulty requests or runtime errors.
        """
        self.assertEqual(self.same_stay_1, self.same_stay_2)

    def test_not_eq(self):
        """
        Testing how our scraper deals with faulty requests or runtime errors.
        """
        self.assertNotEqual(self.same_stay_1, self.repr_and_str_stay)


if __name__ == '__main__':
    unittest.main()
