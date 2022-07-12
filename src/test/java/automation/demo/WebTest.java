package automation.demo;

import automation.demo.driver.WebTestClass;
import automation.demo.driver.driver.web.WebDriver;
import automation.demo.driver.driver.web.WebSession;
import org.slf4j.Logger;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

public abstract class WebTest extends WebTestClass {
    public static final String SMOKE = "smoke";
    public static final String ALTERNATIVE = "alternative";
    public static final String NEGATIVE = "negative";
    public static final String UI = "ui";
    public static final String PERFORMANCE = "performance";

    // move to correct child
    public static final String BASKET = "basket";
    public static final String LOGIN = "login";
    public static final String FEATURE3 = "feature3";

    private HashMap<String, WebDriver> webDrivers = new HashMap<String, WebDriver>();
    private Logger logger;
    public HashMap<String, WebDriver> getWebDrivers() { return this.webDrivers; }

    public WebDriver getWebDriver(String testClassName) {
        WebDriver webDriver = new WebSession().initDriver();
        this.webDrivers.put(testClassName, webDriver);
        return webDriver;
    }

    @AfterClass(alwaysRun = true)
    public void reset() {
        this.reset(this.getClass().getName());
    }

    @AfterSuite(alwaysRun = true)
    public void resetAll()
    {
        for(Map.Entry<String, WebDriver> entry : this.webDrivers.entrySet()) {
            WebDriver webDriver = entry.getValue();
            if (webDriver != null)
                webDriver.reset();
        }
    }

    public void reset(String testClassName) {
        if (this.webDrivers.containsKey(testClassName)) {
            if (this.webDrivers.get(testClassName) != null) {
                this.webDrivers.get(testClassName).reset();
                this.webDrivers.remove(testClassName);
            }
        }
    }
}
