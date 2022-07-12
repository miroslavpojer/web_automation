package automation.demo.objects.web.alza.dialogs;

import automation.demo.driver.annotations.WaitUntilPresent;
import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.web.Page;
import automation.demo.driver.objects.web.WebComponent;

public class LoginDialog extends Page {

    @Web(id = "loginIframe")
    private WebComponent loginIframe;
    @Web(xpath = "//*[@id = \"userName\"]")
    private WebComponent userNameInput;
    @Web(xpath = "//*[@id = \"password\"]")
    private WebComponent passwordInput;
    @Web(xpath = "//*[@id = \"btnLogin\"]")
    private WebComponent loginButton;
    @Web(xpath = "//*[contains(@class, \"invalid\")]")
    private WebComponent invalidLoginButton;

    public LoginDialog(WebDriver webDriver) {
        super(webDriver);
    }

    public LoginDialog fillLoginForm(String email, String password) {
        this.getDriverManager().switchTo(this.loginIframe.findComponent());

        this.userNameInput.sendKeys(email);
        this.passwordInput.sendKeys(password);

        this.getDriverManager().switchToDefaultContent();
        return this;
    }

    public LoginDialog clickLoginButton() {
        this.getDriverManager().switchTo(this.loginIframe.findComponent());
        this.loginButton.click();
        this.getDriverManager().switchToDefaultContent();
        return this;
    }

    public LoginDialog checkInvalidLoginVisibility() {
        return this.checkInvalidLoginVisibility(true);
    }
    public LoginDialog checkInvalidLoginVisibility(boolean expected) {
        try {
            this.getDriverManager().switchTo(this.loginIframe.findComponent());

            if (this.invalidLoginButton.isDisplayed())
                if (expected)
                    return this;
                else
                    throw new RuntimeException("Not expected invalid login button is visible.");
            else if (expected)
                throw new RuntimeException("Expected invalid login button is not visible.");
            else
                return this;
        } finally {
            this.getDriverManager().switchToDefaultContent();
        }
    }
}
