package com.weather.acceptancetest.stepdefinitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-html-report",},
    tags = {}
)
public class CukesTest {

}
