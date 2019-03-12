package com.weather.acceptancetest.pageobjects;

import com.weather.acceptancetest.Config.Config;
import com.weather.acceptancetest.util.DriverFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage implements Config {
    private static final Logger LOG = Logger.getLogger(BasePage.class.getName());

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public void clickOnElement(By locator) {
        findElement(locator).click();
    }

    public void type(String inputText, By locator) {
        findElement(locator).sendKeys(inputText);

    }

    public WebElement waitUntilElemenetLocated(By locator) {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void clear(By locator) {
        findElement(locator).clear();
    }

    public void click(By locator) {
        findElement(locator).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void visit(String url) {
        driver.get(url);
    }

    /**
     * returns the text from the provided DOM locator using Selenium's getText method
     *
     * @param locator
     * @return String representing the inner HTML of the DOM element (MW: To check it is actually inner-text
     */
    public String getText(By locator) {
        return findElement(locator).getText();
    }

    /**
     * searches for the provided text in the current page Url
     *
     * @param searchString partial text to locate within the page url
     * @throws IllegalStateException
     */
    public void valCorrectPage(String searchString) {
        if (!getPageTitle().contains(searchString)) {
            throw new IllegalStateException("This is not " + searchString + " .The actual Url is: " + getUrl());
        }
    }

    protected void takeScreenshot(RuntimeException e, String fileName) {
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(fileName + ".png"));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage(), ioe);
        }
        throw e;
    }

}
