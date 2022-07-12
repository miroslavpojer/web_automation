package automation.demo.objects.web.alza.pages;

import automation.demo.driver.annotations.WaitUntilPresent;
import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.Page;
import automation.demo.driver.objects.web.WebComponent;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShoppingCartPage extends Page {

    @WaitUntilPresent
    @Web(className = "obuttons")
    private WebComponent continueShoppingButton;

    @Web(className = "floor")
    private WebComponent viewProductCatalogue;
    @Web(xpath = "//tr[@class=\"ui-draggable\" and @data-code]")
    private WebComponent shoppingCartItem;
    @Web(xpath = "(//div[@class = \"countMinus\"])[%s]")
    private WebComponent shoppingCartItemAmountMinusButton;

    // Remove dialog
    @Web(xpath = "//div[@class=\"buttons\"]/span[contains(@class, \"ok\")]")
    private WebComponent removeDialogOKButton;


    public ShoppingCartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public HomePage clickOnContinueShoppingButton() {
        continueShoppingButton.click();
        return new HomePage(this.getDriverManager());
    }

    public ShoppingCartPage assertEmptyShoppingCart() {
        if (!viewProductCatalogue.isDisplayed()) {
            throw new RuntimeException("Shopping cart is not empty");
        }

        return this;
    }

    public ShoppingCartPage checkShoppingCartItemCount(int expectedItems) {
        List<WebElement> cartItems = shoppingCartItem.findElements();
        this.getLogger().info(String.format("Debug: %d", cartItems.size()));
        if (expectedItems != cartItems.size())
            throw new RuntimeException(String.format("Expected visible '%d' shopping cart items. Received '%d' items.",
                    expectedItems, cartItems.size()));

        return this;
    }

    public ShoppingCartPage removeItem(int itemIndex) {
        WebElement e = shoppingCartItemAmountMinusButton.getCopyWithParametrizedLocator(itemIndex);
        e.click();
        removeDialogOKButton.isDisplayed();
        removeDialogOKButton.click();
        e.isDisplayed();
        return this;
    }

}
