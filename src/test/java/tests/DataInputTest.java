package tests;

import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.AddressPage;
import pages.HomePage;
import pages.LoginPage;

	/*
	 * Teszt célja: Új szállítási cím rögzítése a felhasználói fiókhoz, érvényes és érvénytelen adatokkal.
	 * Funkcionális terület: Felhasználókezelés / Új adat bevitele
	 * Előfeltételek: Futó PrestaShop, meglévő regisztrált felhasználói fiók.
	 */

@Epic("Felhasználókezelés")
@Feature("Új adat bevitele")
class DataInputTest extends BaseTest {

	/**
     * TC-11.1.:
     * Cél: Új szállítási cím hozzáadása érvényes adatokkal és a sikeres mentés ellenőrzése
     * Lépsek: 
     * 1. Bejelentkezés regisztrált felhasználóval.
     * 2. Navigáció az új cím hozzáadása oldalra.
     * 3. Adatok kitöltése Java Faker által generált érvényes adatokkal.
     * 4. Adatok mentése a Save gombbal.
     * Elvárt eredmény:  A cím mentése sikeres és megjelenik a zöld "Address succesfully added" szöveg.
     */
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-11.1.: Új szállítási cím hozzáadása érvényes adatokkal és a sikeres mentés ellenőrzése.")
	void addNewAddressTest() throws InterruptedException {

		// 1. Oldal példányosítása 
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		AddressPage addressPage = new AddressPage(driver);
		
		
		// 2. Navigáció a bejelentkezési oldalra
		homePage.open();
		homePage.clickSignIn();
		
		// 3. Bejelentkezés meglévő felhasználóval.
		loginPage.login("admin@vizsga.hu", "TitkosJelszo123");
		
		// 4. Navigálás a New Address űralpra
		// Várunk 2 másodpercet, hogy a belépés biztosan megtörténjen a szerveren!
        Thread.sleep(2000);
		addressPage.openNewAddress();
		
		//5. Űralap kitöltése Java Faker-el
		
		// Faker inicializálása
		Faker faker = new Faker(Locale.US);
	    
	    // egyedi azonosítóka generálunk
		String country = "United States";
	    String fakeAlias = "Address"+faker.number().digits(2);
	    String fakeCompany = faker.company().name();
	    String fakeAddress = faker.address().streetAddress();
	    String fakeAddressComplement = faker.address().secondaryAddress();
	    String fakeCity = faker.address().city();
	    String state = "New York";
	    String fakeZipCode = faker.number().digits(5);
	    
	    String fakePhone = faker.phoneNumber().cellPhone();
	    
	    addressPage.fillAddressForm(country, fakeAlias, fakeCompany, fakeAddress, fakeAddressComplement, fakeCity, state, fakeZipCode, fakePhone);
	    
	    // 5. Ellenőrzés
	    
	    String actualMessage = addressPage.getSuccesMessage();
	    
	    Assertions.assertEquals(actualMessage, "Address successfully added.",
	    	"Hiba: Nem jelent meg a sikeres mentést igazoló zöld üzenet!");
	}

