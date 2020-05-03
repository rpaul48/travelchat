<link rel="stylesheet" href="/css/chat.css">
<link rel="stylesheet" href="/css/sidenav.css">

<#assign content>

    <div class="container">
        <div class="sidewrapper">
            <div class="sidenav">
                <!-- <h2><a id="back" href="/manage-chats">TravelChat</a></h2> -->
                <a id="back" href="/manage-chats"> <img src="/images/TravelChat-Banner.png" alt="TravelChat"> </a>
                <ul>
                    <li><a onclick="openPopup('settings-div')"><i class="fas fa-cog"></i>Settings</a></li>
                    <li><a onclick="openPopup('trip-details-div')"><i class="fas fa-route"></i>Trip Details</a></li>
                    <li><a id="calendar-link"><i class="fas fa-calendar-alt"></i>Calendar</a></li>
                    <li><a onclick="openPopup('plan-my-day-div')"><i class="fas fa-list"></i>Plan My Day</a></li>
                    <li><a onclick="openPopup('restaurants-div')"><i class="fas fa-utensils"></i>Browse Restaurants</a></li>
                    <li><a onclick="openPopup('activities-div')"><i class="fas fa-hiking"></i>Browse Activities</a></li>
                    <li><a onclick="openPopup('lodging-div')"><i class="fas fa-hotel"></i>Browse Lodging</a></li>
                    <li><a onclick="openPopup('flights-div')"><i class="fas fa-plane"></i>Browse Flights</a></li>
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
        <input type="text" placeholder="Display Name" id="update-display-name-field"/>
        <button onclick="editProfile()" class="large-green-button">Save Changes</button>
        <button onclick="leaveChat()" id="leave-chat" class="large-red-button">Leave Chat</button>
        <button onclick="closePopup('settings-div')" class="large-black-button">Back</button>
    </div>

    <div id="budget-div" class="popup-div">
        <h2>Budget</h2>
        <div id="your-budget"></div>
        <input type="number" step="0.01" placeholder="Amount to Log/Add" id="update-budget-field"/>
        <button onclick="updateBudget('log')" class="large-blue-button">Log Payment</button>
        <button onclick="updateBudget('add')" id="add-funds" class="large-green-button">Add Funds</button>
        <button onclick="closePopup('budget-div')" class="large-black-button">Back</button>
    </div>

    <div id="trip-details-div" class="big-popup-div">
        <h2>Trip Details</h2>
        <button onclick="closePopup('trip-details-div')" class="large-black-button">Back</button>
    </div>

    <div id="plan-my-day-div" class="big-popup-div">
        <h2>Plan My Day</h2>
        <button onclick="closePopup('plan-my-day-div')" class="large-black-button">Back</button>
    </div>

    <div id="restaurants-div" class="big-popup-div">
        <h2>Browse Restaurants</h2>
        <div class="form-container">
            <form id="restaurants-form">
                <label>Within </label>
                <select id="restaurant-miles-sel">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">5</option>
                    <option value="4" selected="selected">10</option>
                </select>
                <label> miles from current location </label>

                <br>

                <label>Cuisine: </label>
                <select id="cuisine-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >American</option>
                    <option value="3" >Chinese</option>
                    <option value="4" >Barbecue</option>
                    <option value="5" >Indian</option>
                    <option value="6" >Italian</option>
                    <option value="7" >Japanese</option>
                    <option value="8" >Mexican</option>
                    <option value="9" >Seafood</option>
                    <option value="10" >Thai</option>
                </select>

                <label>Price</label>
                <select id="restaurant-price-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >$</option>
                    <option value="3" >$$</option>
                    <option value="4" >$$$</option>
                </select>

                <label>Minimum rating</label>
                <select id="restaurant-rating-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >3 stars</option>
                    <option value="3" >4 stars</option>
                    <option value="4" >5 stars</option>
                </select>

                <label>Dietary restrictions</label>
                <select id="diet-sel">
                    <option value="1" selected="selected">None</option>
                    <option value="2" >Vegetarian friendly</option>
                    <option value="3" >Vegan options</option>
                    <option value="4" >Halal</option>
                    <option value="5" >Kosher</option>
                    <option value="6" >Gluten-free options</option>
                </select>
                <button onclick="browseRestaurants()" type="button">Search</button>
            </form>
        </div>
        <div class="results-container"></div>
        <button onclick="closePopup('restaurants-div')" class="large-black-button">Back</button>
    </div>

    <div id="activities-div" class="big-popup-div">
        <h2>Browse Activities</h2>
        <div class="form-container">
            <form id="activities-form">
                <label>Within </label>
                <select id="activities-miles-sel">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">5</option>
                    <option value="4" selected="selected">10</option>
                </select>
                <label> miles from current location </label>
                <br>
                <label>Type of activity: </label>
                <div class="activities">
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

                <button onclick="browseActivities()" type="button">Search</button>
            </form>
        </div>
        <div class="results-container"></div>
        <button onclick="closePopup('activities-div')" class="large-black-button">Back</button>
    </div>

    <div id="lodging-div" class="big-popup-div">
        <h2>Browse Lodging</h2>
        <div class="form-container">
            <form id="lodging-form">
                <label>Type</label>
                <select id="hotel-type-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >Hotel</option>
                    <option value="3" >Bed and breakfast</option>
                    <option value="4" >Specialty</option>
                </select>
                <label>Check-in date:</label>
                <!--TODO constrain this by trip date-->
                <input type="date" id="check-in"
                       value="2020-05-15"
                       min="2020-05-02" max="2020-12-31">
                <label>Check-out date:</label>
                <!--TODO constrain this by trip date and check-in date-->
                <input type="date" id="check-out"
                       value="2020-05-16"
                min="2020-05-03" max="2021-01-01">
                <label>Price:</label>
                <select id="hotel-price-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >$</option>
                    <option value="3" >$$</option>
                    <option value="4" >$$$</option>
                </select>
                <label>Minimum rating:</label>
                <select id="hotel-rating-sel">
                    <option value="1" selected="selected">Any</option>
                    <option value="2" >1 star</option>
                    <option value="3" >2 star</option>
                    <option value="4" >3 star</option>
                    <option value="5" >4 star</option>
                    <option value="6" >5 star</option>
                </select>
                <label>Number of rooms</label>
                <input type="number" id="num-rooms" value="1">
                <button onclick="browseLodging()" type="button">Search</button>
            </form>
        </div>
        <div class="results-container"></div>
        <button onclick="closePopup('lodging-div')" class="large-black-button">Back</button>
    </div>

    <div id="flights-div" class="big-popup-div">
        <h2>Browse Flights</h2>
        <label>Departure airport code: </label>
        <input type="text" maxlength="3" id="depart">
        <label>Destination airport code: </label>
        <input type="text" maxlength="3" id="destination">
        <label>Adults: </label>
        <input type="number" step="1" value="0" id="num-adults"></input>
        <label>Children: </label>
        <input type="number" step="1" value="0" id="num-children"></input>
        <label>Seniors: </label>
        <input type="number" step="1" value="0" id="num-adults"></input>
        <label>Max number of stops:</label>
        <select id="max-stops-sel">
            <option value="1" selected="selected">Any</option>
            <option value="2" >0</option>
            <option value="3" >1</option>
            <option value="4" >2+</option>
        </select>
        <label>Class: </label>
        <select id="flight-class-sel">
            <option value="1" selected="selected">Any</option>
            <option value="2" >Economy</option>
            <option value="3" >Premium Economy</option>
            <option value="4" >Business</option>
            <option value="5" >First</option>
        </select>
        <button onclick="browseFlights()" type="button">Search</button>
        <button onclick="closePopup('flights-div')" class="large-black-button">Back</button>
    </div>

    <script src="/js/login.js"></script>
    <script src="/js/chat.js"></script>
</#assign>
<#include "main.ftl">