package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CoinDeskAPITest {

    private static final String BASE_URL = "https://api.coindesk.com/v1/bpi";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetBitcoinPrice() {
        Response response =
                given()
                        .when()
                        .get("/currentprice.json")
                        .then()
                        .statusCode(200) //
                        .body("chartName", equalTo("Bitcoin")) //
                        .extract().response();

        System.out.println("🔍 Trenutne cijene Bitcoina: " + response.asString());


        Assert.assertNotNull(response.jsonPath().getString("bpi.USD.rate"), "❌ USD cijena nije dostupna!");
        Assert.assertNotNull(response.jsonPath().getString("bpi.EUR.rate"), "❌ EUR cijena nije dostupna!");
        Assert.assertNotNull(response.jsonPath().getString("bpi.GBP.rate"), "❌ GBP cijena nije dostupna!");
    }
    @Test
    public void testCheckUpdatedTime() {
        Response response =
                given()
                        .when()
                        .get("/currentprice.json")
                        .then()
                        .statusCode(200)
                        .extract().response();

        String updatedTime = response.jsonPath().getString("time.updated");
        Assert.assertNotNull(updatedTime, "❌ Vrijeme ažuriranja nije dostupno!");

        System.out.println("⏳ Podaci ažurirani u: " + updatedTime);
    }
    @Test
    public void testGetBitcoinPriceInUSD() {
        Response response =
                given()
                        .when()
                        .get("/currentprice/USD.json")
                        .then()
                        .statusCode(200)
                        .extract().response();

        String priceUSD = response.jsonPath().getString("bpi.USD.rate");
        Assert.assertNotNull(priceUSD, "❌ USD cijena nije dostupna!");

        System.out.println("💵 Cijena Bitcoina u USD: " + priceUSD);
    }
}