package automation.demo.tests.web.alza.shoppingcart;

import automation.demo.WebTest;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.objects.web.alza.pages.HomePage;
import org.testng.annotations.Test;

public class TC_03 extends WebTest {

    /**
     * Goal: User can remove item from shopping cart.
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
            // AND The user opens the Shopping Cart
                .openCart()
            // THEN The user sees one item in Shopping Cart
                .checkShoppingCartItemCount(1)
            // WHEN The user remove one item from SHopping Cart
                .removeItem(1)
            // THEN The user sees empty Shopping Cart
                .assertEmptyShoppingCart();

//        webDriver.stopScreenRecording();
    }

}
