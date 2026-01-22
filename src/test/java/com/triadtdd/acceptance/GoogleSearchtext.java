package com.triadtdd.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleSearchtext {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        try {
            driver = new ChromeDriver(options);
        } catch(Exception e) {
            System.err.println("Error initializing firefox: " + e.getMessage());
            throw e;
        }

    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Should find search term in page title after Google search")
    void shouldPerformGoogleSearch() {
        driver.get("https://www.google.com.br");

        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("TriadWorks");
        searchField.submit();

        assertTrue(driver.getTitle().contains("TriadWorks"),
                "The page title should contain the searched term.");
    }

}
