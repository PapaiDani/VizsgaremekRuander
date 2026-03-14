package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;

	/**
	 * Teszt célja: A felhasználói regisztráció folyamatát érvényes és már létező (érvénytelen) adatokkal.
	 * Funkcionális terület: Felhasználókezelés / Regisztráció
	 * Előfeltételek: Futó PrestaShop, nincs aktív bejelentkezett felhasználó,
	 * a teszthez használt e-mail cím és jelszó páros még nem létezik a webshop adatbázisában
	 */

@Epic("Felhasználókezelés")
@Feature("Regiszrtáció")


public class RegistrationTest extends BaseTest{
		
	/**
     * TC-1.1:
     * Cél: Sikeres regisztráció tesztelése érvényes bemeneti adatokkal és GDPR elfogadása
     * Lépések: 
     * 1. Navigáció a főoldalra, majd a bejelentkezés gombra kattintás.
     * 2. Tovább a fiók létrehozása oldalra
     * 3. Regisztrációs űrlap kitöltése dinamikusan generált érvényes adatokkal. 
     * 4. Adatvédelmi és ÁSZF elfogadása és mentés. 
     * Elvárt eredmény: A felhasználó sikeresen belép, majd a fejlécben megjelenik a neve.
     */
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC-1.1.: A felhasználó sikeresen regisztrál a rendszerbe érvényes adatokkal és a GDPR elfogadásával.")
    void RegistrationPositiveTest() {
    	
    	
    // 1. Oldal objektumok példányosítása
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = new LoginPage(driver);
    RegistrationPage registrationPage = new RegistrationPage(driver);
    
    // 2. Webshop megnyitása és navigáció a regisztrációs űrlaphoz
    homePage.open();
    homePage.clickSignIn();
    loginPage.clickCreateAccount();
    
    //3. Egyedi attribútumok inicializálása az ismételhető futtatáshoz
    
    Faker faker = new Faker(Locale.US);
    
    //Név beállítása, email, jelszó és birthday beállítása
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String uniqueEmail = "tesztvasarolo"+ faker.number().digits(5) + "@vizsga.hu";
    
    // Generálunk egy erős jelszót (minimum 8 karakter, kis/nagybetű, szám) 
    String password = faker.internet().password(8, 15, true, true, true ) + "A1";
    
    // Generálunk egy kort 18 és 65 év között, és egy véletlen napot az évből
    int randomAge = faker.number().numberBetween(18, 65);
    int randomDayOfYear = faker.number().numberBetween(1, 365);
    
    // Visszaszámolunk a mai naptól
    LocalDate randomBirthDate = LocalDate.now().minusYears(randomAge).minusDays(randomDayOfYear);
    
    // Megformázzuk a pontos "05/31/1970" (Hónap/Nap/Év) stílusra
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String formattedBirthDate = randomBirthDate.format(formatter);
  
    //4. űrlap kitöltése és mentése
    registrationPage.acceptSocialTitle();
    registrationPage.registration(firstName, lastName, uniqueEmail, password, formattedBirthDate);
    registrationPage.acceptPrivacyAndTerms();
    registrationPage.clickSaveButton();
    
    //5. Ellenőrzés: Sikeres regisztráció után a fejlécben meg kell jelennie a névnek
    String actualLoggedInName = homePage.getLoggedInUserName();
    String expectedFullName = firstName +" "+lastName;
    
    Assertions.assertEquals(expectedFullName, actualLoggedInName,
    	"Hiba: A regisztráció után nem jelent meg a felhasználó neve a fejlécben!");
    }
    
    /**
     * TC-1.2: 
     * Cél: Sikertelen regisztráció tesztelése már létező adatokkal.
     * Lépések:
     * 1. Navigáció a főoldalra, majd a bejelentkezés gombra kattintás.
     * 2. Tovább a fiók létrehozása oldalra.
     * 3. Regisztrációs űrlap kitöltése új adatokkal.
     * 4. Adatvédelmi és ÁSZF elfogadása és mentés.
     * 5. Kijelentkezés
     * 6. 3. pontban beregisztrált felhasználó adataival való regisztráslás
     * 7. Adatvédelmi és ÁSZF elfogadása és mentés.
     * Elvárt eredmény: A regisztráció sikertelen, az űrlapon "already used" tartalmú hibaüzenet jelenik meg, és a felhasználó sincs bejelentkezve.
     */
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("TC-1.2.: Sikertelen regisztráció már létező adatokkal")
    void RegistrationEmailNegativeTest() {
    	
    	
    // 1. Oldal objektumok példányosítása
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = new LoginPage(driver);
    RegistrationPage registrationPage = new RegistrationPage(driver);
    
    // 2. Webshop megnyitása és navigáció a regisztrációs űrlaphoz
    homePage.open();
    homePage.clickSignIn();
    loginPage.clickCreateAccount();
    
    //3. Egyedi attribútumok inicializálása az ismételhető futtatáshoz
    
    Faker faker = new Faker(Locale.US);
    
    //Név beállítása, email, jelszó és birthday beállítása
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String targetEmail = "foglalt_email" + faker.number().digits(5) + "@vizsga.hu";
    
    // Generálunk egy erős jelszót (minimum 8 karakter, kis/nagybetű, szám) 
    String password = faker.internet().password(8, 15, true, true, true ) + "A1";
    
    // Generálunk egy kort 18 és 65 év között, és egy véletlen napot az évből
    int randomAge = faker.number().numberBetween(18, 65);
    int randomDayOfYear = faker.number().numberBetween(1, 365);
    
    // Visszaszámolunk a mai naptól
    LocalDate randomBirthDate = LocalDate.now().minusYears(randomAge).minusDays(randomDayOfYear);
    
    // Megformázzuk a pontos "05/31/1970" (Hónap/Nap/Év) stílusra
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String formattedBirthDate = randomBirthDate.format(formatter);
  
    //4. űrlap kitöltése, mentése és kijelentkezés
    registrationPage.acceptSocialTitle();
    registrationPage.registration(firstName, lastName, targetEmail, password, formattedBirthDate);
    registrationPage.acceptPrivacyAndTerms();
    registrationPage.clickSaveButton();
    
    homePage.clickSignOut();
    
    //5. Újra regisztráció az előbb létrehozott adatokkal.
    homePage.clickSignIn();
    loginPage.clickCreateAccount();
    registrationPage.acceptSocialTitle();
    registrationPage.registration(firstName, lastName, targetEmail, password, formattedBirthDate);
    registrationPage.acceptPrivacyAndTerms();
    registrationPage.clickSaveButton();
    
    //6. Ellenőrzés: Sikertelen regiszrációnál hibaüzenet kell kapjunk
    String actualErrorMessage = registrationPage.getRegistrationErrorMessage();
    System.out.println("     --> Kapott hibaüzenet: " + actualErrorMessage);
    
    // A contains() logikával ellenőrizzük
    Assertions.assertTrue(
        actualErrorMessage.toLowerCase().contains("already used"),
        "Hiba: A rendszer nem dobott megfelelő hibaüzenetet a létező e-mail címre! Kapott szöveg: " + actualErrorMessage);
    
    // Biztonsági ellenőrzés: Megbizonyosodunk róla, hogy a rendszer NEM léptetett be minket!
    // Ha nem sikerült a regisztráció, a fejlécben továbbra is a "Sign in" gombnak kell lennie.
    Assertions.assertTrue(homePage.isSignInButtonVisible(),
        "Kritikus Biztonsági Hiba: A rendszer a hiba ellenére beléptetett a felületre!");
    }
}
