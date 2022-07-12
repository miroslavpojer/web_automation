package automation.demo.objects.web.alza.page_sections;

import automation.demo.driver.annotations.WaitUntilPresent;
import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.Page;
import automation.demo.driver.objects.web.WebComponent;
import automation.demo.objects.web.alza.components.buttons.ShoppingCartButton;
import automation.demo.objects.web.alza.dialogs.LoginDialog;
import automation.demo.objects.web.alza.pages.ShoppingCartPage;
import automation.demo.objects.web.alza.pages.CataloguePage;
import automation.demo.objects.web.alza.pages.HomePage;

public class HeaderPageSection extends Page {

    //@Web(xpath = "//div[@id=\"basket\"]", timeout = 5) - explicit timeout - faster !!!
    @WaitUntilPresent
    @Web(id = "logo")
    private WebComponent websiteLogo;
    private ShoppingCartButton shoppingCartButton;
    @Web(id = "edtSearch")
    private WebComponent searchInput;
    @Web(id = "btnSearch")
    private WebComponent searchButton;
    @Web(id = "lblLogin")
    private WebComponent logInButton;


    public HeaderPageSection(WebDriver webDriver) {
        super(webDriver);
        this.shoppingCartButton = new ShoppingCartButton(this.getDriverManager());
    }

    public CataloguePage findGoods(String name) {
        searchInput.sendKeys(name);
        searchButton.click();
        return new CataloguePage(this.getDriverManager());
    }

    public ShoppingCartPage clickOnCartButton() {
        return shoppingCartButton.clickShoppingCartButton();
    }

    public HomePage clickWebsiteLogo() {
        websiteLogo.click();
        return new HomePage(this.getDriverManager());
    }

    public void checkShoppingCartButtonValues(int count, double price) {
        shoppingCartButton.checkValues(count, price);
    }

    public LoginDialog openLoginDialog() {
        this.logInButton.click();
        return new LoginDialog(this.getDriverManager());
    }

}
