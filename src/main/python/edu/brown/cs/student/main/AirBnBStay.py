class AirBnBStay:
    """
    A property listed on AirBnB.

    author: Joshua Nathan Mugerwa
    version: 5/5/20
    """

    def __init__(self, booking_url=None, photo_url=None, description=None, price=None, rating=None):
        """
        Initializes a stay with most important information (subjective).
        Default params are None since it's common for one or more to not be accessible.

        :param booking_url: URL leading to personal webpage of stay
        :param photo_url: URL of main photo
        :param description: Description provided by owner
        :param price: Price-per-night (including all taxes and fees)
        :param rating: Out of 5
        """
        self.booking_url = booking_url
        self.photo_url = photo_url
        self.description = description
        self.price = price
        self.rating = rating

    def __eq__(self, other):
        """
        Equality override.

        :param other: The other stay.
        :return: True iff the objects represent the same stay
        """
        return type(other) == AirBnBStay and other.booking_url == self.booking_url

    def __str__(self):
        """
        Printable string override.

        :return: A printable string representation of the object (specifically, a link where you can find ALL
                 information on stay)
        """
        if not self.booking_url:
            return "This stay has no booking URL -- please initialize it correctly!"
        else:
            return "This link contains all information on the stay: {0}".format(self.booking_url)

    def __repr__(self):
        """
        String representation override.

        :return: A string representation of the object
        """
        return f"A stay that costs {self.price} USD per night, is rated {self.rating} out of 5 stars, and can" \
               f" be booked at {self.booking_url}"
