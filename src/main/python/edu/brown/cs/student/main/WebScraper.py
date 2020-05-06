from contextlib import closing
from requests import get
from requests.exceptions import RequestException


class WebScraper:
    '''
    A simple web scraper.
    MUCH credit to Colin OKeefe of RealPython.org!

    author: Joshua Nathan Mugerwa
    version: 5/5/20
    '''

    def __init__(self):
        """
        Empty constructor.
        """
        pass

    def log_error(self, error):
        """
        Informal logging of an error that occurred during scraping.

        :param error: Error
            The error that occured during scraping
        """
        print(error)

    def get(self, url):
        """
        Attempts to get the content at `url` by making an HTTP GET request.
        If the content-type of response is some kind of HTML/XML, return the
        text content, otherwise return None.

        :param url: string
            The URL of the page to scrape
        :return: None or Response content
            None if an error occurred else Response content
        """
        try:
            '''
            1. Opening w/ resources
            2. Streaming allows us to conditionally download content -- i.e. if response is good, then return
            3. Timeout allows us to stop trying to pull after a certain amount of time
            '''
            with closing(get(url, stream=True, timeout=3)) as resp:
                if self.is_good_response(resp):
                    return resp.content
                else:
                    return None

        except RequestException as e:
            self.log_error('Error of type {0} during requests to {1} : {2}'.format(type(e), url, str(e)))
            return None
        except KeyboardInterrupt:
            self.log_error("Keyboard Interruption during request. Ending data download.")
            return None

    def is_good_response(self, resp):
        """
        Returns True if the response seems to be HTML, False otherwise.

        :param resp: Response
            The response from the target page
        :return: True iff the response is valid
        """
        content_type = resp.headers['Content-Type'].lower()
        return (resp.status_code == 200
                and content_type is not None
                and content_type.find('html') > -1)
