package StepDefinitions;

import Constants.Endpoints;
import RequestPOJO.GenerateTokenPOJO;
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
import org.junit.Assert;

import java.util.HashMap;

import static Constants.Endpoints.auth;
import static io.restassured.RestAssured.given;

public class generateTokenSD {

    public static HashMap<String, String> keyValue = new HashMap<>();

    private static final Logger log = LogManager.getLogger(generateTokenSD.class);

    GenerateTokenPOJO generateTokenPOJO;
    private RequestSpecification requestSpecification = given().baseUri(ConfigReader.getInstance().getProperty("BASE_URI"))
            .config(RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()));

    @When("I generate a token")
    public void user_set_get_example_endpoint() {
        //generating body for token
        generateTokenPOJO = new GenerateTokenPOJO(ConfigReader.getInstance().getProperty("username"),
                ConfigReader.getInstance().getProperty("password"));

        ExtentReportsUtils.createTest("Generating Token", "User sends a GET request to generate the token to execute create booking endpoints");


        Response response = given().spec(requestSpecification).contentType(ConfigReader.getInstance().getProperty("Content-Type"))
                .body(generateTokenPOJO)
                .when().post(Endpoints.auth)
                .then().assertThat().statusCode(200).extract().response();

        keyValue.put("token", response.jsonPath().getString("token"));
        log.info("Token: " + keyValue.get("token"));
        ExtentReportsUtils.getTest().info("Token generation Response: " + response.asString());
    }

    @Then("I should get a token")
    public void iShouldGetAToken() {
        try{
            Assert.assertNotNull(keyValue.get("token"));
            ExtentReportsUtils.getTest().pass("Token generated successfully");
        }catch (Exception e) {
            log.error("Token not generated");
            ExtentReportsUtils.getTest().fail("Token not generated");
        }
    }
}
