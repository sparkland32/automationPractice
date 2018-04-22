Feature: Perform API Call Tests

@api @get
Scenario: API GET List Users
	Given the API performs GET request on "/api/users?page=2"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains key "total" equal to int "12"
	Then the response contains key "total_pages" equal to int "4"	
	And the response collection has items
	| Collection				| Has Values				| Type				|
	| data.id				| 4,5,6					| int				|	
	| data.first_name		| Eve,Charles,Tracey		| string				|
	| data.last_name			| Holt,Morris,Ramos		| string				|

@api @get
Scenario: API GET Single User
	Given the API performs GET request on "/api/users/2"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains values equal to
	| Key 				| Equal To															| Type		|
	| data.id			| 2																	| int		|
	| data.first_name 	| Janet																| string		|
	| data.last_name		| Weaver																| string 	|
	| data.avatar		| https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg	| string		|

@api @get	
Scenario: API GET Single User Not Found
	Given the API performs GET request on "/api/users/23"
	Then the response is returned with an HTTP code of "404"	

@api @get	
Scenario: API GET List Resource
	Given the API performs GET request on "/api/unknown"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains key "total" equal to int "12"
	Then the response contains key "total_pages" equal to int "4"	
	And the response collection has items
	| Collection				| Has Values							| Type				|
	| data.id				| 1,2,3								| int				|	
	| data.name				| cerulean,fuchsia rose,true red		| string				|
	| data.year				| 2000,2001,2002						| int				|	

@api @get	
Scenario: API GET Single Resource
	Given the API performs GET request on "/api/unknown/2"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains values equal to
	| Key 				| Equal To																| Type		|
	| data.id			| 2																		| int		|
	| data.name 			| fuchsia rose															| string		|
	| data.year			| 2001																	| int	 	|
	| data.color			| #C74375															 	| string		|
	| data.pantone_value	| 17-2031																| string 	|

@api @get	
Scenario: API GET Single Resource Not Found
	Given the API performs GET request on "/api/unknown/23"
	Then the response is returned with an HTTP code of "404"

@api @post	
Scenario: API POST Create
	Given the API performs POST request on "/api/users" with values
	| Key 				| Equal To	|
	| name				| morpheus 	|
	| job				| leader		|
	When the response is returned with an HTTP code of "201"
	And an object type of "JSON"
	Then the response contains key "id" not null
	And the response contains key "createdAt" not null

@api @put	
Scenario: API PUT Update
	Given the API performs PUT request on "/api/users/2" with values
	| Key 				| Equal To			|
	| name				| morpheus 			|
	| job				| zion_resident		|
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains key "name" equal to string "morpheus"
	And the response contains key "job" equal to string "zion_resident"
	And the response contains key "updatedAt" not null

@api @patch	
Scenario: API PATCH Update
	Given the API performs PATCH request on "/api/users/2" with values
	| Key 				| Equal To			|
	| name				| morpheus 			|
	| job				| zion_resident		|
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains key "name" equal to string "morpheus"
	And the response contains key "job" equal to string "zion_resident"
	And the response contains key "updatedAt" not null	

@api @delete	
Scenario: API DELETE Delete
	Given the API performs DELETE request on "/api/users/2"
	Then the response is returned with an HTTP code of "204"

@api @post	
Scenario: API POST Register - Successful
	Given the API performs POST request on "/api/register" with values
	| Key 				| Equal To		|
	| email				| sydney@fife 	|
	| password			| pistol			|
	When the response is returned with an HTTP code of "201"
	And an object type of "JSON"
	Then the response contains key "token" not null

@api @post	
Scenario: API POST Register - Unsuccessful
	Given the API performs POST request on "/api/register" with values
	| Key 				| Equal To		|
	| email				| sydney@fife 	|
	When the response is returned with an HTTP code of "400"
	And an object type of "JSON"
	Then the response contains key "error" equal to string "Missing password"

@api @post	
Scenario: API POST Login - Successful
	Given the API performs POST request on "/api/login" with values
	| Key 				| Equal To		|
	| email				| peter@klaven 	|
	| password			| cityslicka		|
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains key "token" not null

@api @post	
Scenario: API POST Login - Unsuccessful
	Given the API performs POST request on "/api/login" with values
	| Key 				| Equal To		|
	| email				| peter@klaven 	|
	When the response is returned with an HTTP code of "400"
	And an object type of "JSON"
	Then the response contains key "error" equal to string "Missing password"	

@api @get	
Scenario: API GET Delayed Response
	Given the API performs GET request on "/api/users?delay=3"
	When the response is returned with an HTTP code of "200"
	And an object type of "JSON"
	Then the response contains key "total" equal to int "12"
	Then the response contains key "total_pages" equal to int "4"	
	And the response collection has items
	| Collection				| Has Values				| Type				|
	| data.id				| 1,2,3					| int				|	
	| data.first_name		| George,Janet,Emma		| string				|
	| data.last_name			| Bluth,Weaver,Wong		| string				|	