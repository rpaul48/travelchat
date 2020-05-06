<link rel="stylesheet" href="/css/chat.css">
<link rel="stylesheet" href="/css/sidenav.css">

<#assign content>

    <div class="container">
        <div class="sidewrapper">
            <div class="sidenav">
                <!-- <h2><a id="back" href="/manage-chats">TravelChat</a></h2> -->
                <a id="back" href="/manage-chats"> <img src="/images/TravelChat-Banner.png" alt="TravelChat"> </a>
                <ul>
                    <li><a href="#settings-div" onclick="openPopup('settings-div')"><i class="fas fa-cog"></i>Settings</a></li>
                    <li><a href="#trip-details-div" onclick="openPopup('trip-details-div')"><i class="fas fa-route"></i>Trip Details</a></li>
                    <li><a id="calendar-link"><i class="fas fa-calendar-alt"></i>Calendar</a></li>
                    <li><a href="#plan-my-day-div" onclick="openPopup('plan-my-day-div')"><i class="fas fa-list"></i>Plan My Day</a></li>
                    <li><a href="#restaurants-div" onclick="openPopup('restaurants-div')"><i class="fas fa-utensils"></i>Browse Restaurants</a></li>
                    <li><a href="#activities-div" onclick="openPopup('activities-div')"><i class="fas fa-hiking"></i>Browse Activities</a></li>
                    <li><a href="#lodging-div" onclick="openPopup('lodging-div')"><i class="fas fa-hotel"></i>Browse Lodging</a></li>
                    <li><a href="#flights-div" onclick="openPopup('flights-div')"><i class="fas fa-plane"></i>Browse Flights</a></li>
                </ul>
            </div>
        </div>
        <div class="main">
            <div id="firechat-wrapper">
            </div>
        </div>
    </div>

    <div id="settings-div" class="popup-div">
        <h2>Settings</h2>
        <input type="text" aria-label="Display Name" placeholder="Display Name" id="update-display-name-field"/>
        <button onclick="editProfile()" class="large-green-button">Save Changes</button>
        <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>
        <button onclick="closePopup('settings-div')" class="small-black-button">Back</button>
    </div>

    <div id="budget-div" class="popup-div">
        <h2>Budget</h2>
        <div id="your-budget"></div>
        <input type="number" step="0.01" aria-label="Amount to Log/Add" placeholder="Amount to Log/Add" id="update-budget-field"/>
        <button onclick="updateBudget('log')" class="large-blue-button">Log Payment</button>
        <button onclick="updateBudget('add')" id="add-funds" class="large-green-button">Add Funds</button>
        <button onclick="closePopup('budget-div')" class="small-black-button">Back</button>
    </div>

    <div id="trip-details-div" class="big-popup-div">
        <h2>Trip Details</h2>
        <form id="trip-details-form">
        <label>Start date: <input type="date" id="trip-start-date" min="2020-01-01" class="date-class"></label>
        <label>End date: <input type="date" id="trip-end-date" min="2020-01-01" class="date-class"></label>
        <button onclick="closePopup('trip-details-div')" class="small-black-button">Back</button>
    </div>

    <div id="plan-my-day-div" class="big-popup-div">
        <h2>Plan My Day</h2>
        <div class="form-container">
            <form id="plan-my-day-form">
                <label for="date-to-plan">Date: <input type="date" class="date-class" min="2020-01-01" id="date-to-plan"></label>
                <label for="max-distance">Max distance from current location (miles): <input type="number" min="1" id="max-distance"> </label>
                <label>Preferred cuisines: </label>
                <div class="checkboxes">
                    <label><input type="checkbox" name="pmd-cuisine" value="Any" checked>Any</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="American">American</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Barbecue">Barbecue</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Chinese">Chinese</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Indian">Indian</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Italian">Italian</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Japanese">Japanese</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Mexican">Mexican</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Seafood">Seafood</label>
                    <label><input type="checkbox" name="pmd-cuisine" value="Thai">Thai</label>
                </div>
                <label>Preferred activity types: </label>
                <div class="checkboxes">
                    <label><input type="checkbox" name="pmd-activity" value="All" checked/>All</label>
                    <label><input type="checkbox" name="pmd-activity" value="Boat Tours & Water Sports">Boat Tours & Water Sports</label>
                    <label><input type="checkbox" name="pmd-activity" value="Fun & Game">Fun & Game</label>
                    <label><input type="checkbox" name="pmd-activity" value="Nature & Parks">Nature & Parks</label>
                    <label><input type="checkbox" name="pmd-activity" value="Sights & Landmarks">Sights & Landmarks</label>
                    <label><input type="checkbox" name="pmd-activity" value="Shopping">Shopping</label>
                    <label><input type="checkbox" name="pmd-activity" value="Transportation">Transportation</label>
                    <label><input type="checkbox" name="pmd-activity" value="Museums">Museums</label>
                    <label><input type="checkbox" name="pmd-activity" value="Outdoor Activities">Outdoor Activities</label>
                    <label><input type="checkbox" name="pmd-activity" value="Spas & Wellness">Spas & Wellness</label>
                    <label><input type="checkbox" name="pmd-activity" value="Classes & Workshops">Classes & Workshops</label>
                    <label><input type="checkbox" name="pmd-activity" value="Tours">Tours</label>
                    <label><input type="checkbox" name="pmd-activity" value="Nightlife">Nightlife</label>
                </div>
                <button onclick="planMyDay()" type="button" class="small-green-button">Plan My Day</button>
            </form>
        </div>
        <button onclick="closePopup('plan-my-day-div')" class="small-black-button">Back</button>
    </div>

    <div id="restaurants-div" class="big-popup-div">
        <h2>Browse Restaurants</h2>
        <div class="form-container">
            <form id="restaurants-form">
                <label for="restaurant-miles-sel">Within </label>
                <select aria-label="Restaurant Miles Within" id="restaurant-miles-sel">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">5</option>
                    <option value="4" selected="selected">10</option>
                </select>
                <label> miles from current location </label>

                <br>

                <label>Preferred cuisines: </label>
                <div class="checkboxes">
                    <label><input type="checkbox" name="browse-cuisine" value="Any" checked>Any</label>
                    <label><input type="checkbox" name="browse-cuisine" value="American">American</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Barbecue">Barbecue</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Chinese">Chinese</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Indian">Indian</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Italian">Italian</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Japanese">Japanese</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Mexican">Mexican</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Seafood">Seafood</label>
                    <label><input type="checkbox" name="browse-cuisine" value="Thai">Thai</label>
                </div>

                <label for="restaurant-price-sel">Price</label>
                <select aria-label="Restaurant Price" id="restaurant-price-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >$</option>
                    <option value="3" >$$-$$$</option>
                    <option value="4" >$$$$</option>
                </select>

                <label for="restaurant-rating-sel">Minimum rating</label>
                <select aria-label="Restaurant Minimum Rating" id="restaurant-rating-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >2 stars</option>
                    <option value="3" >3 stars</option>
                    <option value="4" >4 stars</option>
                    <option value="5" >5 stars</option>
                </select>

                <label for="diet-sel">Dietary restrictions</label>
                <select aria-label="Dietary Restrictions" id="diet-sel">
                    <option value="1" selected="selected">None</option>
                    <option value="2" >Vegetarian friendly</option>
                    <option value="3" >Vegan options</option>
                    <option value="4" >Halal</option>
                    <option value="5" >Gluten-free options</option>
                </select>
                <button onclick="browseRestaurants()" type="button" class="small-green-button">Search</button>
            </form>
        </div>
        <div id="restaurants-results" class="results-container"></div>
        <button onclick="closePopup('restaurants-div')" class="small-black-button">Back</button>
    </div>

    <div id="activities-div" class="big-popup-div">
        <h2>Browse Activities</h2>
        <div class="form-container">
            <form id="activities-form">
                <label for="activities-miles-sel">Within </label>
                <select aria-label="Activities Miles Within" id="activities-miles-sel">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">5</option>
                    <option value="4" selected="selected">10</option>
                </select>
                <label> miles from current location </label>
                <br>
                <label>Type of activity: </label>
                <div class="checkboxes">
                    <label><input type="checkbox" name="browse-activity" value="All" checked/>All</label>
                    <label><input type="checkbox" name="browse-activity" value="Boat Tours & Water Sports">Boat Tours & Water Sports</label>
                    <label><input type="checkbox" name="browse-activity" value="Fun & Game">Fun & Game</label>
                    <label><input type="checkbox" name="browse-activity" value="Nature & Parks">Nature & Parks</label>
                    <label><input type="checkbox" name="browse-activity" value="Sights & Landmarks">Sights & Landmarks</label>
                    <label><input type="checkbox" name="browse-activity" value="Shopping">Shopping</label>
                    <label><input type="checkbox" name="browse-activity" value="Transportation">Transportation</label>
                    <label><input type="checkbox" name="browse-activity" value="Museums">Museums</label>
                    <label><input type="checkbox" name="browse-activity" value="Outdoor Activities">Outdoor Activities</label>
                    <label><input type="checkbox" name="browse-activity" value="Spas & Wellness">Spas & Wellness</label>
                    <label><input type="checkbox" name="browse-activity" value="Classes & Workshops">Classes & Workshops</label>
                    <label><input type="checkbox" name="browse-activity" value="Tours">Tours</label>
                    <label><input type="checkbox" name="browse-activity" value="Nightlife">Nightlife</label>
                </div>

                <button onclick="browseActivities()" type="button" class="small-green-button">Search</button>
            </form>
        </div>
        <div id="activities-results" class="results-container"></div>
        <button onclick="closePopup('activities-div')" class="small-black-button">Back</button>
    </div>

    <div id="lodging-div" class="big-popup-div">
        <h2>Browse Lodging</h2>
        <div class="form-container">
            <form id="lodging-form">
                <label for="hotel-type-sel">Type</label>
                <select aria-label="Lodging Type" id="hotel-type-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >Hotel</option>
                    <option value="3" >Bed and breakfast</option>
                    <option value="4" >Specialty</option>
                </select>
                <label for="check-in">Check-in date:</label>
                <input aria-label="Check-in date" type="date" id="check-in"
                       value="2020-05-15" min="2020-01-01" class="date-class">
                <label for="check-out">Check-out date:</label>
                <input aria-label="Check-out date" type="date" id="check-out"
                       value="2020-05-16" min="2020-01-01" class="date-class">
                <label for="hotel-rating-sel">Minimum rating:</label>
                <select aria-label="Minimum Hotel Rating" id="hotel-rating-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >1 star</option>
                    <option value="3" >2 star</option>
                    <option value="4" >3 star</option>
                    <option value="5" >4 star</option>
                    <option value="6" >5 star</option>
                </select>
                <label for="num-rooms">Number of rooms</label>
                <input aria-label="Number of rooms" type="number" id="num-rooms" value="1">
                <button onclick="browseLodging()" type="button" class="small-green-button">Search</button>
            </form>
        </div>
        <div id="lodging-results" class="results-container"></div>
        <button onclick="closePopup('lodging-div')" class="small-black-button">Back</button>
    </div>

    <div id="flights-div" class="big-popup-div">
        <h2>Browse Flights</h2>
        <div class="form-container">
            <form id="flights-form">
                <label for="departure-date">Departure date:</label>
                <input type="date" id="departure-date" value="2020-05-15" min="2020-01-01" class="date-class">
                <label for="depart">Departure airport code: </label>
                <input aria-label="Departure airport code" type="text" maxlength="3" id="depart">
                <label for="destination">Destination airport code: </label>
                <input aria-label="Destination airport code" type="text" maxlength="3" id="destination">
                <label for="num-adults">Adults: </label>
                <input type="number" step="1" value="1" id="num-adults"></input>
                <label for="num-children">Children: </label>
                <input type="number" step="1" value="0" id="num-children"></input>
                <label for="num-seniors">Seniors: </label>
                <input type="number" step="1" value="0" id="num-seniors"></input>
                <label for="max-stops-sel">Max number of stops:</label>
                <select id="max-stops-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >0</option>
                    <option value="3" >1</option>
                    <option value="4" >2+</option>
                </select>
                <label for="flight-class-sel">Class: </label>
                <select id="flight-class-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >Economy</option>
                    <option value="3" >Premium Economy</option>
                    <option value="4" >Business</option>
                    <option value="5" >First</option>
                </select>
                <button onclick="browseFlights()" type="button" class="small-green-button">Search</button>
            </form>
        </div>
        <div id="flights-results" class="results-container"></div>
        <button onclick="closePopup('flights-div')" class="small-black-button">Back</button>
    </div>

    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">