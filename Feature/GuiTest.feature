Feature: Perform GUI Tests

@gui
Scenario: Happy Path, Purchase 2 Items
	Given the User is logged in with username "mark.robertson@automationpractice.com" and password "BJSSTest"
	And the User selects menu option "Dresses"
	And the User selects category option "Casual Dresses"
	And the User selects to Quick view the item "Printed Dress"
	And the User changes the Size to "M"
	And the User adds the item to their basket
	And the User continues shopping
	And the User selects menu option "T-Shirts"
	And the User is on the category "T-Shirts"
	And the User selects to Quick view the item "Faded Short Sleeve T-shirts"
	And the User adds the item to their basket
	When the User views the basket
	And confirm the Size of item "Printed Dress" is "M"
	And confirm the Size of item "Faded Short Sleeve T-shirts" is "S"	
	And confirm the price of item "Printed Dress" is "$26.00"
	And confirm the price of item "Faded Short Sleeve T-shirts" is "$16.51"
	And confirm the total price is equal to the total products and total shipping
	Then the User can complete payment by "wire"

@gui
Scenario: Review Previous Orders and Add A Message
	Given the User is logged in with username "mark.robertson@automationpractice.com" and password "BJSSTest"
	And the User views Order History
	When the User selects order with date "04/18/2018" and time "18:22:28"
	And the User adds a message "This order was great"
	Then the User can view the message "This order was great" against todays date

@gui	
Scenario: Capture Images
	Given the User is logged in with username "mark.robertson@automationpractice.com" and password "BJSSTest"
	And the User views Order History
	When the User selects order with date "04/18/2018" and time "18:22:28"
	Then confirm the Color of item "Printed Dress" is "Blue"