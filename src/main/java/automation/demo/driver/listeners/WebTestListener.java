package automation.demo.driver.listeners;

import automation.demo.driver.WebTestClass;
import automation.demo.driver.driver.web.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class WebTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebTestClass testConfiguration = (WebTestClass) result.getInstance();
        String webDriverTestClass = result.getMethod().getTestClass().getName();

        WebDriver webDriver = testConfiguration.getWebDrivers().get(webDriverTestClass);
        webDriver.takeScreenshot("TestFailure - " + webDriverTestClass);
        webDriver.stopScreenRecording();
        webDriver.takePageSourceSnapshot("TestFailure - " + webDriverTestClass);

        processExceptionContainer(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        processExceptionContainer(result);
    }

    private void processExceptionContainer(ITestResult result) {
        WebTestClass testConfiguration = (WebTestClass) result.getInstance();
        String testClassName = result.getTestClass().getName().toString();

        if (testConfiguration.getWebDrivers().containsKey(testClassName) &&
                (testConfiguration.getWebDrivers().get(testClassName) != null )) {

            RuntimeException exception = testConfiguration.getWebDrivers().get(testClassName)
                    .getExceptionContainer();
            if (!result.isSuccess()) {
                exception.addSuppressed(result.getThrowable());
            }
            if (exception.getSuppressed().length != 0) {
                result.setStatus(ITestResult.FAILURE);
            }
            result.setThrowable(exception);
        }
    }
}
