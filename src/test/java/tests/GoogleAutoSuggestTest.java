package tests;

import frontend.GoogleHomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class GoogleAutoSuggestTest {
    WebDriver driver;
    String testURL = "https://www.google.com";

    @BeforeMethod
    public void setupTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver.exe");

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
    }

    @Test
    public void googleAutoSuggestTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        waitForUserToAcceptCookies();

        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        } catch (Exception e) {
            System.out.println("⚠️ Polje nije klikabilno, pokušavamo kliknuti na Google logo...");
            WebElement googleLogo = driver.findElement(By.xpath("//img[@alt='Google']"));
            googleLogo.click();
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        }


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus();", searchBox);

        searchBox.sendKeys("Selenium Web");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@role='listbox']//li")));

        List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@role='listbox']//li"));

        Assert.assertFalse(suggestions.isEmpty(), "❌ Nema prikazanih prijedloga!");

        String firstSuggestionText = suggestions.get(0).getText();
        System.out.println("🔍 Odabrani prijedlog: " + firstSuggestionText);
        suggestions.get(0).click();

        wait.until(ExpectedConditions.urlContains(firstSuggestionText.toLowerCase().replace(" ", "+")));

        System.out.println("✅ Automatski prijedlog je uspješno preusmjerio na rezultate.");
    }

    public void waitForUserToAcceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement acceptCookies = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Prihvati sve']")));

            System.out.println("🚨 Molimo ručno prihvatite kolačiće u pregledniku.");
            System.out.println("🔴 Kada prihvatite, pritisnite ENTER u konzoli da nastavite test...");

            new Scanner(System.in).nextLine(); // Test čeka da korisnik pritisne ENTER

            System.out.println("✅ Kolačići su prihvaćeni ručno. Nastavljamo test.");
        } catch (Exception e) {
            System.out.println("ℹ️ Nema popup-a za kolačiće. Nastavljamo test.");
        }
    }

    @AfterMethod
    public void teardownTest() {
        driver.quit();
        System.out.println("✅ Preglednik zatvoren.");
    }
}
