package automation.demo.driver;

import automation.demo.driver.listeners.WebTestListener;
import automation.demo.driver.driver.web.WebDriver;
import org.testng.annotations.Listeners;

import java.util.HashMap;

@Listeners(value = {WebTestListener.class})
public abstract class WebTestClass {

    public abstract HashMap<String, WebDriver> getWebDrivers();
}