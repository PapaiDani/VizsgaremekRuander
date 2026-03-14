package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.HomePage;
import pages.LoginPage;

	/**
	 * Teszt célja: A felhasználói munkamenet biztonságos bezárása, kijelentkezés.
	 * Funkcionális terület: Felhasználókezelés / Kijelentkezés
	 * Előfeltételek: Futó PrestaShop, sikeresen bejelentkezett felhasználói munkamenet.
	 */

@Epic("Felhasználókezelés")
@Feature("Kijelentkezés")
class LogoutTest extends BaseTest {

	/**
	 * TC-14.:
	 * Cél: Sikeres kijelentkezés tesztelése.
	 * Lépések: 
	 * 1. Bejelentkezés a teszt fiókba.
	 * 2. Kijelentkezés gomb megnyomása
	 * 3. Fejléc gombjainak ellenőrzése
	 * Elvárt eredmény: A kijelentkezés sikeres, a fejlécben ismét megjelenik a 'Sing in' gomb.
	 */
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-14: Sikeres kijelentkezés tesztelése")
	void succesfulLogoutTest() {

		// 1. Oldal objektumok példányosítása és bejelentkezés
		HomePage homePage = new HomePage(driver);
		LoginPage logiPage = new LoginPage(driver);
		
		homePage.open();
        homePage.clickSignIn();
		logiPage.login("admin@vizsga.hu", "TitkosJelszo123");
		
		// 2. Kijelentkezés
		homePage.clickSignOut();
		
		// 3. Kijelentkezés validálása
		boolean isLoggedOut = homePage.isSignInButtonVisible();
        
        Assertions.assertTrue(isLoggedOut, 
                "Kritikus Biztonsági Hiba: A kijelentkezés nem sikerült, a 'Sign in' gomb nem jelent meg a fejlécben!");
		
	}

}
