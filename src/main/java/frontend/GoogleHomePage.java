package frontend;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

public class GoogleHomePage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(name = "q")
    WebElement searchBox;

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void search(String query) {
        acceptCookiesIfPresent();
        checkForCaptcha();


        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus();", searchBox);


        searchBox.sendKeys(query);
        searchBox.sendKeys(Keys.ENTER);

        System.out.println("Tekst unesen u tražilicu.");
    }

    private void acceptCookiesIfPresent() {
        try {
            WebElement acceptCookies = driver.findElement(By.xpath("//button[text()='Prihvati sve']"));
            if (acceptCookies.isDisplayed()) {
                acceptCookies.click();
                System.out.println("✅ Kolačići prihvaćeni odmah.");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Nema popup-a za kolačiće.");
        }
    }

    private void checkForCaptcha() {
        try {
            WebElement captchaBox = driver.findElement(By.xpath("//iframe[contains(@src, 'recaptcha')]"));
            if (captchaBox.isDisplayed()) {
                System.out.println("CAPTCHA DETECTED!");
                System.out.println("Molimo riješite CAPTCHA u pregledniku, zatim pritisnite ENTER...");

                // Pauza dok korisnik ne pritisne ENTER
                new Scanner(System.in).nextLine();

                System.out.println("CAPTCHA riješena, nastavljamo test.");
            }
        } catch (Exception e) {
            System.out.println("Nema CAPTCHA provjere.");
        }
    }
}
