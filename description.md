#### Core Task description: 

Create Spring application with the following services and logic using either XML or Annotation configuration. Create domain objects as needed. Create DAO classes for storing data in simple static maps (later, they will be replaced for storing data in DB).

Application for managing a movie theater. Allows for admins to enter events, view purchased tickets. Allows for users to register, view events with air dates and times, get ticket ticketPrice, buy tickets.

There is no application UI for now. Create a simple console application that demonstrates the work. Additionally, unit tests could also be created.

Use Maven for building a project. Use latest Spring 3 version (3.x.x)

Services and their descriptions:

UserService - Manages registered users

register, remove, getById, getUserByEmail, getUsersByName, getBookedTickets
EventService - Manages events (movie shows). Event contains only basic information, like name, base ticketPrice for tickets, rating (high, mid, low). Event can be presented on several dates and several times within each day. For each dateTime an Event will be presented only in single auditorium.

create, remove, getByName, getAll
getForDateRange(from, to) - returns events for specified date range (OPTIONAL)
getNextEvents(to) - returns events from now till the ‘to’ date (OPTIONAL)
assignAuditorium(event, auditorium, date) - assign auditorium for event for specific date. Only one auditorium for Event for specific dateTime
AuditoriumService - Returns info about auditoriums and places

Since auditorium information is usually static, store it in some property file. The information that needs to be stored is:
   name
   number of seats
   vip seats (comma-separated list of expensive seats)
Several auditoriums can be stored in separate property files, information from them could be injected into the AuditoriumService

getAuditoriums(), getSeatsNumber(), getVipSeats()
BookingService - Manages tickets, prices, bookings

getTicketPrice(event, date, time, seats, user) - returns ticketPrice for ticket for specified event on specific date and time for specified seats.
User is needed to calculate discount (see below)
Event is needed to get base ticketPrice, rating
Vip seats should cost more than regular seats (For example, 2xBasePrice)
All prices for high rated movies should be higher (For example, 1.2xBasePrice)
bookTicket(user, ticket) - user could  be registered or not. If user is registered, then booking information is stored for that user. Purchased tickets for particular event should be stored
getTicketsForEvent(event, date) - get all purchased tickets for event for specific date
DiscountService - Counts different discounts for purchased tickets

getDiscount(user, event, date) - returns discount for each ticket for the user on particular event
DiscountStrategy - single class with logic for calculating discount
   Birthday strategy - give 5% if user has birthday
   Every 10th ticket - give 50% for every 10th ticket purchased by user
All discount strategies should be injected as list into the DiscountService. The getDiscount method will execute each strategy to get max available discount.
Define DiscountService with all strategies as separate beans in separate configuration file (either separate XML or separate Java config class)

----------

Add some aspects to the application created when implementing hometask for Spring Core (1-10):

CounterAspect - aspectCount how many times each event was accessed by name, how many times its prices were queried, how many times its tickets were booked. Store counters in map for now (later could be replaced by DB dao)
DiscountAspect - aspectCount how many times each discount was given total and for specific user
LuckyWinnerAspect - every time the bookTicket method is executed perform the checkLucky method for the user that based on some randomness will return true or false. If user is lucky, the ticketPrice changes to zero and ticket is booked, thus user pays nothing. Store the information about this lucky event into the user object (like some system messages or so) - OPTIONAL

-------------------

Based on the home task performed for Spring Core (1.1-1.10) do the following:

Create DAO objects that use JDBCTemplate to store and retrieve data from DB.

Based on the home task performed for Spring Aspects (2.1-2.2) do the following:

Add DAO object to store all counters into the database.

----------------

#### Advanced:

1 Task description:

1. Based on the codebase of previous hometasks, create a web application, configure Spring MVC application context and dispatcher servlet.

2. For all BookingFacde operations implement Spring MVC annotation-based controllers.

3. For operations that return one or several entites as a result (e.g. getUserByEmail, getUsersByName, getBookedTickets) implement simple views rendered via Velocity template engine. Use InternalResourceViewResolver for view resolving procedure.

4. For operations, that return list of booked tickets (by event, or by user) implement alternative controllers, that will return result as PDF document. Map this controller to a specific value of Accept request header  - Accept=application/pdf

5. Implement batch loading of users and events into system. In order to do this, create controller which accepts multipart file upload, parses it and calls BookingFacade methods to add events and users into the system. The format of the file (JSON, XML, ...) is up to you as long as you can implement the correct parsing procedure.

6. Implement generic exception handler which should redirect all controller exceptions to simple Velocity view, that just prints exception message.


2 Task description:

