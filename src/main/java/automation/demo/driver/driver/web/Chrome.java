package automation.demo.driver.driver.web;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;

public class Chrome extends WebDriver {

    @Override
    public void init() {
        // TODO: parameterize options
        System.setProperty("webdriver.chrome.driver",
                Paths.get(System.getProperty("user.dir"), "drivers", "chromedriver.exe").toString());

        ChromeOptions chromeOptions = new ChromeOptions();
        super.init(ChromeDriver::new, chromeOptions);
    }
}
