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
import io.qameta.allure.Story;
import pages.AddressPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductsPage;
import pages.RegistrationPage;

	/**
	 * Teszt célja: A teljes webshop End-to-End vásárlási folyamatának tesztelése
	 * Funkcionális terület: teljes vásárlási folyamat 
	 * Előfeltételek: Futó PrestaShop alkalmazás.
	 */

@Epic("Teljes E2E vásárlási folyamat")
@Feature("Vásárlás teljes folyamata")
class EndtoEndTest extends BaseTest {

	/**
     * TC-15.:
     * Cél: A webshop üzleti folyamatának teljes körű ellenőrzése dinamikus adatokkal (End-to-End).
     * Lépések:
     * 1. Új vásárló regisztrációja dinamikusan generált adatokkal.
     * 2. Kijelentkezés, majd ismételt bejelentkezés.
     * 3. Szállítási cím megadása Java Faker generált adatokkal.
     * 4. Két darab termék elhelyezése a kosárban.
     * 5. Fizetési folyamat (Checkout) végrehajtása.
     * 6. Kijelentkezés elvégzése.
     * Elvárt eredmény: A rendelés megszakítás nélkül sikeresen lefut, és a felhasználó kijelentkezik.
     */
	
	@Test
	@Severity(SeverityLevel.BLOCKER)
	@Story("Új vásárló regisztrál, vásárol majd kijelentkezik")
	@Description("TC-15.: A webshop  üzleti folyamatának teljes körű ellenőrzése dinamikus adatokkal.")
	void endToEndShoppingTest() throws InterruptedException {
		
		// 1. Adatok előkészítése
        // Dinamikus e-mail generálása az aktuális milliszekundumok alapján.
        String uniqueEmail = "tesztvasarlo_" + System.currentTimeMillis() + "@vizsga.hu";
        String password = "TesztVasarlo123";
        
        // 2. Oldal objektumok példányosítása
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage= new RegistrationPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        AddressPage addressPage = new AddressPage(driver);
        
        // 3. Regisztráció
        homePage.open();
        homePage.clickSignIn();
        loginPage.clickCreateAccount();
        registrationPage.acceptSocialTitle();
        registrationPage.registration("Teszt", "Elek", uniqueEmail, password, "2000-04-22");
        registrationPage.acceptPrivacyAndTerms();
        registrationPage.clickSaveButton();
        
        // 4. Kijelentkezés és belépés, mivel a webshop regisztráció után automatikusan beléptet
        homePage.clickSignOut();
        Assertions.assertTrue(homePage.isSignInButtonVisible(), "Hiba: Regisztráció után nem sikerült kijelentkezni!");
        
        homePage.clickSignIn();
        loginPage.login(uniqueEmail, password);
        
        // 5. Szállítási cím megadása generált adatokkal
        addressPage.openNewAddress();
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
	    
        
        // 6. termékek kosárba helyezése
        int productsToBuy = 2; // 2 terméket teszünk a kosárba
        
        for (int i = 1; i <= productsToBuy; i++) {
            productsPage.openAllProducts(); // Biztos forrás, ahol van elég termék
            
            productsPage.clickProductByIndex(i);
            
            if (i == productsToBuy) {
                productsPage.addProductAndProceedToCart(); 
            } else {
                productsPage.addProductAndContinueShopping(); 
          }
            // Megvárjuk, amíg a felugró ablak animációja teljesen befejeződik, 
            // különben a következő driver.get() "összeakadhat" a böngészővel.
            Thread.sleep(2000);
      }
        
        // 7. rendelés véglegesítése
        checkoutPage.clickProceedToCheckout();
        checkoutPage.confirmAddress();
        checkoutPage.confirmShipping();
        checkoutPage.placeOrder();
        
        // 8. Rendelés validációja
        String confirmationText = checkoutPage.getConfirmationText();
        Assertions.assertTrue(confirmationText.toLowerCase().contains("is confirmed"), 
                "Kritikus hiba: A fizetés elakadt, nincs visszaigazoló üzenet!");
        
        // 9. Kijelentkezés
        homePage.clickSignOut();
        Assertions.assertTrue(homePage.isSignInButtonVisible());
        
	}

}
