package automation.demo.driver.objects;

import automation.demo.driver.factories.PageFactory;
import automation.demo.driver.driver.DriverManager;

public abstract class PageObject implements IPage {

    public PageObject(DriverManager driverManager) {
        PageFactory.processPageObjects(this, driverManager);
    }

    public abstract DriverManager getDriverManager();

    public abstract void doPageScreenshot();
    public abstract void doPageScreenshot(String namePrefix);

    public abstract void doPageSourceSnapshot();
    public abstract void doPageSourceSnapshot(String namePrefix);
}
