package automation.demo.tests.web.alza.login;

import automation.demo.WebTest;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.objects.web.alza.pages.HomePage;
import org.testng.annotations.Test;

public class TC_04 extends WebTest {

    /**
     * Goal: User can remove item from shopping cart.
     */
    @Test(groups = {SMOKE, LOGIN})
    public void test() {
        WebDriver webDriver = this.getWebDriver(this.getClass().getName());

//        Logger logger = LoggerFactory.getLogger(this.getClass());
//        webDriver.startScreenRecording();

        new HomePage(webDriver)
            // GIVEN The user sees website Home page
            // AND The all cookies are allowed.
            // AND The action dialog is not visible.
                .closeModalDialog()
                .confirmCookies()
            // WHEN The user Logs in with wrong username and password
                .loginwithWrongCredentials("test1@seznam.cz", "wrongPassword")
            // THEN The user sees error information on Log in button location
                .checkInvalidLoginVisibility();

//        webDriver.stopScreenRecording();
    }

}
