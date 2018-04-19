Feature: Perform GUI Tests
	
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