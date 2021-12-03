O bag project

@ENTITIES_____________________________________________________________________________

@MappedSupperclass
BASE_ENTITY
id(PK),
createdOn

USER 
id (PK), 
firstName(NN), 
lastName, 
phone(NN), 
email(NN, UQ, length = 30), 
password(NN, hashed), 
address, 
city, 
postCode, 
country, 
role, Enum(USER, ADMIN…)
registeredOn,
countOrders, 
valueOrders, 
products (List<Product>), 
note (‘TEXT’)
createdOn

ROLE
id (PK),
RoleEnum(USER, ADMIN…)




PRODUCT
id (PK),
name(NN, UQ),
sku(NN, UQ),
category, Enum(OBAG, MINI)
season, Enum(Spring’21…)
metric
cost(NN)
price(NN)
barcode
description(NN)
image(NN)
createdOn
createdBy
deleted

CATEGORY
id (PK),
category(NN, UQ),
priority
createdOn
createdBy
image
deleted

SEASON
id (PK),
category(NN, UQ),
priority
createdOn
createdBy
deleted
ORDER
id (PK),
date(NN)
products(List<Product>)
totalValue
user(User.class)
status, Enum(New, Process)
trackingNumber
peymentMethod, Enum(cash
note (‘TEXT’)
createdOn

CART
Id (PK)
List<Products>
deliveryCost
totalValue
User user
createdOn

LOG
user_id
product_id
action

VISIT
Id (PK)
uniqueVisits
pageClicks
createdOn



ENUMS_____________________________________________________________________________

ROLEENUM
user
admin
superadmin

STATUS
new
on hold
processing
sent
delivered
returned

PAYMENT
cash
credit card
bank transfer
pay pal


SECURITY AND PERMISSION ___________________________________________________________

LOGIN(email:pass)
	1.0. required fields: email, pass,
	1.1. check for @Valid input (@Emal, @Password)
	1.2. check for correct/existing email
	1.3. check for matching email:pass
	1.4. login the user

2. REGISTER (name, phone, email, password, rePassword, address, city, postCode, country)
	2.0. required fields: name, email, pass, rePass
	2.1. check for @Valid input (name, @Emal, @Password)
	2.2. check for not existing email in DB (free)
	2.3. check for matching password and confirmPassword
	2.4. register the user

3. LOGOUT

4. MENU TOP (search, login, register, logout, orders, administration, hello)
	4.1. anonymous load: search, login, register
	4.2. USER logged-in: hello, orders, search, logout
	4.3. ADMIN logged-in: hello, administration, design, orders, search, logout
	4.4. SUPERADMIN: ADMIN + Role management.

5. ROLES (set role / access rights to users, by default newly registered are USERs)
	5.1 select user y email (from dropdown), set/add new role


PRODUCTS FUNCTIONALITY_____________________________________________________________

6. PRODUCT MANAGEMENT
	6.1. ADD product
	    6.1.1. required fields: name, sku, category, season, cost, price, description
	    6.1.2. optional fields: metric, barcode, image, mark deleted (soft)
	    6.1.3. check for @Valid input
	    6.1.4. check for Unique constraints (name, sku, barcode)
	    6.1.5. list all products(not deleted) in the summary report, ordered by Category, then sku
	    6.1.6. Clone functionality, load(copy) data / fields from existing products

	6.2. UPDATE product
	    6.2.1. required fields: name, sku, category, season, cost, price, description
	    6.2.2. optional fields: metric, barcode, image, mark deleted (soft)
	    6.2.3. check for @Valid input
	    6.2.4. check for Unique constraints, except current one (name, sku, barcode)
	    6.2.5. list all products(not deleted) in the summary report, ordered by Category, then sku
	    6.2.6. Edit data / fields of existing product
	    6.2.7. Clone functionality, load(copy) data / fields from existing products	

7. IMPORTS FROM FILES 
	7.1. from JSON, using Gson library, mark fields with @Expose annotation
	7.2. from XML, using JAXB Context



8. ADD/EDIT CATEGORIES
	8.1. ADD category
	    8.1.1. required fields: category
	    8.1.2. optional fields: id, priority, createdOn, createdBy, mark deleted (soft)
	    8.1.3. check for @Valid input
	    8.1.4. check for Unique constraints (category name)
	    8.1.5. list all categories(incl. deleted) in the summary report, ordered by Priority

	8.2. UPDATE category
	    8.2.1. required fields: category
	    8.2.2. optional fields: id, priority, createdOn, createdBy, mark deleted (soft)
	    8.2.3. check for @Valid input
	    8.2.4. check for Unique constraints (category name)
	    8.2.5. list all categories(incl. deleted) in the summary report, ordered by Priority
	    8.2.6. edit data / fields of existing category

9. ADD/EDIT SEASONS
	9.1. ADD season
	    9.1.1. required fields: season
	    9.1.2. optional fields: id, priority, createdOn, createdBy, mark deleted (soft)
	    9.1.3. check for @Valid input
	    9.1.4. check for Unique constraints (season name)
	    9.1.5. list all seasons (incl. deleted) in the summary report, ordered by Priority

	9.2. UPDATE category
	    9.2.1. required fields: season
	    9.2.2. optional fields: id, priority, createdOn, createdBy, mark deleted (soft)
	    9.2.3. check for @Valid input
	    9.2.4. check for Unique constraints (season name)
	    9.2.5. list all seasons (incl. deleted) in the summary report, ordered by Priority
	    9.2.6. edit data / fields of existing season


USERS PROFILE _____________________________________________________________________________________                                                                                                  
10. USER’s PROFILE
	10.1 Edit/update User profile	
	     10.1.1. user image (125px round)
	     10.1.2. name, phone, email, password, address…
	     10.1.3. Edit/update User details (name, phone, email, password, address)
	10.2 ORDERS (default)
	     10.2.1. List of all submitted orders, latest on top.
	     10.2.2. Each order record, must have No, datetime, status, tracking number
	     10.2.3. Each order have List of Products(image, name, qty, price) and Order Total Value and Delivery cost.
	10.3 FAVOURITES
		// TODO:


E-SHOP ____________________________________________________________________________________________                                                                                                                             
11. DESIGN <HTML, CSS, Bootstrap, JavaScript>
	11.1. Top menu (admin/login)
	11.2. Main menu <nav> - logo, main categories by dropdown or mega menu.
	11.3. Content <body>
	     11.3.1. Homepage, main focus banner/slider (horizontal), 4-5 accents (squares) “chess” design, 2 rectangular images, horizontal narrow stripe Viber service
	     11.3.2. Category page - list products summary view (image, name & price)
	     11.3.3. Product page - product image 50%, and sku, name, price, description, qty, Buy btn, Shipment & Returns, Customer Care (accordion)
	11.4. Footer <footer> - contacts (focus), all links, like sitemap and copyrights.

ORDERS FUNCTIONALITY______________________________________________________________________________ 

// TODO: Coming soon…! Hopefully




LOGS, STATISTICS_____________________________________________________________________________________ 

12. LOGS (with AspectJ and @PointCut)
	12.1. USER BEHAVIOUR
	     12.1.1 Date time statistic of all (logged-in) Users behaviour, by email/id.
	     12.1.2 Products view page, ordered by user, showing products view flow.
	     12.1.3 Action/method call and date time
	12.2. TOP VIEW PRODUCTS
	     12.2.1. Aggregated statistic of products page view’s count
	     12.2.2. Ordered by top view product, and their details(sku, category, season, price...).
	12.3. COUNT OF DAILY PAGE VIEWS/CLICKS (with Interceptor)
	     12.3.1 Collect daily page loadings by HttpSessionId and number of request/clicks, record the data via Scheduler by midnight.
	12.4. @Schedule - clear/delete Logs, older than 1 month, every night at 3:00AM
