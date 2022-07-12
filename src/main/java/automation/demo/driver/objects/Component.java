package automation.demo.driver.objects;

import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.factories.PageFactory;
import automation.demo.driver.Configuration;
import automation.demo.driver.driver.DriverManager;
import automation.demo.driver.objects.web.WebComponent;
import org.openqa.selenium.*;

import java.util.List;

public abstract class Component implements WebElement, IPage {
    protected DriverManager driverManager;
    protected static final int COMPONENT_FIND_TIMEOUT_IN_SECONDS = 60;
    protected By by;

    private int componentTimeout = -1;

    public Component(DriverManager driverManager) {
        this.driverManager = driverManager;
        PageFactory.processComponents(this, driverManager);
    }

    public abstract DriverManager getDriverManager();

    public WebElement findComponent() {
        // get value from system variables
        int timeout = Configuration.componentTimeoutSeconds();

        // if defined, override by component specific value
        if (timeout <= 0)
            timeout = this.componentTimeout;

        if (timeout > 0)
            return this.getDriverManager().findComponent(this.by, timeout);
        else
            return this.getDriverManager().findComponent(this.by, COMPONENT_FIND_TIMEOUT_IN_SECONDS);
    }

    public WebElement findComponent(int timeoutInSeconds) {
        if (this.by.toString().contains("%"))
            throw new RuntimeException(String.format("Wrong definition of 'By' condition: '%s'", this.by.toString()));

        return this.getDriverManager().findComponent(this.by, timeoutInSeconds);
    }

    public void setFindComponentBy(By by) {
        this.by = by;
    }

    public void setFindComponentTimeout(int componentTimeout) {
        this.componentTimeout = componentTimeout;
    }

    public WebComponent getCopyWithParametrizedLocator(Object ... arguments) {
        WebComponent thisCopy = new WebComponent(this.getDriverManager());

        String locator = String.format(this.by.toString().split(": ")[1], arguments);
        if (this.by instanceof By.ByXPath) {
            thisCopy.setFindComponentBy(By.xpath(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ByClassName) {
            thisCopy.setFindComponentBy(By.className(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ById) {
            thisCopy.setFindComponentBy(By.id(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ByName) {
            thisCopy.setFindComponentBy(By.name(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ByLinkText) {
            thisCopy.setFindComponentBy(By.linkText(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ByPartialLinkText) {
            thisCopy.setFindComponentBy(By.partialLinkText(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ByTagName) {
            thisCopy.setFindComponentBy(By.tagName(locator));
            return thisCopy;
        }
        if (this.by instanceof By.ByCssSelector) {
            thisCopy.setFindComponentBy(By.cssSelector(locator));
            return thisCopy;
        }

        throw new RuntimeException(String.format("Unexpected 'By' instance: '%s'", this.by.toString()));
    }

    /*
     * *********************** WebElement implementation ***********************
     */

    @Override
    public void click() {
        findComponent().click();
    }

    @Override
    public void submit() {
        findComponent().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        findComponent().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        findComponent().clear();
    }

    @Override
    public String getTagName() {
        return findComponent().getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return findComponent().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return findComponent().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return findComponent().isEnabled();
    }

    @Override
    public String getText() {
        return findComponent().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return findComponent().findElements(by);
    }

    public List<WebElement> findElements() {
        return findComponent().findElements(this.by);
    }

    @Override
    public WebElement findElement(By by) {
        return findComponent().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        try {
            return findComponent(2).isDisplayed();
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public Point getLocation() {
        return findComponent().getLocation();
    }

    @Override
    public Dimension getSize() {
        return findComponent().getSize();
    }

    @Override
    public Rectangle getRect() {
        return findComponent().getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return findComponent().getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return findComponent().getScreenshotAs(target);
    }
}