	/**
     * TC-11.2.:
     * Cél: Új szállítási cím hozzáadása érvénytelen telefonszám formátummal és hibakezelés ellenőrzése.
     * Lépések:
     * 1. Bejelentkezés regisztrált felhasználóval.
     * 2. Navigáció az új cím hozzáadása oldalra.
     * 3. Űrlap kitöltése, de a telefonszám mezőbe érvénytelen karakter (@) megadása.
     * 4. Mentés kísérlete.
     * Elvárt eredmény: A mentés sikertelen, piros "Please fix the error below." hibaüzenet jelenik meg az űrlapon.
     */
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-11.2.: Új szállítási cím hozzáadása érvénytelen telefonszám formátummal")
	void addNewAddressNegativePhoneNumberTest() throws InterruptedException {

		// 1. Oldal példányosítása 
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		AddressPage addressPage = new AddressPage(driver);
		
		
		// 2. Navigáció a bejelentkezési oldalra
		homePage.open();
		homePage.clickSignIn();
		
		// 3. Bejelentkezés meglévő felhasználóval.
		loginPage.login("admin@vizsga.hu", "TitkosJelszo123");
		
		// 4. Navigálás a New Address űralpra
		// Várunk 2 másodpercet, hogy a belépés biztosan megtörténjen a szerveren!
        Thread.sleep(2000);
		addressPage.openNewAddress();
		
		//5. Űralap kitöltése Java Faker-el
		
		// Faker inicializálása
		Faker faker = new Faker(Locale.US);
	    
		 // egyedi azonosítóka generálunk
		String country = "United States";
	    String fakeAlias = "Address"+faker.number().digits(2);
	    String fakeCompany = faker.company().name();
	    String fakeAddress = faker.address().streetAddress();
	    String fakeAddressComplement = faker.address().secondaryAddress();
	    String fakeCity = faker.address().city();
	    String state = "New York";
	    String fakeZipCode = faker.number().digits(5);
	    
	    String fakePhone = "@"+faker.phoneNumber().cellPhone();
	    
	    addressPage.fillAddressForm(country, fakeAlias, fakeCompany, fakeAddress, fakeAddressComplement, fakeCity, state, fakeZipCode, fakePhone);
	    
	    // 5. Ellenőrzés
	    
	    String actualMessage = addressPage.getUnsuccessfulMessage();
	    
	    Assertions.assertEquals(actualMessage, "Please fix the error below.",
	    	"Hiba: Nem jelent meg a hibaüzenet!");
	}	
	
	/**
     * TC-11.3.:
     * Cél: Új szállítási cím hozzáadása érvénytelen irányítószám (Zip code) formátummal.
     * Lépések:
     * 1. Bejelentkezés regisztrált felhasználóval.
     * 2. Navigáció az új cím hozzáadása oldalra.
     * 3. Űrlap kitöltése, de a Zip code megadásánál érvénytelen karakter (@) használata.
     * 4. Mentés kísérlete a Save gombbal.
     * Elvárt eredmény: A mentés sikertelen, és a "Please fix the error below." piros figyelmeztetés jelenik meg.
     */
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-11.3.: Új szállítási cím hozzáadása érvénytelen Zip code formátummal")
	void addNewAddressNegativeZipCodeTest() throws InterruptedException {

		// 1. Oldal példányosítása 
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		AddressPage addressPage = new AddressPage(driver);
		
		
		// 2. Navigáció a bejelentkezési oldalra
		homePage.open();
		homePage.clickSignIn();
		
		// 3. Bejelentkezés meglévő felhasználóval.
		loginPage.login("admin@vizsga.hu", "TitkosJelszo123");
		
		// 4. Navigálás a New Address űralpra
		// Várunk 2 másodpercet, hogy a belépés biztosan megtörténjen a szerveren!
        Thread.sleep(2000);
		addressPage.openNewAddress();
		
		//5. Űralap kitöltése Java Faker-el
		
		// Faker inicializálása
		Faker faker = new Faker(Locale.US);
	    
	    // egyedi azonosítóka generálunk
		String country = "United States";
	    String fakeAlias = "Address"+faker.number().digits(2);
	    String fakeCompany = faker.company().name();
	    String fakeAddress = faker.address().streetAddress();
	    String fakeAddressComplement = faker.address().secondaryAddress();
	    String fakeCity = faker.address().city();
	    String state = "New York";
	    String fakeZipCode = "@"+faker.number().digits(5);
	    
	    String fakePhone =faker.phoneNumber().cellPhone();
	    
	    addressPage.fillAddressForm(country, fakeAlias, fakeCompany, fakeAddress, fakeAddressComplement, fakeCity, state, fakeZipCode, fakePhone);
	    
	    // 5. Ellenőrzés
	    
	    String actualMessage = addressPage.getUnsuccessfulMessage();
	    
	    Assertions.assertEquals(actualMessage, "Please fix the error below.",
	    	"Hiba: Nem jelent meg a hibaüzenet!");
	
	}
}
