package automation.demo.objects.web.alza.components.buttons;

import automation.demo.driver.annotations.WaitUntilPresent;
import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.Page;
import automation.demo.driver.objects.web.WebComponent;
import automation.demo.objects.web.alza.pages.ShoppingCartPage;

public class ShoppingCartButton extends Page {

    @WaitUntilPresent
    @Web(id = "basketLink")
    private WebComponent shoppingCartButton;
    @WaitUntilPresent
    @Web(className = "count")
    private WebComponent shoppingCartCount;
    @WaitUntilPresent
    @Web(id = "price")
    private WebComponent shoppingCartPrice;

    public ShoppingCartButton(WebDriver webDriver) {
        super(webDriver);
    }

    public ShoppingCartPage clickShoppingCartButton() {
        shoppingCartButton.isDisplayed();
        shoppingCartButton.click();
        return new ShoppingCartPage(this.getDriverManager());
    }

    public void checkValues(int exptectedCount, double expectedPrice) {
        int recCount = Integer.parseInt(shoppingCartCount.getText().replaceAll("[^0-9.,]", ""));
        double recPrice = Double.parseDouble(shoppingCartPrice.getText().replaceAll("[^0-9.,]", ""));

        if ((exptectedCount == recCount) && (expectedPrice == recPrice)) {
            return;
        } else {
            throw new RuntimeException(String.format("Expected [count|price] : [%d|%f]. Received : [%d|%f]",
                    exptectedCount, expectedPrice, recCount, recPrice));
        }
    }
}
