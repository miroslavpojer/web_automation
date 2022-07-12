package automation.demo.tests.web.alza.shoppingcart;

import automation.demo.WebTest;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.objects.web.alza.pages.HomePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_02 extends WebTest {

    /**
     * Goal: User sees shopping cart button with one item in from Home Page.
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
            // WHEN The user adds goods in to Shopping Cart
                .findGoods("Dell Vostro 3510")
                .addItemToCart("4")
            // THEN The user sees Shopping cart button in not empty state
            // AND The Shopping Cart button shows total price of goods in Shopping Cart.
                .checkShoppingCartButtonValues(1, 26590);

//        webDriver.stopScreenRecording();
    }

}
