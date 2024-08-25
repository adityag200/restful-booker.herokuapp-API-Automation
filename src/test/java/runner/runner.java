package runner;

import Utils.ExtentReportsUtils;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "StepDefinitions",
        tags = "@NAGP",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-reports/cucumber.json",
        }
)
public class runner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setup() {
        ExtentReportsUtils.getInstance();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        ExtentReportsUtils.getInstance().flush();
    }
}
