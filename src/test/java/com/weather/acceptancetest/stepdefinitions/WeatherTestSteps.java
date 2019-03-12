package com.weather.acceptancetest.stepdefinitions;


import com.weather.acceptancetest.Config.Config;
import com.weather.acceptancetest.pageobjects.Homepage;
import com.weather.acceptancetest.util.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.logging.Logger;


public class WeatherTestSteps implements Config {

    private static final Logger LOG = Logger.getLogger(WeatherTestSteps.class.getName());
    private Homepage homepage;

    @Given("^User launches weather forecast site$")
    public void user_launches_weather_forecast_site() throws Throwable {
        homepage = new Homepage(DriverFactory.getDriver());
    }

    @When("^User type city name (.*)$")
    public void user_type_city_name_Edinburgh(String cityName) throws Throwable {
        homepage.enterCity(cityName);
    }

    @Then("^User should see (\\d+) day forecast$")
    public void user_should_see_day_forecast(int totalDays) throws Throwable {
        Assert.assertEquals(homepage.gettotalDayForecast(), totalDays);
        Assert.assertEquals(homepage.getDayOne(), "Tue");
    }

    @When("^User chose a day (\\d+)$")
    public void user_chose_a_day(int day) throws Throwable {
        homepage.choseADay(day);
    }

    @Then("^User should see hourly weather forecast for selected (\\d+)$")
    public void user_should_see_hourly_weather_forecast_for_selected(int chosenDay) throws Throwable {
        Assert.assertTrue("Hourly forecast not displayed for every 3 hours", homepage.getHourlyForecastTimes(chosenDay));
    }

    @Then("^User should see correct summarised minimum temp and maximum temperatures for selected (\\d+) and hour <hour>$")
    public void user_should_see_correct_summarised_minimum_temp_and_maximum_temperatures_for_selected_and_hour_hour(int selectedDay) throws Throwable {
        Assert.assertTrue("Max Temp Aggergate Incorrect", homepage.isMaxTempDisplayed(selectedDay));
        Assert.assertTrue("Min Temp Aggergate Incorrect", homepage.isMinTempAggregate(selectedDay));
    }


}
