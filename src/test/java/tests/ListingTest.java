package tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.CategoryPage;
import pages.HomePage;

	/**
	 * Teszt célja: A különböző termékkategóriák kilistázása és tartalmuk ellenőrzése
	 * Funkcionális terület: Termékkezelés / Adatok listázása
	 * Előfeltétel: Futó PrestaShop, vannak feltöltve termékek a megfelelő kategóriákhoz. 
	 */

	@Epic("Termékkezelés")
	@Feature("Adatok listázása")
class ListingTest extends BaseTest {

	/**
     * TC-5.1.:
     * Cél: A 'Clothes' kategóriában található termékek listázása és ellenőrzése
     * Lépések: 
     * 1. Navigáció a főoldalra
     * 2. Rákattintás a 'Clothes' menüpontra
     * 3. Az oldalon található összes termék beolvasása
     * Elvárt eredmény: Az oldal betöltődik és pontosan 2 db ruházati termék jelenik meg a listában.
     */
	
	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("TC-5.1.: Clothes menüpont alatt található termékek listázása és ellenőrzése, hogy helyesen tölti be.")
	void listClothesProductsTest() {

		// 1. Oldal példányosítása 
		HomePage homePage = new HomePage(driver);
		
		// 2.Navigáció a Clothes kategóriába.
		// kattintás után a metódus átadja nekünk az új oldalt!
		homePage.open();
		CategoryPage categoryPage = homePage.clickClothesMenu();
		
		// 3. Adatok (terméknevek) beolvasása már az ÚJ oldalról
        List<String> products = categoryPage.getProductNames();
		      
    	// 4. Ellenőrzés
    	// Biztosan betöltött az oldal legalább egy terméket
    	Assertions.assertTrue(products.size() > 0, 
                "Hiba: Egyetlen termék sem jelent meg a Clothes kategóriában!");
    	
    	// 2 ruházati termék van az oldalon
    	Assertions.assertEquals(2, products.size(),
    		"Hiba: Nem pontosan 2 ruházati termék jelent meg! (Talált darabszám: " + products.size() + ")");
	}
	
	/**
     * TC-5.2.:
     * Cél: Az 'Accessories' kategóriában található termékek listázása és ellenőrzése.
     * Lépések:
     * 1. Navigáció a főoldalra.
     * 2. Rákattintás az 'Accessories' menüpontra.
     * 3. Az oldalon található összes termék nevének beolvasása.
     * Elvárt eredmény: Az oldal betöltődik, és pontosan 11 db kiegészítő termék jelenik meg a listában.
     */
	
	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("TC-5.2.: Accessories menüpont alatt található termékek listázása és ellenőrzése, hogy helyesen tölti be.")
	void listAccessoriesProductsTest() {
		// 1. Oldal példányosítása 
				HomePage homePage = new HomePage(driver);
				
				// 2.Navigáció a Accessories kategóriába.
				// kattintás után a metódus átadja nekünk az új oldalt!
				homePage.open();
				CategoryPage categoryPage = homePage.clickAccessoriesMenu();
				
				// 3. Adatok (terméknevek) beolvasása már az ÚJ oldalról
		        List<String> products = categoryPage.getProductNames();
				      
		    	// 4. Ellenőrzés
		    	// Biztosan betöltött az oldal legalább egy terméket
		    	Assertions.assertTrue(products.size() > 0, 
		                "Hiba: Egyetlen termék sem jelent meg a Clothes kategóriában!");
		    	
		    	// 2 ruházati termék van az oldalon
		    	Assertions.assertEquals(11, products.size(),
		    		"Hiba: Nem pontosan 2 ruházati termék jelent meg! (Talált darabszám: " + products.size() + ")");
	}
	
	/**
     * TC-5.3.:
     * Cél: Az 'Art' kategóriában található termékek listázása és ellenőrzése.
     * Lépések:
     * 1. Navigáció a főoldalra.
     * 2. Rákattintás az 'Art' menüpontra.
     * 3. Az oldalon található összes termék nevének beolvasása.
     * Elvárt eredmény: Az oldal betöltődik, és pontosan 7 db művészeti termék jelenik meg a listában.
     */
	
	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("TC-5.3.: Art menüpont alatt található termékek listázása és ellenőrzése, hogy helyesen tölti be.")
	void listArtProductsTest() {
		// 1. Oldal példányosítása 
				HomePage homePage = new HomePage(driver);
				
				// 2.Navigáció a Art kategóriába.
				// kattintás után a metódus átadja nekünk az új oldalt!
				homePage.open();
				CategoryPage categoryPage = homePage.clickArtMenu();
				
				// 3. Adatok (terméknevek) beolvasása már az ÚJ oldalról
		        List<String> products = categoryPage.getProductNames();
				      
		    	// 4. Ellenőrzés
		    	// Biztosan betöltött az oldal legalább egy terméket
		    	Assertions.assertTrue(products.size() > 0, 
		                "Hiba: Egyetlen termék sem jelent meg a Clothes kategóriában!");
		    	
		    	// 2 ruházati termék van az oldalon
		    	Assertions.assertEquals(7, products.size(),
		    		"Hiba: Nem pontosan 2 ruházati termék jelent meg! (Talált darabszám: " + products.size() + ")");
	}
}
