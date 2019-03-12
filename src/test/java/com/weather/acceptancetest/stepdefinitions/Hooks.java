package com.weather.acceptancetest.stepdefinitions;

import com.weather.acceptancetest.util.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;


public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class.getName());

    public static WebDriver driver;
    public static String baseUrl = "https://weather-acceptance.herokuapp.com/";


    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void openBrowser() throws MalformedURLException {
        LOG.info("Navigate to the homepage");
        driver = DriverFactory.getDriver();
    }


    @After
    /**
     * Add screenshot to the test report , whenever test is failed
     */
    public void addScreenshot(Scenario scenario) {

        if (scenario.isFailed()) {
            try {
                scenario.write("Current web page is " + driver.getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }

        }
        driver = null;
    }
}
