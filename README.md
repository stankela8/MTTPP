# Projektni zadatak iz kolegija MTTPP

Ovaj repozitorij sadrži automatizirane testove napisane pomoću **Selenium-a**, **TestNG-a** i **Playwright-a**.  
Testovi su organizirani u hijerarhiju s fokusom na testiranje **web aplikacija** i **REST API-ja**.

---
## 🔧 **Korištene tehnologije i alati**
| Tehnologija / Alat | Verzija | Opis |
|--------------------|---------|------|
| **Java**          | 21       | Programsko okruženje za pisanje testova |
| **Selenium WebDriver** | 4.5.0 | Automatsko testiranje web aplikacija |
| **TestNG**        | 7.4.0    | Testni framework za vođenje testova |
| **Playwright**    | 1.39.0   | Moderna testna automatizacija |
| **Maven**         | 3.8.1    | Build i dependency management |
| **ChromeDriver**  | 133.0    | Pokretanje testova u Chrome pregledniku |
---
Za pokretanje projekta potrebno je:
```sh
git clone https://github.com/stankela8/Testiranje-Projekt.git
```
Nakon kloniranja, uđite u direktorij:
```sh
cd Testiranje-Projekt
```

---
## 🚀 **Kako pokrenuti testove?**
1️⃣ Pokretanje testova pomoću Maven-a
```sh
mvn test
```
2️⃣ Pokretanje TestNG testova iz testng.xml
```sh
mvn test -DsuiteXmlFile=testng.xml
```
3️⃣ Pokretanje testova u IntelliJ IDEA

1. Otvorite IntelliJ IDEA i učitajte projekt.
2. Idite na src/test/java/tests direktorij.
3. Desni klik na testnu klasu (CoinDeskAPITest.java ili bilo koji drugi test).
4. Kliknite Run 'CoinDeskAPITest'.

## 🧪 **Opis testnih slučajeva**

| Testna klasa | Opis |
|-------------|------|
| **APITest.java** | Provjera API endpointova koristeći **RestAssured**. Testira valjanost odgovora i HTTP statuse. |
| **CoinDeskAPITest.java** | Testiranje CoinDesk API-ja za dohvaćanje podataka o **cijenama kriptovaluta**. |
| **GoogleAutoSuggestTest.java** | Testira funkcionalnost **Google Autocomplete** prijedloga pretrage. |
| **GoogleSearchTest.java** | Simulira Google pretragu i provjerava prikaz **rezultata pretrage**. |
| **WebShopTest.java** | Testira online trgovinu **Links.hr**, dodavanje proizvoda u košaricu i provjeru dodanih stavki. |


