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

	/**
	 * Teszt célja: Meglévő adatok (szállítási cím) felülírása és módosítása.
	 * Funkcionális terület: Felhasználókezelés / Meglévő adat módosítása
	 * Előfeltételek: Futó PrestaShop, bejelentkezett felhasználó, akinek rögzítve van a szállítási címe.
	 */

@Epic("Felhasználókezelés")
@Feature("Meglévő adat módosítása")
class DataModificationTest extends BaseTest {

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("TC-12: Egy már létező szállítási cím adatainak felülírása és a sikeres módosítás ellenőrzése.")
	public void updateAddressTest() throws InterruptedException{
		
		/**
		 * TC-12.:
		 * Cél: egy már létező szállítási cím adatinak felülírása és a sikeres módosítás ellenőrzése. 
		 * Lépések:
		 * 1. Bejelentkezés regisztrált tesztfiókkal.
		 * 2. Navigáció az "Addresses" aloldalra és az első cím "Update" gombjának megnyomása.
		 * 3. Címsorok és telefonszám felülírása generált adatokkal. 
		 * 4. Mentés gomb megnyomása
		 * Elvárt eredmény: A cím sikeresen frissült, megjelenik a "sucessfully updated" zöld üzenet. 
		 */
		
		
		// 1. Oldal objektumok példányosítása 
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = new LoginPage(driver);
		AddressPage addressPage = new AddressPage(driver);
		
		// 2. Bejelentkezés és navigáció a címek listájához
		homePage.open();
		homePage.clickSignIn();
		loginPage.login("modositas@vizsga.hu", "TitkosJelszo123");
		Assertions.assertTrue(homePage.isSignInButtonVisible(), "Kritikus hiba: A robot nem tudott bejelentkezni a módosításhoz!");
		addressPage.openNewAddresses();
		addressPage.clickUpdateFirstAddress();
		
		//3. Faker inicializálása
		Faker faker = new Faker(Locale.US);
		
		// 4. új adatok generálása
		String newAlias = "New address"+faker.number().digits(3);
		String newAddress = faker.address().streetAddress();
		String newCity = faker.address().city();
		String newZipCode = faker.number().digits(5);
		String newPhone = faker.phoneNumber().cellPhone();
		
		// 5. Űrlap felülírása
		addressPage.clearAndfillAddressForm(newAlias, newAddress, newCity, newZipCode, newPhone);
		
		//6. Ellenőrzés
        String actualMessage = addressPage.getSuccesMessage();
        
        Assertions.assertTrue(actualMessage.toLowerCase().contains("successfully updated"), 
                "Hiba: Az adat módosítása nem sikerült, nem jelent meg a várt zöld üzenet!");
		
        
		
	}	
}
