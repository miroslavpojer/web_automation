package automation.demo.driver.objects.web;

import automation.demo.driver.driver.DriverManager;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.objects.Component;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebComponent extends Component {

    public WebComponent(DriverManager driverManager) {
        super(driverManager);
    }

    public RemoteWebDriver getDriver() {
        return this.getDriverManager().getDriver();
    }

    @Override
    public WebDriver getDriverManager() {
        return (WebDriver) this.driverManager;
    }
}
