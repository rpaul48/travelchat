class AirBnBStay:
    """
    A property listed on AirBnB.

    author: Joshua Nathan Mugerwa
    """

    def __init__(self, booking_url, photo_url, description, price, rating):
        """
        Initializes a stay with most important information (subjective)
        :param booking_url: URL leading to personal webpage of stay
        :param photo_url: URL of main photo
        :param description: Description provided by owner
        :param price: Price-per-night (including all taxes and fees)
        :param rating: Out of 5
        """
        self._booking_url = booking_url
        self._photo_url = photo_url
        self._description = description
        self._price = price
        self._rating = rating

    def __eq__(self, other):
        """
        Equality override.
        :param other: The other stay.
        :return: True iff the objects represent the same stay
        """
        return type(other) == AirBnBStay and other._booking_url == self._booking_url

    def __str__(self):
        """
        String override.
        :return: A string representation of the object (specifically, a link where you can find ALL information on stay)
        """
        if not self._booking_url:
            return "This stay has no booking URL -- please initialize it correctly!"
        else:
            return "This link contains all information on the stay: {0}".format(self._booking_url)
