This project is an automated API testing framework built with RestAssured for testing a Booking API.

Project Structure
The project is organized with the following directories and files:

Root Directory:

logs (Directory to store test execution logs)
src (Source code directory)
    main (Contains production code)
        java (Java source code packages)
            Constants (Contains commonly used constants)
            Endpoints (Stores API endpoint URLs)
            RequestPOJO (Plain Old Java Objects representing request payloads)
                BookingDates (POJO defining booking date information)
                CreateBooking (POJO for Create Booking request data)
                GenerateTokenPOJO (POJO for Generate Token request data)
            ResponsePOJO (Plain Old Java Objects representing response payloads)
                BookingDatesResponse (POJO for Booking Dates response data)
                CreateBookingResponse (POJO for Create Booking response data)
                UpdateBookingResponse (POJO for Update Booking response data)
            StepDefinitions (Contains code for defining API test steps using Gherkin syntax)
                CreateBookingSD (Step definitions specific to Create Booking feature)
                DeleteBookingSD (Step definitions specific to Delete Booking feature)
                FetchCreatedBookingSD (Step definitions specific to Get Created Booking feature)
                generateTokenSD (Step definitions specific to Generate Token feature)
                UpdateBookingSD (Step definitions specific to Update Booking feature)
            Utils (Utility classes for common functionalities)
                ConfigReader (Reads configuration properties from a file)
                ExcelUtils (Utility class for interacting with Excel data (if applicable))
                ExtentReportsUtils (Utility class for generating test reports (if applicable))
        resources (Directory for project resources)
            config.properties (Configuration properties file)
            Log4j2.xml (Logging configuration file)
    test (Directory for test code)
        java (Java source code packages)
            runner (Contains test runner class)
                runner (Test runner class file, likely named Runner.java)
        resources (Directory for test resources)
            features (Directory containing Gherkin feature files)
                01_Generate_Token.feature (Feature file for Generate Token tests)
                02_Create_Booking.feature (Feature file for Create Booking tests)
                03_Get_Created_Booking.feature (Feature file for Get Created Booking tests)
                04_Update_Created_Booking.feature (Feature file for Update Created Booking tests)
                05_Delete_Created_Booking.feature (Feature file for Delete Created Booking tests)
testdata (Directory containing test data files)

Ensure you have Java and Maven installed.
Open the project in your IDE (if using one).
Configure any necessary environment variables (based on config.properties).
Run the test runner class (e.g., mvn test or your IDE's equivalent).
Note: This is a general overview based on the project structure. Specific instructions for running the tests might vary depending on additional tools or configurations used in the project.