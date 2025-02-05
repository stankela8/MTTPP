package tests;

import java.time.Duration;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebShopTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver.exe");

        // Chrome opcije za stabilnost
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        baseUrl = "https://www.links.hr/hr";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("https://www.links.hr/hr");

        // ✅ Ako postoji banner za kolačiće, klik na "Prihvaćam"
        acceptCookiesIfPresent();

        driver.findElement(By.name("q")).click();
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("tipkovnica");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Tipkovnica + miš LOGITECH Pebble 2 Combo, bežična, crna'])[1]/following::span[4]")).click();
        driver.findElement(By.xpath("//a[@id='topcartlink']/img")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    // ✅ METODA ZA AUTOMATSKO PRIHVAĆANJE KOLAČIĆA AKO SE POJAVE
    private void acceptCookiesIfPresent() {
        try {
            WebElement cookieButton = driver.findElement(By.xpath("//button[contains(text(),'Prihvaćam')]"));
            if (cookieButton.isDisplayed()) {
                cookieButton.click();
                System.out.println("✅ Kolačići su prihvaćeni.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("✅ Banner za kolačiće nije pronađen. Nastavljam test.");
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
