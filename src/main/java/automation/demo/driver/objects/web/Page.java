package automation.demo.driver.objects.web;

import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page extends PageObject {
    private final WebDriver webDriver;
    private final Logger logger;

    public Page(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    protected Logger getLogger() { return this.logger; }

    @Override
    public WebDriver getDriverManager() {
        return this.webDriver;
    }

    @Override
    public void doPageScreenshot() {
        if (this.webDriver == null)
            throw new RuntimeException(String.format("No web driver found."));

        this.webDriver.takeScreenshot();
    }

    @Override
    public void doPageScreenshot(String namePrefix) {
        if (this.webDriver == null)
            throw new RuntimeException(String.format("No web driver found."));

        this.webDriver.takeScreenshot(namePrefix);
    }

    @Override
    public void doPageSourceSnapshot() {
        if (this.webDriver == null)
            throw new RuntimeException(String.format("No web driver found."));

        this.webDriver.takePageSourceSnapshot();
    }

    @Override
    public void doPageSourceSnapshot(String namePrefix) {
        if (this.webDriver == null)
            throw new RuntimeException(String.format("No web driver found."));

        this.webDriver.takePageSourceSnapshot(namePrefix);
    }

}
