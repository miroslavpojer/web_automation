package automation.demo.driver.driver;

import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public abstract class DriverManager {
    private final RuntimeException exceptionContainer = new RuntimeException("There were some exceptions found during the test");

    public DriverManager() {
        this.init();
    }

    public abstract RemoteWebDriver getDriver();

    public abstract void init();
    protected abstract void initScreenRecording();
    public abstract void startScreenRecording();
    public abstract void stopScreenRecording();

    public WebElement findComponent(By by, int timeoutInSeconds) {
        if (by == null) {
            throw new RuntimeException("Component has no locator set.");
        }

        Wait<WebDriver> wait = new WebDriverWait(this.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        ExpectedCondition<WebElement> expectation = driver -> {
            try {
                if (driver == null) {
                    return null;
                }
                return this.getDriver().findElement(by);
            } catch (WebDriverException e) {
                return null;
            }
        };
        try {
            return wait.until(input -> expectation.apply(this.getDriver()));
        } catch (TimeoutException e) {
            throw new RuntimeException(String.format("No element found by '%s' after %s seconds timeout", by, timeoutInSeconds));
        }
    }
    public void takeScreenshot() { this.takeScreenshot(""); }

    public void takeScreenshot(String prefix) {
        File screenshot = this.getDriver().getScreenshotAs(OutputType.FILE);
        try {
            if (prefix.isEmpty())
                Files.copy(screenshot.toPath(), Paths.get("target/" + screenshot.getName()));
            else
                Files.copy(screenshot.toPath(), Paths.get("target/" + prefix + "-" + screenshot.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takePageSourceSnapshot() { this.takePageSourceSnapshot(""); }

    public void takePageSourceSnapshot(String prefix) {
        String pageSource = this.getDriver().getPageSource();
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS'.txt'").format(new Date());

        if (!prefix.isEmpty())
            fileName = prefix + "__" + fileName;

        try {
            Files.writeString(Paths.get("target/", fileName), pageSource, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save page source snapshot.", e);
        }
    }

    public void addException(Throwable throwable) {
        this.takeScreenshot();
        this.exceptionContainer.addSuppressed(throwable);
    }

    public RuntimeException getExceptionContainer() {
        return this.exceptionContainer;
    }

}
