package com.weather.acceptancetest.util;

import java.util.logging.Logger;

import com.weather.acceptancetest.Config.Config;
import com.weather.acceptancetest.Enums.Browsers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {

    private static final Logger LOG = Logger.getLogger(DriverFactory.class.getSimpleName());
    private static WebDriver driver;

    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            try {
                driver = getBrowser();
            } catch (UnreachableBrowserException e) {
                driver = getBrowser();
            } catch (WebDriverException e) {
                driver = getBrowser();
            } finally {
                Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return driver;
    }

    public static void close() {
        try {
            getDriver().quit();
            driver = null;
            LOG.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            LOG.info("Unable close browser: unreachable browser");
        }
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            close();
        }
    }

    public static WebElement waitForElementVisibility(WebElement elementToWaitFor) {
        return waitForElementVisibility(elementToWaitFor, null);
    }

    public static WebElement waitForElementVisibility(WebElement elementToWaitFor, Integer waitTimeInSeconds) {
        if (waitTimeInSeconds == null) {
            waitTimeInSeconds = 10;
        }

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTimeInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
    }

    public static void waitForTextToBePresent(WebDriver newDriver, String textToAppear, WebElement element) {
        WebDriverWait wait = new WebDriverWait(newDriver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));

    }

    /**
     * creates the browser driver specified in the system property "browser"
     * if no property is set , by default chrome browser driver is created.
     * e.g to run with chrome, pass in the option -Dbrowser=chrome at runtime
     *
     * @return WebDriver
     */
    public static WebDriver getBrowser() {
        Browsers browser;
        WebDriver driver;

        browser = Browsers.getBrowser(Config.browser);


        switch (browser) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        return driver;
    }


}
