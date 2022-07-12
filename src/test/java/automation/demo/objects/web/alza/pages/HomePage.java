package automation.demo.objects.web.alza.pages;

import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.WebComponent;
import automation.demo.objects.web.alza.CategoryPage;

public class HomePage extends CategoryPage {

    // MODAL & COOKIES
    @Web(xpath = "//div[@id=\"alzaDialog\"]/div[@class=\"close\"]")
    private WebComponent modalDialogCloseButton;

    @Web(xpath = "//a[@data-action-id-value=\"1023\"]")
    private WebComponent cookiesAcceptAllButton;

    // METHODS
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public HomePage closeModalDialog() {
        if (modalDialogCloseButton.isDisplayed())
            modalDialogCloseButton.click();

        return this;
    }

    public HomePage confirmCookies() {
        if (cookiesAcceptAllButton.isDisplayed())
            cookiesAcceptAllButton.click();

        return this;
    }

}
