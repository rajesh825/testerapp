package com.weather.acceptancetest.pageobjects;


import java.util.logging.Logger;

import com.weather.acceptancetest.util.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Homepage extends BasePage {

    private static final Logger LOG = Logger.getLogger(Homepage.class.getName());

    By cityId = By.id("city");
    By totalDaysCss = By.cssSelector("#root > div > div > div.summary > span:nth-child(1) > span.name");
    By dayOneCss = By.cssSelector("#root > div > div:nth-child(2) > div.summary > span:nth-child(1) > span.name");

    String dayFirstSection = "#root > div > div:nth-child(";
    String daySecondSection = ") > div.summary > span:nth-child(1)";

    String hourFirstSection = "#root > div > div:nth-child(";
    String hourSecondSection = ") > div.details > div:nth-child(1) > span:nth-child(1) > span";


    String hourlyForecastFirstSection = "#root > div > div:nth-child(";
    String hourlyForecastSecondSection = ") > div.details > div > span:nth-child(1) > span";
    String hourForecastFirstSection = "#root > div > div:nth-child(";

    String hourForecastSecondSection = ") > div.details > div:nth-child(";
    String hourForecastThirdSection = ") > span:nth-child(1) > span";


    public Homepage(WebDriver driver) {
        super(driver);
        visit(baseUrl + "/");

        DriverFactory.waitForTextToBePresent(driver, "Five Day Weather Forecast", findElement(By.cssSelector("h1")));

    }

    public void enterCity(String city) {
        try {
            clear(cityId);
            type(city, cityId);
        } catch (RuntimeException e) {
            takeScreenshot(e, "searchError");
        }
    }

    public int gettotalDayForecast() {
        return findElements(totalDaysCss).size();
    }

    public String getDayOne() {
        return waitUntilElemenetLocated(dayOneCss).getText();
    }


    public void choseADay(int day) {
        int daySelector = day + 1;
        clickOnElement(By.cssSelector(dayFirstSection + daySelector + daySecondSection));
        DriverFactory.waitForElementVisibility(findElement(By.cssSelector(hourFirstSection + daySelector + hourSecondSection)));


    }

    public boolean getHourlyForecastTimes(int day) {
        boolean result = false;
        int daySelector = day + 1;
        int totalHourlyForecasts = findElements(By.cssSelector(hourlyForecastFirstSection + daySelector + hourlyForecastSecondSection)).size();
        for (int i = 1; i < totalHourlyForecasts - 1; i++) {

            int firstHour = Integer.parseInt(findElement(By.cssSelector(hourForecastFirstSection + daySelector + hourForecastSecondSection + i + hourForecastThirdSection)).getText());
            int secondHour = Integer.parseInt(findElement(By.cssSelector(hourForecastFirstSection + daySelector + hourForecastSecondSection + (i + 1) + hourForecastThirdSection)).getText());

            if (secondHour - firstHour != 300) {
                break;
            } else {
                result = true;
            }

        }
        return result;
    }

    //TO-DO
    // Need to Assess if max, min Temp Displayed methods Can be combined into Single Method

    /**
     * @param day
     * @return Return boolean indicating whether Max Temperature displayed is Correct
     */
    public boolean isMaxTempDisplayed(int day) {

        boolean result = false;
        int daySelector = day + 1;

        //Max Temp Aggregate displayed in website
        String maxTempAggregate = findElement(By.cssSelector("#root > div > div:nth-child(" + daySelector + ") > div.summary > span:nth-child(3) > span.max")).getText().split("°")[0];
        int totalHourlyForecasts = findElements(By.cssSelector(hourlyForecastFirstSection + daySelector + hourlyForecastSecondSection)).size();


        int maxTemp = 0;

        for (int i = 1; i <= totalHourlyForecasts; i++) {
            int currentMaxTemp = Integer.parseInt(findElement(By.cssSelector("#root > div > div:nth-child(" + daySelector + ") > div.details > div:nth-child(" + i + ") > span:nth-child(3) > span.max")).getText().split("°")[0]);
            if (currentMaxTemp> maxTemp){
                maxTemp = currentMaxTemp;
            }
        }

        LOG.info(" Max Temperature : " + maxTemp);
        LOG.info("Actual displayed Max Temp  : " + maxTempAggregate);

        if (Integer.parseInt(maxTempAggregate) == maxTemp) {
            result = true;
        }
        return result;

    }

    /**
     * @param day
     * @return Return boolean indicating whether Min Temperature Displayed is Correct
     */
    public boolean isMinTempAggregate(int day) {
        boolean result = false;
        int daySelector = day + 1;

        //Min Temp Aggregate displayed in website
        String minTempAggregate = findElement(By.cssSelector("#root > div > div:nth-child(" + daySelector + ") > div.summary > span:nth-child(3) > span.rmq-5ea3c959.min")).getText().split("°")[0];
        int totalHourlyForecasts = findElements(By.cssSelector(hourlyForecastFirstSection + daySelector + hourlyForecastSecondSection)).size();

        int minTemp = 0;

        for (int i = 1; i <= totalHourlyForecasts; i++) {

            int currentMinTemp = Integer.parseInt(findElement(By.cssSelector("#root > div > div:nth-child(" + daySelector + ") > div.details > div:nth-child(" + i + ") > span:nth-child(3) > span.rmq-5ea3c959.min")).getText().split("°")[0]);
            if (i == 1){
                minTemp = currentMinTemp;
            }

            if(currentMinTemp< minTemp){
                minTemp = currentMinTemp;
            }

        }

        LOG.info(" Min Temperature : " + minTemp);
        LOG.info("Actual displayed Min Temp  : " + minTempAggregate);
        if (Integer.parseInt(minTempAggregate) == minTemp) {
            result = true;
        }

        return result;
    }
}
