package tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.HomePage;

	/*
	 * Teszt célja: Termékadatok (név és ár) kinyerése a felületről és exportálása CSV-be.
	 * Funkcionális terület: Adatkinyerés és Validáci / Termékadatok ellenőrzése
	 * Előfeltételek: Futó PrestaShop, legelább egy termék az induló oldalon.
	 */

@Epic("Adatkinyerés és validáció")
@Feature("Termékadatok ellenőrzéses")
class DataExtractionTest extends BaseTest {
	
	/**
	 * TC-10.:
	 * Cél: A webshop kezdőlapján található összes termék nevének és árának kinyerése, majd exportálása CSV fájlba.
	 * Lépések: 
	 * 1. Navigáció a kezdőoldalra.
	 * 2.Termékdobozok elementjeinek listába gyűjtése
	 * 3. Végig megyünk a dobozokon és lokális kereséssel a név és az árat kiolvassuk.
	 * 4. Az adatok összefűzése CSV formátumba és mentése.
	 * Elvárt eredmény: A termékadatok sikeresen kimentésre kerültek 'PrestaShop.csv' fájlba.
	 */

	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("TC-10: A webshop kezdőlapján található összes termék nevének és árának kinyerése, majd exportálása CSV fájlba")
	public void extractAndSaveProductDataTest() throws Exception {
		
		// 1. Navigáció a kezdőoldalra
		HomePage homePage = new HomePage(driver);
		homePage.open();
		
		// 2. Megkeressük az összes olyan HTML dobozt, ami egy terméket reprezentál.
        // Ezt egy Java Listába (List<WebElement>) mentjük, hogy később végig tudjunk menni rajta.
        List<WebElement> productBoxes = driver.findElements(By.cssSelector(".product-miniature"));
        
        // Biztonsági ellenőrzés: Biztosítjuk, hogy az oldal betöltött, és van mit lementeni.
        Assertions.assertTrue(productBoxes.size() > 0, "Kritikus hiba: Egyetlen terméket sem találtunk az oldalon!");

        // 3. Exportálás
        StringBuilder csvData = new StringBuilder();
        
        // Létrehozzuk a CSV fájl fejlécét.
        csvData.append("Sorszam;Termek_Neve;Ar\n");
        
        for (int i = 0; i < productBoxes.size(); i++) {
            // Kivesszük az aktuális (i-edik) termékdobozt a listából
            WebElement currentBox = productBoxes.get(i);
            
            // Lokális keresés! 
            // Itt a 'currentBox.findElement()' metódust használjuk a 'driver.findElement()' helyett!
            // Így a Selenium csakis az aktuális, HTML dobozon belül keresi a címet és az árat, 
            // megelőzve azt, hogy a gép összekeverje a különböző termékek adatait.
            String productName = currentBox.findElement(By.cssSelector(".product-title a")).getText();
            String productPrice = currentBox.findElement(By.cssSelector(".price")).getText();
            
            // Hozzáadjuk a kinyert adatokat a StringBuilder-hez a megfelelő CSV formátumban
            int sorszam = i + 1;
            csvData.append(sorszam).append(";")
                   .append(productName).append(";")
                   .append(productPrice).append("\n");
                   
            System.out.println("     --> Kinyerve: [" + sorszam + "] " + productName + " | " + productPrice);
        }
        
        String fileName = "PrestaShop_Termekek.csv";
        
        // A Java Files.writeString() metódusával beleírjuk a StringBuilder tartalmát a fájlba.
        // TRUNCATE_EXISTING: Ha már létezik ilyen fájl a mappában az előző tesztből, felülírja friss adatokkal!
        Files.writeString(Paths.get(fileName), csvData.toString(), 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                
        // 4. Ellenőrzés
        // Ellenőrizzük, hogy a fájl tényleg létrejött-e a számítógép háttértárán.
        File savedFile = new File(fileName);
        Assertions.assertTrue(savedFile.exists(), "Hiba: A CSV fájl nem jött létre a mentés során!");
        Assertions.assertTrue(savedFile.length() > 0, "Hiba: A CSV fájl létrejött, de teljesen üres!");
        
        System.out.println("---> A TC-10 teszt sikeres! Az adatok mentve a projekt gyökerébe: " + fileName + "\n");
	}
	

}
