package tests;

import frontend.GoogleHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GoogleSearchTest {
    WebDriver driver;
    String testURL = "https://www.google.com";

    @BeforeMethod
    public void setupTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver.exe");

        // ✅ ChromeOptions za brže pokretanje
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get(testURL);
        if (!driver.getCurrentUrl().contains("google")) {
            System.out.println("⚠️ Google se nije odmah učitao, pokušavamo ponovo...");
            driver.navigate().refresh();
        }
    }

    @Test
    public void googleSearchTest() {
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.search("Selenium WebDriver");

        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id='search']//h3"));

        if (searchResults.isEmpty()) {
            Assert.fail("❌ Nema pronađenih rezultata pretrage!");
        }

        WebElement firstResult = searchResults.get(0);
        String firstResultText = firstResult.getText();

        System.out.println("🔍 Prvi rezultat pretrage: " + firstResultText);

        Assert.assertTrue(firstResultText.toLowerCase().contains("webdriver"), "❌ Prvi rezultat ne sadrži 'Selenium'!");

        System.out.println("✅ Prvi rezultat sadrži 'webdriver'. Test uspješan.");
    }

    @AfterMethod
    public void teardownTest() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Preglednik zatvoren.");
        }
    }
}
