package automation.demo.driver.driver.web;

import automation.demo.driver.Configuration;

public class WebSession {

    public WebDriver initDriver() {
        return Configuration.getBrowser().init();
    }
}
