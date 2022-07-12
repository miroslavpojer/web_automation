package automation.demo.objects.web.alza;

import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.Page;
import automation.demo.objects.web.alza.dialogs.LoginDialog;
import automation.demo.objects.web.alza.pages.ShoppingCartPage;
import automation.demo.objects.web.alza.pages.CataloguePage;
import automation.demo.objects.web.alza.pages.HomePage;
import automation.demo.objects.web.alza.page_sections.*;

public abstract class CategoryPage extends Page {

    // HEADER PAGE SECTIONS
    protected NotificationBoxDeliveryPageSection notificationBoxDeliveryPageSection;
    protected InfoMessagePageSection infoMessagePageSection;
    protected NotificationBoxPageSection notificationBoxPageSection;

    // BODY PAGE SECTIONS
    protected LeftBannerPageSection leftBannerPageSection;
    protected RightBannerPageSection rightBannerPageSection;
    protected HeaderPageSection headerPageSection;

    // FOOTER PAGE SECTIONS
    protected FooterPageSection footerPageSection;

    // DIALOGS
    protected LoginDialog loginDialog;


    // METHODS
    public CategoryPage(WebDriver webDriver) {
        super(webDriver);

        // demo examples:
        notificationBoxDeliveryPageSection = new NotificationBoxDeliveryPageSection(webDriver);
        infoMessagePageSection = new InfoMessagePageSection(webDriver);
        notificationBoxPageSection = new NotificationBoxPageSection(webDriver);
        leftBannerPageSection = new LeftBannerPageSection(webDriver);
        rightBannerPageSection = new RightBannerPageSection(webDriver);
        headerPageSection = new HeaderPageSection(webDriver);
        footerPageSection = new FooterPageSection(webDriver);
        loginDialog = new LoginDialog(webDriver);
    }


    // Facade for : TBD class

    // Facade for : HeaderPageSection class
    public ShoppingCartPage openCart() { return this.headerPageSection.clickOnCartButton(); }
    public CataloguePage findGoods(String findValue) { return this.headerPageSection.findGoods(findValue); }
    public HomePage clickWebsiteLogo() { return this.headerPageSection.clickWebsiteLogo(); }
    public void checkShoppingCartButtonValues(int count, double price) {
        this.headerPageSection.checkShoppingCartButtonValues(count, price);
    }
    public LoginDialog loginwithWrongCredentials(String email, String password) {
        LoginDialog lg = this.headerPageSection.openLoginDialog();
        lg = lg.fillLoginForm(email, password);
        lg = lg.clickLoginButton();
        return lg;
    }
}
