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
	 * Teszt célja: Kosár tartalmának kezelésének tesztelése, termék hozzáadásával és eltávolításával. 
	 * Funkcionális terület: Vásárlási folyamat / kosár ürítése
	 * Előfeltételek: Futó PrestaShop, legalább egy elérhető termék a webáruházban.
	 */

@Epic("Vásárlási folyamat")
@Feature("Kosár kezelés és adat törlése")
class CartTest extends BaseTest {
	
	/**
	 * TC-7.:
	 * Cél: Termék kosárba helyezése, majd annak sikeres eltávolítása
	 * Lépések:
	 * 1. Termék betöltése 
	 * 2. Termék elhelyezése a kosárba és továbblépés a kosárba
	 * 3. Termék eltávolítás gomb megnyomása kosár nézetben.
	 * Elvárt eredmény: A termék törlődik a kosárból és megjelenik a "no more item" felírat. 
	 */

	@Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC-7.: Termék kosárba helyezése, majd sikeres eltávolítása.")
    public void addAndDeleteFromCartTest() {
        
		// 1. Oldal objektumok példányosítása és termék megnyitása
		ProductsPage productPage = new ProductsPage(driver);
        // Közvetlenül megnyitjuk az 1-es ID-jú termék (T-shirt) oldalát
        driver.get("http://localhost/1-1-hummingbird-printed-t-shirt.html");
        
        // 2. Termék kosárba helyezése majd eltávolítása
        CartPage cartPage = productPage.addProductAndProceedToCart();
        cartPage.removeFirstItem();
        
        // 3. Kosár ellenőrzése
        String actualMessage = cartPage.getEmptyCartMessage();
        
        Assertions.assertTrue(actualMessage.toLowerCase().contains("no more items"), 
                "Hiba: A termék törlése után nem jelent meg az üres kosár üzenet!");

    }
}
