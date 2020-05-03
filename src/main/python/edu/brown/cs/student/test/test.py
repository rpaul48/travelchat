from main import AirBnBScraper


def test():
    """
    Really simple testing.
    :return: True iff our query was successful
    """
    scraper = AirBnBScraper.AirBnBScraper()
    stays = scraper.scrape(190.00, "2020-06-09", "2020-06-10", ("brooklyn", "ny"), 1)
    assert len(stays) != 0


test()
