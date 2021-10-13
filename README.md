# O bag project


@ENTITIES_____________________________________________________________________________

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

ROLE
id (PK),
RoleEnum(USER, ADMIN…)
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

@MappedSupperclass
BaseEntity
id(PK),
createdOn ?

ENUMS_____________________________________________________________________________

CATEGORY
Obag
Omini
Opocket
Oclock

SEASON
Spring’21
Summer’21
Fall’21
Winter’21

ROLEENUM
USER
ADMIN
SUPERADMIN

STATUS
new
hold
processing
Sent
delivered
returned
PAYMENT
cash
credit card
bank transfer
pay pal







SECURITY AND PERMISSION FUNCTIONALITY__________________________________________

LOGIN(email:pass)
	1.0. required fields: email, pass,
	1.1. check for @Valid input (@Emal, @Password)
	1.2. check for correct/existing email
	1.3. check for matching email:pass
	1.4. login the user

2. REGISTER (name, phone, email, password, rePassword, address, city, postCode, country)
	2.0. required fields: name, email, pass, rePass
	2.1. check for @Valid input (name, @Emal, @Password)
	2.2. check for not existing in DB email (free)
	2.3. check for matches password and confirmPassword
	2.4. register the user

3. LOGOUT

4. MENU TOP (search, login, register, logout, orders, administration, hello)
	4.1. anonymous load: search, login, register
	4.2. USER logged-in: hello, orders, search, logout
	4.3. ADMIN logged-in: hello, administration, design, orders, search, logout
	4.4. SUPERADMIN: ADMIN + Role management.

5. ROLES (set role / access rights to users, by default newly registered are USERs)


PRODUCTS FUNCTIONALITY_____________________________________________________________

6. ADD PRODUCT
	6.1. required fields: name, sku, category, season, cost, price, description
	6.2. check for @Valid input
	6.3. list product in the summary report, ordered by Category, then sku
	6.4. // TODO: Edit
	6.5. // TODO: Delete

7. IMPORTS FROM FILES 
	7.1. from JSON, using Gson library, mark fields with @Expose annotation
	7.2. from XML, using JAXB Context

8. ADD/EDIT CATEGORIES
	8.1. // TODO


E-SHOP FUNCTIONALITY                                                                                                                              

9. DESIGN <HTML, CSS, Bootstrap, JavaScript>
	9.1. Top menu (admin/login)
	9.2. Main menu <nav> - logo, main categories by dropdown or mega menu.
	9.3. Content <body>
	     9.3.1. Homepage, main focus banner(horizontal), and with 5-6 accents (squares)
	     9.3.2. Category page - list products summary view (image, name & price)
	     9.3.3. Product page - product image 50%, and sku, name, price, description, qty, Buy btn, Shipment & Returns, Customer Care (accordion)
	9.4. Footer <footer> - contacts (focus), all links, like sitemap and copyrights.

ORDERS FUNCTIONALITY________________________________________________________________


