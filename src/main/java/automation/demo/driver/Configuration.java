package automation.demo.driver;

import automation.demo.driver.annotations.web.Web;
import automation.demo.driver.driver.web.*;
import automation.demo.driver.objects.Component;
import automation.demo.driver.objects.web.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Configuration {

    public static ApplicationType getApplicationType() {
        try {
            return ApplicationType.valueOf(System.getProperty("applicationType", "ANDROID"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("You have to specify 'applicationType' system variable.");
        }
    }

    public static Browser getBrowser() {
        try {
            return Browser.valueOf(System.getProperty("browser", "CHROME").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("You have to specify 'browser' system variable.");
        }
    }

    public static String getAppUrl() {
        String appUrl = System.getProperty("appUrl");
        if (appUrl == null) {
            throw new RuntimeException("You have to set 'appUrl' system variable.");
        }
        return appUrl;
    }

    public static URL getGridAddress() {
        try {
            return new URL(System.getProperty("gridAddress", "http://localhost:4444/wd/hub"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isRemote() {
        return "remote".equalsIgnoreCase(System.getProperty("webStartup", "local"));
    }

    public static int componentTimeoutSeconds() {
        String timeout = System.getProperty("componentTimeoutSeconds");
        if (timeout == null)
            return -1;
        else {
            return Integer.parseInt(timeout);
        }
    }

    public enum Browser {
        CHROME {
            @Override
            public Chrome init() {
                return new Chrome();
            }
        };

        public abstract WebDriver init();
    }


    public enum ApplicationType {
        WEB {
            @Override
            public <T extends WebElement> void processAnnotations(Map<Class<? extends Annotation>, Annotation> annotationMap, Component component) {
                WebComponent webComponent = (WebComponent) component;
                Annotation webAnnotation = annotationMap.get(Web.class);
                if (webAnnotation == null) {
                    return;
                }
                Web web = (Web) webAnnotation;

                // Note: logic ordered from the most used By way (expected most usage)
                int timeout = web.timeout();
                // 0 and negative values are not accepted
                if (timeout > 0) {
                    webComponent.setFindComponentTimeout(timeout);
                }
                String xpath = web.xpath();
                if (!xpath.isEmpty()) {
                    webComponent.setFindComponentBy(By.xpath(xpath));
                    return;
                }
                String id = web.id();
                if (!id.isEmpty()) {
                    webComponent.setFindComponentBy(By.id(id));
                    return;
                }
                String name = web.name();
                if (!name.isEmpty()) {
                    webComponent.setFindComponentBy(By.name(name));
                    return;
                }
                String linkText = web.linkText();
                if (!linkText.isEmpty()) {
                    webComponent.setFindComponentBy(By.linkText(linkText));
                    return;
                }
                String partialLinkText = web.partialLinkText();
                if (!partialLinkText.isEmpty()) {
                    webComponent.setFindComponentBy(By.partialLinkText(partialLinkText));
                    return;
                }
                String tagName = web.tagName();
                if (!tagName.isEmpty()) {
                    webComponent.setFindComponentBy(By.tagName(tagName));
                    return;
                }
                String className = web.className();
                if (!className.isEmpty()) {
                    webComponent.setFindComponentBy(By.className(className));
                    return;
                }
                String cssSelector = web.cssSelector();
                if (!cssSelector.isEmpty()) {
                    webComponent.setFindComponentBy(By.cssSelector(cssSelector));
                    return;
                }

                throw new RuntimeException("@Web annotation must have at least one parameter filled in.");
            }
        };

        public boolean is(ApplicationType applicationType) {
            return this == applicationType;
        }

        public abstract <T extends WebElement> void processAnnotations(Map<Class<? extends Annotation>, Annotation> annotationMap, Component component);
    }
}
