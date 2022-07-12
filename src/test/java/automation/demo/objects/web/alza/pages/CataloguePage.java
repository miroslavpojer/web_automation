package automation.demo.objects.web.alza.pages;

import automation.demo.driver.driver.web.WebDriver;
import automation.demo.objects.web.alza.CategoryPage;
import automation.demo.objects.web.alza.page_sections.CatalogueContentPageSection;
import automation.demo.objects.web.alza.page_sections.LeftParamatrizationPageSection;

public class CataloguePage extends CategoryPage {

    // BODY PAGE SECTIONS
    protected LeftParamatrizationPageSection leftParamatrizationPageSection;
    protected CatalogueContentPageSection catalogueContentPageSection;

    // METHODS
    public CataloguePage(WebDriver webDriver) {
        super(webDriver);
        leftParamatrizationPageSection = new LeftParamatrizationPageSection(this.getDriverManager());
        catalogueContentPageSection = new CatalogueContentPageSection(this.getDriverManager());
    }

    public CrossSellPage addItemToCart(String orderNr) {
        return catalogueContentPageSection.addItemToCart(orderNr);
    }
}
