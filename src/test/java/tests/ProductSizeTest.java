package tests;

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
	 * Teszt célja: A terméklapon található méretválasztás tesztelése
	 * Funkcionális terület: Termékkezelés / Termékvariációk
	 * Előfeltétel: Futó PrestaShop, elérhető ruházati termék a nyitóoldalon
	 */

@Epic("Termékkezelés")
@Feature("Termékvariációk tesztelése")
class ProductSizeTest extends BaseTest {

	/**
	 * TC-8.:
	 * Cél: Ruha méretének kiválasztása és a kiválasztás sikerességének ellenőrzése a Checkout oldalon.
	 * Lépések: 
	 * 1. Navigáció a kezdőoldalra. 
	 * 2. Kattintás az első termékre
	 * 3. Méret kiválasztása a legördülő menüből "L"-es méretre.
	 * 4. Checkout oldalra navigálás
	 * 5. A kiválasztott termék méretének kiolvasása és ellenőrzése. 
	 * Elvárt eredmény: Az "L"-es méret sikeresen kiválasztásra került és a Checkout oldalon is ez jelenik meg.
	 */
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-8.: Ruha méretének kiválasztása és a kiválasztás sikerességének ellenőrzése a checkout oldalon.")
	void selectAndVerifyClothingSizeTest() {
		// 1. oldal objektum példányosítása és navigálás a termékoldalra
		ProductsPage productsPage = new ProductsPage(driver);
		CartPage cartPage = new CartPage(driver);
		driver.get("http://localhost/2-home");
		
		// 2. Kattintás az első termékre
		productsPage.clickProductByIndex(1);
		
		// 3. Méret kiválasztása
		String targetSize = "L";
		productsPage.selectSize(targetSize);
		
		// 4. Kosárba rakás és tovább a kasszához
		productsPage.addProductAndProceedToCart();
		
		// 5. Ellenőrizzük, hogy a kosárban is "L" méretű termék jelent-e meg.
		String actualSizeInCart = cartPage.getProductSize();
        
        // Ellenőrizzük, hogy a kosár oldalon az elem adatlapján a kiválasztott méret jelenik meg
        Assertions.assertEquals(targetSize, actualSizeInCart, 
                "Hiba: A kosárban nem a kiválasztott méret szerepel! Várt: " + targetSize + ", de a kosárban ez van: " + actualSizeInCart);
	}

}
