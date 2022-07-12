package automation.demo.driver.listeners;

import automation.demo.driver.TestClass;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        TestClass testConfiguration = (TestClass) result.getInstance();
        testConfiguration.getDriverManager().takeScreenshot();
        processExceptionContainer(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        processExceptionContainer(result);
    }

    private void processExceptionContainer(ITestResult result) {
        TestClass testConfiguration = (TestClass) result.getInstance();
        RuntimeException exception = testConfiguration.getDriverManager()
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
