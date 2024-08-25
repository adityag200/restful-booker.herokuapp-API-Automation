package StepDefinitions;

import RequestPOJO.BookingDates;
import RequestPOJO.CreateBooking;
import ResponsePOJO.UpdateBookingResponse;
import Utils.ConfigReader;
import Utils.ExcelUtils;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static StepDefinitions.generateTokenSD.keyValue;
import static io.restassured.RestAssured.given;

public class UpdateBookingSD {

    private static final Logger log = LogManager.getLogger(UpdateBookingSD.class);
    List<CreateBooking> createBooking;
    UpdateBookingResponse updateBookingResponse;
    BookingDates bookingDates;
    Response response;
    private RequestSpecification requestSpecification = given().baseUri(ConfigReader.getInstance().getProperty("BASE_URI"))
            .config(RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()));

    @When("user sends a PUT request to update the created booking")
    public void userSendsAPUTRequestToUpdateTheCreatedBooking() {
        // Generating body for create booking

        createBooking = ExcelUtils.readExcel("TestData/TestData.xlsx", "update_booking");

        ExtentReportsUtils.createTest("Update Booking", "User sends a PUT request to update the created booking");

        response = given().spec(requestSpecification)
                .contentType(ConfigReader.getInstance().getProperty("Content-Type"))
                .accept(ConfigReader.getInstance().getProperty("Accept"))
                .header("Cookie", "token=" + keyValue.get("token"))
                .body(createBooking.get(2)).log().body()
                .when().put("booking/" + keyValue.get("bookingid"))
                .then().assertThat().statusCode(200).extract().response();

        log.info("Response: " + response.asString());
        ExtentReportsUtils.getTest().info("Response: " + response.asString());
        updateBookingResponse = response.as(UpdateBookingResponse.class);
    }

    @Then("user validates the booking should be updated successfully")
    public void userValidatesTheBookingShouldBeUpdatedSuccessfully() {
        ExtentReportsUtils.getTest().info("Validating the booking update");
        try {
            Assert.assertNotEquals(updateBookingResponse.getFirstname(), keyValue.get("firstname"), "FirstName are still matching");
            Assert.assertNotEquals(updateBookingResponse.getLastname(), keyValue.get("lastname"), "LastName are still matching");
            Assert.assertNotEquals(updateBookingResponse.getTotalprice(), Integer.parseInt(keyValue.get("totalprice")), "TotalPrice are still matching");
            Assert.assertNotEquals(updateBookingResponse.isDepositpaid(), Boolean.parseBoolean(keyValue.get("depositpaid")), "DepositPaid are still matching");
            Assert.assertNotEquals(updateBookingResponse.getBookingdates(), bookingDates, "BookingDates are still matching");
            Assert.assertNotEquals(updateBookingResponse.getAdditionalneeds(), keyValue.get("additionalneeds"), "AdditionalNeeds are still matching");
            ExtentReportsUtils.getTest().pass("Booking update validated successfully");
        } catch (AssertionError e) {
            log.error("Booking update validation failed: " + e.getMessage());
            ExtentReportsUtils.getTest().fail("Booking update validation failed: " + e.getMessage());
            throw e;
        }
    }

    @When("user sends a Patch request to partially update the created booking")
    public void userSendsAPatchRequestToPartiallyUpdateTheCreatedBooking() {
        ExtentReportsUtils.createTest("Partial Update Booking", "User sends a PATCH request to partially update the created booking");

        // Generating body for partial update
        Map<String, Object> partialUpdate = new HashMap<>();
        partialUpdate.put("firstname", "Ms.Sara");
        partialUpdate.put("lastname", "SR Tendulkar");

        response = given().spec(requestSpecification)
                .contentType(ConfigReader.getInstance().getProperty("Content-Type"))
                .accept(ConfigReader.getInstance().getProperty("Accept"))
                .header("Cookie", "token=" + keyValue.get("token"))
                .body(partialUpdate).log().body()
                .when().patch("booking/" + keyValue.get("bookingid"))
                .then().assertThat().statusCode(200).extract().response();

        log.info("Partial Update Response: " + response.asString());
        ExtentReportsUtils.getTest().info("Partial Update Response: " + response.asString());
        updateBookingResponse = response.as(UpdateBookingResponse.class);
    }

    @Then("user validates the booking should be partially updated successfully")
    public void userValidatesTheBookingShouldBePartiallyUpdatedSuccessfully() {
        ExtentReportsUtils.getTest().info("Validating the partial booking update");
        try {
            Assert.assertEquals(updateBookingResponse.getFirstname(), "Ms.Sara", "FirstName did not update correctly");
            Assert.assertEquals(updateBookingResponse.getLastname(), "SR Tendulkar", "LastName did not update correctly");
            ExtentReportsUtils.getTest().pass("Partial booking update validated successfully");
        } catch (AssertionError e) {
            ExtentReportsUtils.getTest().fail("Partial booking update validation failed: " + e.getMessage());
            throw e;
        }
    }
}
