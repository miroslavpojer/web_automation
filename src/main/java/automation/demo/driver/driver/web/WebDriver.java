package automation.demo.driver.driver.web;

import automation.demo.driver.Configuration;
import automation.demo.driver.driver.DriverManager;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.io.IOException;
import java.util.function.Supplier;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public abstract class WebDriver extends DriverManager {
    protected RemoteWebDriver remoteWebDriver;
    private ScreenRecorder screenRecorder;

    @Override
    public RemoteWebDriver getDriver() {
        return this.remoteWebDriver;
    }

    public void reset() {
        if (this.remoteWebDriver != null) {
            try{
                this.remoteWebDriver.quit();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void switchTo(WebElement we) {
        this.remoteWebDriver.switchTo().frame(we);
    }

    public void switchToDefaultContent() {
        this.remoteWebDriver.switchTo().defaultContent();
    }

    protected void init(Supplier<RemoteWebDriver> localDriver, Capabilities capabilities) {
        // driver initialization
        if (Configuration.isRemote()) {
            this.remoteWebDriver = new RemoteWebDriver(Configuration.getGridAddress(), capabilities);
        } else {
            while (this.remoteWebDriver == null) {
                this.remoteWebDriver = localDriver.get();

                if (this.remoteWebDriver == null) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        System.out.println("Detected InterruptedException in WebDriver::init.");
                    }
                }
            }
        }
        this.remoteWebDriver.manage().window().maximize();
        this.remoteWebDriver.get(Configuration.getAppUrl());

        // TODO add to config
        //this.remoteWebDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // screen recorder initialization
        this.initScreenRecording();
    }

    protected void initScreenRecording() {
        // The recorded video is saved in the "C:\\users\\<<UserName>>\\Videos" folder.
        GraphicsConfiguration gconfig = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        try {
            this.screenRecorder = new ScreenRecorder(gconfig,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                            ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f,
                            KeyFrameIntervalKey, (int) (15 * 60)),
                    new Format(MediaTypeKey, MediaType.VIDEO,
                            EncodingKey,"black", FrameRateKey, Rational.valueOf(30)), null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

    }

    public void startScreenRecording() {
        try {
            this.screenRecorder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopScreenRecording() {
        try {
            this.screenRecorder.stop();
        } catch (IOException e) {
            throw new RuntimeException("Failed Screen recorder stop.", e);
        }
    }

}
