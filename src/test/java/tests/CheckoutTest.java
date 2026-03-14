package tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;

	/*
	 * Teszt célja: A teljes megrendelési folyamat teszteléső meglévő felhasználóval és adatokkal
	 * Funkcionális terület: Vásárlási folyamat / Pénztár
	 * Előfeltételek: Futó PrestaShop, regisztrált felhasználó beállított szállítási címmel.
	 */

@Epic("Vásárlási folyamat")
@Feature("Rendelés leadása és checkout")
class CheckoutTest extends BaseTest {

	/*
	 * TC-13.:
	 * Cél: Kosár funkció tesztelése és teljes checkout folyamat bejárása.
	 * Lépések:
	 * 1. Bejelentkezés regisztálr vásárlóval
	 * 2. Három különböző termék kosárba helyezése főoldalról indulva.
	 * 3. Továbba pénztárhoz.
	 * 4. Szállítási cím, szállítási mód megerősítése.
	 * 5. Rendelés leadása
	 * Elvárt eredmény: Sikeres rendelés, megjelenik a "Youe order is confirmed" felirat.
	 */
	
	
	@Test
	@Severity(SeverityLevel.BLOCKER)
	@Description("TC-13. Kosár funkció tesztelése adatkezelési nyilatkozat elfogadásával")
	public void fullCheckoutProcessTest() throws InterruptedException{
		
		
		// 1. Oldal objektumok példányosítása
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		ProductsPage productsPage = new ProductsPage(driver);
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		
		// 2. Bejelentkezés meglévő felhasználóval
		driver.get("http://localhost/");
		homePage.clickSignIn();
		loginPage.login("kiszallitas@vizsga.hu", "Kiszallitas123");
		
		// 3. "x" mennyiségű termék kosárba tétele
		int productsToBuy = 3;
		
		for (int i = 1;i<=productsToBuy; i++) {
			driver.get("http://localhost/");
			// Rákattintunk az i-edik termékre
			productsPage.clickProductByIndex(i);
			
			//Ha ez a legutolsó termék, irány a pénztár, különben folytatjuk a vásárlást.
				if (i == productsToBuy) {
					productsPage.addProductAndProceedToCart();
					}else {
						productsPage.addProductAndContinueShopping();
					}
		}
		
		// 4. Szállítási és fizetési adatok kitöltése
		checkoutPage.clickProceedToCheckout();
		checkoutPage.confirmAddress();
		checkoutPage.confirmShipping();
		checkoutPage.placeOrder();
		
		// 5. ellenőrzés
		
		String confirmationText = checkoutPage.getConfirmationText();
		
		Assertions.assertTrue(confirmationText.toLowerCase().contains("is confirmed"),
				"Kritikus Hiba:A rendelés nem ment végbe, nem jelent meg a sikeres visszaigazolás!");

	}

}
