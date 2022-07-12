package automation.demo.driver;

import automation.demo.driver.driver.DriverManager;
import automation.demo.driver.listeners.TestListener;
import org.testng.annotations.Listeners;

@Listeners(value = {TestListener.class})
public abstract class TestClass {

    public abstract DriverManager getDriverManager();
}