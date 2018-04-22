# automationPractice
Automation Practice Repository

## Project Technologies

I employed Eclipse IDE for developing the project. The Eclipse project file is part of the repository.

The project deploys its dependencies using Maven. A list of required Maven dependencies are listed below. These should be downloaded when the project is lauched via the pom XML.

I chose to employ Cucumber/Gherkin as a BDD tool for Junit testing on top of Selenium WebDriver for GUI page interactions (employing the Page Object Model) and Rest Assured for API calls.

Firefox is required as the project uses only the Firefox Gecko Driver at present. Both Mac and Windows versions included and OS will be determined on launch.

### Maven Dependencies

* Selenium WebDriver v 3.9.1
* Cucumber JAVA v 1.1.8
* Cucumber Junit v 1.1.8
* Apache Commons IO v 2.5
* Hamcrest All v 1.3
* Junit v 4.12
* Rest Assured v 3.0.7
* Cucumber Reporting v 3.14.0

## Launching Tests

The TestRunner Class, located in the tests directory, will launch both the GUI and API tests and Cucumber Reports once completed in order to display results. This can be launched a stand-alone Junit test once the Maven resources have been downloaded or ran directly from within Eclipse.

The output Report will be launched in the system default browser.

## Folder Structure

I've arranged the folders in the following manner:

* com
  * automation
    * dependencies -- Abstract Classes for API and WebDriver, Cucumber Report and additional resources
    * pageObject -- Page Object Classes and base abstract Page Object Class
    * stepDefinitions -- Cucumber Step Definitions for API and GUI functions
    * tests -- Junit Test Runner Classes
* binaries -- WebDriver Gecko Driver for Windows and Mac
* Feature -- Gherkin Feature files with Scenarios for API and GUI tests
    
Additionally the following folders will be output (defined by values in the project.properties file):

* Report -- Cucumber Report output from project run
* FailureScreenshots -- Screenshots of any failures. These are held separately from the screenshots embedded in the Report output

## Additional Configuration

The following parameters can be set using the project.properties file:

* use_browser -- The Web Browser to use with WebDriver tests. Currently runs with Firefox only as defined in Browser enum Class. Default: Firefox
* mac_firefox_driver_location -- Location of Geckodriver binary for Mac. Default: ./binaries/geckodriver
* win_firefox_driver_location -- Location of Geckodriver binary for Windows. Default: ./binaries/geckodriver.exe
* api_url -- URL for API functions to use as base. Default: https://reqres.in
* gui_url -- URL for GUI functions to use as base. Default: http://automationpractice.com
* screenshot_output_directory -- Directory to output stand along failure screenshots to. Default: ./FailureScreenshots/
* report_output_directory -- Directory to output run report to. Default: Report

## Future Enhancements

Given more time and further scope I would probably look to do the following:

* Follow up on comments contained within source code where I have marked areas I would like to improve upon, such as including further assertions within tests
* Further refactoring to split some functionality and increase reusability of methods within the project
* Investigate JSON Path further to improve API call methods and reduce code repetition
* Include Parallel testing and support further Browsers using Remote WebDriver or similar implementation
