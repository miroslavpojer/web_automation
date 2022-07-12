package automation.demo.tests.web.alza.shoppingcart;

import automation.demo.WebTest;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.objects.web.alza.pages.HomePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class TC_01 extends WebTest {

    /**
     * Goal: User sees empty cart in first access to eshop website.
     */
    @Test(groups = {SMOKE, BASKET})
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
                // WHEN The user clicks on Shopping cart button
                .openCart()
                // THEN The user sees Shopping Cart page
                //AND The Shopping cart is empty.
                .assertEmptyShoppingCart();

//        webDriver.stopScreenRecording();
    }

}
