package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.CartPage;
import pages.ProductsPage;

	/**
	 * Teszt célja: Több termék URL alapján történő kosárba helyezése külső csv alapján és az áruk ellenőrzése
	 * Funkcionális terület: Vásárlási folyamat / Kosár kezelése több termékkel
	 * Előfeltételek: Futó PrestaShop, előrhető cart_products.csv tesztadat fájl
	 */


@Epic("Vásárlási folyamat")
@Feature("Kosár kezelés több termékkel")
class MultiCartTest extends BaseTest {
	
	/**
	 * TC-9.:
	 * Cél: Több termék kosárba helyezése külső csv fájból olvasva és a végösszeg ellenőrzése
	 * Lépések: 
	 * 1. Tesztadatok beolvasása csv fájlból.
	 * 2. Termékek kosárba helyezése
	 * 3. Navigálás a kosár oldalra
	 * 4. A kosárban szereplő teljes összeg összehasonlítása az elvárt összeggel.
	 * Elvárt eredmény: A termékek sikeresen kosárba kerülnek és az összeg pontosan megegyezik a várt értékkel.
	 */

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-9.: Több termék kosárba helyezése külső CSV fájlból olvasva, és a végösszeg ellenőrzése.")
	void addMultipleItemsAndCheckPriceTest() throws IOException {
		// 1. CSV fájl beolvasása
		String csvPath = "src/test/resources/cart_products.csv";
		List<String> productUrls = Files.readAllLines(Paths.get(csvPath));
		
		// Eltávolítjuk a legelső sort, mert az csak a fejléc ("ProductURL")
        productUrls.remove(0);
        
        // CSV-ből beolvasott árak
        double expectedTotal = 0.0;
        
        
        // 2. oldal objektumok példányasítása és All products megnyitása
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        driver.get("http://localhost/2-home");
        
        // 3. CIKLUS, ami végigmegy a CSV sorain
        for (String line : productUrls) {
            
            // Kettévágjuk a sort a vesszőnél. 
            // Az 0. elem az URL lesz, az 1. elem pedig az Ár.
            String[] data = line.split(",");
            String url = data[0].trim();
            double price = Double.parseDouble(data[1].trim()); // Számmá alakítjuk az árat
            
            // Hozzáadjuk a termék árát a várható végösszeghez
            expectedTotal += price;
            
            // Navigálás és kosárba rakás
            driver.get(url);
            productsPage.addProductAndContinueShopping();
        }
        driver.get("http://localhost/cart?action=show");
        
        // 4. ÁR ELLENŐRZÉSE
        double actualPrice = cartPage.getTotalPrice();
        
        // (0.01 = 1 cent eltérés megengedett).
        Assertions.assertEquals(expectedTotal, actualPrice, 0.01, 
                "Hiba: A CSV alapján számolt összeg nem egyezik meg a kosár tényleges végösszegével!");
    
	}

}
