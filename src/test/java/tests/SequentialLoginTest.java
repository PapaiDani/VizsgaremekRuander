package tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.HomePage;
import pages.LoginPage;

	/**
	 * Teszt célja: Adatvezérelt (Data-Driven) tesztelés a bejelentkezési folyamatra pozitív és negatív adatokkal.
	 * Funkcionális terület: Felhasználókezelés / Sorozatos bejelentkezés
	 * Előfeltételek: Futó PrestaShop, elérhető "login_data.csv" tesztadat fájl.
	 */

@Epic("Felhasználókezlés")
@Feature("Sorozatos bejelentkezés")
class SequentialLoginTest extends BaseTest {
	
	
	// Osztályszintű változók, hogy minden futásnál azonnal elérjük az oldalakat
    private HomePage homePage;
    private LoginPage loginPage;
    
    /**
     * Belső inicializáló, ami minden egyes CSV sor (tesztfutás) előtt lefut.
     * Biztosítja, hogy mindig tiszta böngészőablakban induljunk.
     */
    @BeforeEach
    public void setupPages() {
    	homePage = new HomePage(driver);
    	loginPage = new LoginPage(driver); 
    }
    
    
    /**
     * TC azonosító: TC-3
     * Cél: A bejelentkezés tesztelése adatvezérelt módszerrel.
     * Lépések:
     * 1. Adatsor beolvasása a CSV-ből (E-mail, Jelszó, Elvárt üzenet).
     * 2. Navigáció a login oldalra.
     * 3. Bejelentkezés a CSV-ben található adatokkal.
     * 4. Az oldal szöveges tartalmának ellenőrzése a CSV-beli elvárt üzenet alapján.
     * Elvárt eredmény: Rendszer a regisztráltakat beengedi, a hibás adatokat visszautasítja, és mindezt a teszt megfelelően validálja.
     */
    @ParameterizedTest(name = "{index}. Futás: {0} -> Email: {1}")
    @Severity(SeverityLevel.CRITICAL)
    @Story("A rendszernek be kell engednie a regisztrált felhasználókat, és vissza kell utasítania a hibás próbálkozásokat.")
    @Description("TC-3: A bejelentkezési űrlap tesztelése külső CSV fájl alapján. Vizsgálja a sikeres belépést és a biztonsági hibaüzeneteket.")
    @CsvFileSource(resources = "/login_data.csv", numLinesToSkip = 1)
	public void loginTest(String testName, String email, String password, String expectedMessage) throws InterruptedException{
    	
    	// 1. Lépés: Navigálás a belépési oldalra
    	homePage.open();
        homePage.clickSignIn();
    	
        // 2. Lépés: Kísérlet a belépésre a CSV-ből kapott adatokkal
        loginPage.login(email, password);
        
        // 4. Lépés: Ellenőrzés 
        String body = loginPage.bodyMessage();
        boolean isMessageFound = body.toLowerCase().contains(expectedMessage.toLowerCase());
        
        Assertions.assertTrue(isMessageFound, 
                "Hiba a(z) '" + testName + "' tesztesetnél! A várt üzenet ('" + expectedMessage + "') nem jelent meg a képernyőn.");
   
    }
}
