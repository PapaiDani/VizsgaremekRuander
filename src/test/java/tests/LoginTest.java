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
	 * Teszt célja: Regisztrált felhasználók bejelentkezési folyamatát érvényes és érvénytelen profiladatokkal tesztelése
	 * Funkcionális terület: Felhasználókezelés / Bejelentkezés
	 * Előfeltételek: Futó PrestaShop, nincs aktív bejelentkezett munkamenet
	 * A teszthez használt e-mail cím és jelszó páros létezik a webshop adatbázisában.
	 */

@Epic("Felhasználókezelés")
@Feature("Bejelentkezés")
class LoginTest extends BaseTest {

	/**
     * TC-2.1.
     * Cél: Regisztrált felhasználó sikeres bejelentkezése érvényes adatokkal.
     * Lépések:
     * 1. Navigáció a bejelentkezés oldalra.
     * 2. Bejelentkezési adatok (email, jelszó) kitöltése.
     * 3. Login gomb megnyomása.
     * Elvárt eredmény: A belépés sikeres, a fejlécben megjelenik a felhasználó neve ("Teszt Admin").
     */
	
	@Test
	@Severity(SeverityLevel.BLOCKER)
	@Description("TC-2.1.: A felhasználó érvényes e-mail címmel és jelszóval sikeresen bejelentkezik a webshopba.")
	void PositiveLoginTest() {
		
		// 1. Oldal objektumok példányosítása
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
		
        // 2. Navigáció a bejelentkezési oldalra
        homePage.open();
        homePage.clickSignIn();
        
        // 3. Tesztadatok megadása
        String testEmail = "admin@vizsga.hu";
        String testPassword = "TitkosJelszo123";
        String expectedName = "Teszt Admin";
        
        // Bejelentkezés a LoginPage-en
        loginPage.login(testEmail, testPassword);
        
        // 4. Ellenőrzés: Sikeres belépés után a fejlécben megjelenik a nevünk
        String actualLoggedInName = homePage.getLoggedInUserName();
        Assertions.assertEquals(expectedName, actualLoggedInName,
        		"Hiba: A bejelentkezés után nem jelent meg a várt felhasználónév a fejlécben!");
          
	}
	
	/**
     * TC-2.2.
     * Cél: Sikertelen bejelentkezés tesztelése érvénytelen (nem létező) adatokkal.
     * Lépések:
     * 1. Navigáció a bejelentkezés oldalra.
     * 2. Hibás email cím és jelszó megadása.
     * 3. Login gomb megnyomása.
     * Elvárt eredmény: A belépés elutasításra kerül "Authentication failed." hibaüzenettel.
     */
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-2.2.: Sikertelen bejelentkezés ellenőrzése érvénytelen belépési adatokkal")
	void NegativeLoginTest() {
		
		// 1. Oldal objektumok példányosítása
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
		
        // 2. Lépések: Navigáció a bejelentkezési oldalra
        homePage.open();
        homePage.clickSignIn();
        
        // 3. Tesztadatok megadása
        String negativeTestEmail = "hiba@vizsga.hu";
        String negativeTestPassword = "HibasJelszo123";
        
        // Próbálkozás a belépéssel
        loginPage.login(negativeTestEmail, negativeTestPassword);
        
        // 4. Ellenőrzés: Megjelent-e a hibaüzenet?
        String actualErrorMessage = loginPage.getErrorMessage();
        String errorMessage = "Authentication failed.";
        
        Assertions.assertEquals(actualErrorMessage, errorMessage,
        		"Hiba: Nem jelent meg a várt hibaüzenet a rossz jelszó megadásakor!");
   
	}	
}
