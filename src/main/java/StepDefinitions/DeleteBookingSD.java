package StepDefinitions;

import Constants.Endpoints;

import Utils.ConfigReader;
import Utils.ExtentReportsUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static StepDefinitions.generateTokenSD.keyValue;

public class DeleteBookingSD {
    private static final Logger log = LogManager.getLogger(DeleteBookingSD.class);
    Response response;
    private RequestSpecification requestSpecification = given().baseUri(ConfigReader.getInstance().getProperty("BASE_URI"))
            .config(RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()));

    @When("user sends a DELETE request to delete the created booking")
    public void userSendsADELETERequestToDeleteTheCreatedBooking() {
        // Log the values being used
        log.info("Booking ID: " + keyValue.get("bookingid"));

        ExtentReportsUtils.createTest("Delete Booking", "User sends a DELETE request to delete the created booking");

        // Send the DELETE request
        response = given().spec(requestSpecification)
                .contentType(ConfigReader.getInstance().getProperty("Content-Type"))
                .header("Cookie", "token= " + keyValue.get("token"))
                .when().delete(Endpoints.booking + "/" + Integer.parseInt(keyValue.get("bookingid")))
                .then() // Log the response
                .extract()
                .response();

        // Check the status code and status line
        int statusCode = response.getStatusCode();
        log.info("Response Body: " + response.getBody().asString());
        ExtentReportsUtils.getTest().info("Deleted the Booking");

        Assert.assertEquals(statusCode, 201, "Expected status code 201 but got " + statusCode);
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 201 Created", "Status Line not matching");
    }

    @Then("user validates the booking should be deleted successfully")
    public void userValidatesTheBookingShouldBeDeletedSuccessfully() {
        try{
            response = given().spec(requestSpecification).header("Authorization", "Bearer " + keyValue.get("token"))
                    .when().get(Endpoints.booking +"/"+ keyValue.get("bookingid"))
                    .then().assertThat().statusCode(404).extract().response();
            log.info("Response after validating the response: " + response.asString());
            ExtentReportsUtils.getTest().pass("Booking deleted successfully");
        }
        catch (Exception e){
            log.error("Booking not deleted");
            ExtentReportsUtils.getTest().fail("Booking not deleted");
        }
    }
}
