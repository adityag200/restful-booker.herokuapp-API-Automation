package StepDefinitions;

import Constants.Endpoints;
import ResponsePOJO.CreateBookingResponse;
import Utils.ConfigReader;
import Utils.ExtentReportsUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;

import static StepDefinitions.generateTokenSD.keyValue;
import static io.restassured.RestAssured.given;

public class FetchCreatedBookingSD {

    private static final Logger log = LogManager.getLogger(FetchCreatedBookingSD.class);
    Response response;
    JsonPath jsonPath;
    private RequestSpecification requestSpecification = given().baseUri(ConfigReader.getInstance().getProperty("BASE_URI"))
            .config(RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()));

    @When("user performs GET request to fetch created booking ids")
    public void userPerformsGETRequestToFetchCreatedBooking() {
       // getting all the booking ids created
        log.info("Fetching all booking ids");

        ExtentReportsUtils.createTest("Fetch Created Booking", "User sends a GET request to fetch all the booking ids created");
        response = given().spec(requestSpecification).header("Authorization", "Bearer " + keyValue.get("token"))
                .when().get(Endpoints.booking)
                .then().assertThat().statusCode(200).extract().response();

        log.info("Total Booking Id Count: " + response.jsonPath().getList("bookingid").size());
        ExtentReportsUtils.getTest().info("Total Booking Id Count: " + response.jsonPath().getList("bookingid").size());
    }

    @Then("user validates the booking created in previous step in the response")
    public void userValidatesTheBookingCreatedInPreviousStepInTheResponse() {
        try{
            List<Integer> BookingIds = response.jsonPath().getList("bookingid");
            Assert.assertTrue(BookingIds.contains(Integer.parseInt(keyValue.get("bookingid"))));
        }catch (Exception e){
            log.error("Booking Id not found in the response");
            ExtentReportsUtils.getTest().fail("Booking Id not found in the response");
        }
    }

    @When("user performs GET request to fetch booking details for specific booking id")
    public void userPerformsGETRequestToFetchBookingDetailsForSpecificBookingId() {
        // Fetching the booking details for the specific booking id
        log.info("Fetching booking details for the specific booking id");

        ExtentReportsUtils.createTest("Fetch Booking Details", "User sends a GET request to fetch booking details for the specific booking id");
        response = given().spec(requestSpecification)
                .when().get(Endpoints.booking + "/" + keyValue.get("bookingid"))
                .then().assertThat().statusCode(200).extract().response();
        response.prettyPeek();

        log.info("Response for specific id: " + response.asString());
        ExtentReportsUtils.getTest().info("Response for specific id: " + response.asString());
    }

    @Then("user validates the booking details in the response")
    public void userValidatesTheBookingDetailsInTheResponse() {
        jsonPath = response.jsonPath();

        ExtentReportsUtils.getTest().info("Validating the booking details");

        try{
            Assert.assertEquals(jsonPath.getString("firstname"), keyValue.get("firstname"));
            Assert.assertEquals(jsonPath.getString("lastname"), keyValue.get("lastname"));
            Assert.assertEquals(jsonPath.getInt("totalprice"), Integer.parseInt(keyValue.get("totalprice")));
            Assert.assertEquals(jsonPath.getBoolean("depositpaid"), Boolean.parseBoolean(keyValue.get("depositpaid")));
            Assert.assertEquals(jsonPath.getString("bookingdates.checkin"), keyValue.get("checkin"));
            Assert.assertEquals(jsonPath.getString("bookingdates.checkout"), keyValue.get("checkout"));
            Assert.assertEquals(jsonPath.getString("additionalneeds"), keyValue.get("additionalneeds"));
            ExtentReportsUtils.getTest().pass("Booking details validated successfully");
        }catch (Exception e){
            log.error("Booking details not found in the response");
            ExtentReportsUtils.getTest().fail("Booking details not found in the response");
        }
    }
}
