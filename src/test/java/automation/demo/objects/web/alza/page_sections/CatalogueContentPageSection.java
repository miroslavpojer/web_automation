package automation.demo.objects.web.alza.page_sections;

import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.Page;
import automation.demo.driver.objects.web.WebComponent;
import automation.demo.objects.web.alza.pages.CrossSellPage;

public class CatalogueContentPageSection extends Page {

    @Web(xpath = "(//a[@class=\"btnk1\"])[%s]")
    protected WebComponent buyButton;

    public CatalogueContentPageSection(WebDriver webDriver) {
        super(webDriver);
    }

    public CrossSellPage addItemToCart(String orderNr) {
        buyButton.getCopyWithParametrizedLocator(orderNr).click();
        return new CrossSellPage(this.getDriverManager());
    }
}
