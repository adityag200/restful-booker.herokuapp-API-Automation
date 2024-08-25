package StepDefinitions;

import Constants.Endpoints;
import RequestPOJO.BookingDates;
import RequestPOJO.CreateBooking;
import ResponsePOJO.CreateBookingResponse;
import Utils.ConfigReader;
import Utils.ExcelUtils;
import Utils.ExtentReportsUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;

import static StepDefinitions.generateTokenSD.keyValue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateBookingSD{
    private static final Logger log = LogManager.getLogger(CreateBookingSD.class);
    List<CreateBooking> createBooking;
    CreateBookingResponse createBookingResponse;
    BookingDates bookingDates;
    JsonPath jsonPath;
    private RequestSpecification requestSpecification = given().baseUri(ConfigReader.getInstance().getProperty("BASE_URI"))
            .config(RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()));

    @Given("user validates the token is generated")
    public void userValidatesTheTokenIsGenerated() {
        try{
            Assert.assertNotNull(keyValue.get("token"));
            log.info("Token validated successfully");
            ExtentReportsUtils.getTest().pass("Token validated successfully");
        }catch (Exception e){
            log.error("Token not validated, Issues in Token Generation.");
            ExtentReportsUtils.getTest().fail("Token not validated, Issues in Token Generation.");
        }
    }


    @When("user sends a POST request to create a booking")
    public void userSendsAPOSTRequestToCreateABooking() {
        //generating body for create booking

        createBooking = ExcelUtils.readExcel("TestData/TestData.xlsx", "create_booking");

        ExtentReportsUtils.createTest("Create Booking", "User sends a POST request to create a booking");

        Response response = given().spec(requestSpecification).contentType(ConfigReader.getInstance().getProperty("Content-Type"))
                .header("Authorization", "Bearer " + keyValue.get("token"))
                .body(createBooking.get(0))
                .when().post(Endpoints.booking)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = response.jsonPath();

        log.info("Create Booking Response: " + response.asString());
        ExtentReportsUtils.getTest().info("Create Booking Response: " + response.asString());


        // Storing the response in a hashmap
        keyValue.put("bookingid", String.valueOf(jsonPath.getInt("bookingid")));
        keyValue.put("firstname", jsonPath.getString("booking.firstname"));
        keyValue.put("lastname", jsonPath.getString("booking.lastname"));
        keyValue.put("totalprice", String.valueOf(jsonPath.getInt("booking.totalprice")));
        keyValue.put("depositpaid", String.valueOf(jsonPath.getBoolean("booking.depositpaid")));
        keyValue.put("additionalneeds", jsonPath.getString("booking.additionalneeds"));
        keyValue.put("checkin", jsonPath.getString("booking.bookingdates.checkin"));
        keyValue.put("checkout", jsonPath.getString("booking.bookingdates.checkout"));

        // Storing the response in the Reponse POJO class
        createBookingResponse = response.as(CreateBookingResponse.class);
    }

    @And("user should get the booking id")
    public void userShouldGetTheBookingId() {
        try{
            log.info("Booking ID is: " + createBookingResponse.getBookingid());
            Assert.assertTrue("Booking ID is empty", (createBookingResponse.getBookingid()!=0));
        }catch(Exception e){
            log.error("Booking ID is empty");
            ExtentReportsUtils.getTest().fail("Booking ID is empty");
        }
    }

    @And("user validate the response generated")
    public void userValidateTheResponseGenerated() {
        try{
            // Verify the response body
            assertThat(createBookingResponse.getBooking().getFirstname(), is(equalTo(createBooking.get(0).getFirstname())));
            assertThat(createBookingResponse.getBooking().getLastname(), is(equalTo(createBooking.get(0).getLastname())));
            assertThat(createBookingResponse.getBooking().getTotalprice(), is(equalTo(createBooking.get(0).getTotalprice())));
            assertThat(createBookingResponse.getBooking().isDepositpaid(), is(equalTo(createBooking.get(0).isDepositpaid())));
            assertThat(createBookingResponse.getBooking().getBookingdates().getCheckin(), is(equalTo(createBooking.get(0).getBookingdates().getCheckin())));
            assertThat(createBookingResponse.getBooking().getBookingdates().getCheckout(), is(equalTo(createBooking.get(0).getBookingdates().getCheckout())));
            assertThat(createBookingResponse.getBooking().getAdditionalneeds(), is(equalTo(createBooking.get(0).getAdditionalneeds())));
            log.info("Create Booking Response validated successfully");
            ExtentReportsUtils.getTest().pass("Response validated successfully");
        }catch (Exception e){
            log.error("Booking ID is empty");
            ExtentReportsUtils.getTest().fail("Booking ID is empty");
        }
    }
}
