Feature: Perform API Call Tests
	
Scenario: API GET Single User
	Given the API receives "/api/users/2"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the values are present under root "data"
	| Key 			| Value																| Type		|
	| id				| 2																	| int		|
	| first_name 	| Janet																| string		|
	| last_name		| Weaver																| string 	|
	| avatar			| https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg 	| string		|
	
Scenario: API GET Single User Not Found
	Given the API receives "/api/users/23"
	Then the response is returned with an HTTP code of "404"	
	
Scenario: API GET Single Resource
	Given the API receives "/api/unknown/2"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the values are present under root "data"
	| Key 			| Value																| Type		|
	| id				| 2																	| int		|
	| name 			| fuchsia rose														| string		|
	| year			| 2001																| int	 	|
	| color			| #C74375														 	| string		|
	| pantone_value	| 17-2031															| string 	|
	
Scenario: API GET Single Resource Not Found
	Given the API receives "/api/unknown/23"
	Then the response is returned with an HTTP code of "404"