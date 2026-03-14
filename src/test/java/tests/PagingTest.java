package tests;

import java.util.ArrayList;
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
	 * Teszt célja: A lapozógomb működésének tesztelése
	 * Funkcionális terület: Termékkezelés / többoldalas listázás
	 * Előfeltételek: Futó PrestaShop, a listában több elem legyen, mint amennyi elfér egy oldalon.
	 */

	@Epic("Termékkezelés")
	@Feature("Többoldalas listázás")
class PagingTest extends BaseTest {

	/**
	 * TC-4:
	 * Cél: többoldalas lista bejárásának tesztelése, lapozógomb használatával.
	 * Lépések:
	 * 1. Navigáció a "New Products" oldalra
	 * 2. Megjelenő termékek eltárolása listában
	 * 3. Amíg látható a "Next" gomb, addig kattintás a következő oldalra.
	 * Elvárt eredmény: Több oldal sikeres bejárása az utolsó lapig. 
	 */
	
	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("TC-4: Több oldalas lista bejárásnak tesztelése, lapozógomb tesztelésével")
	void paginationTest() throws InterruptedException {
	
		// 1. Oldal példányosítása és azonnali ugrás az Új termékekhez az URL alapján
        HomePage homePage = new HomePage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        homePage.openAllProducts();
        
        List<String> allProducts = new ArrayList<>();
        int pageCount = 1;
        
        // 2. Ciklus a lapozáshoz
        while(true) {
            System.out.println("-> Adatok olvasása a(z) " + pageCount + ". oldalról...");
            
            // Hozzáadjuk a listához a termékeket
            allProducts.addAll(categoryPage.getProductNames());
            
            // Ha van következő gomb, kattintunk, különben kilépünk
            if (categoryPage.existNextPage()) {
                categoryPage.clickNextPage(); 
                pageCount++;
            } else {
                break; 
            }
        }
        // Eltárolt termékek listája
        System.out.println(allProducts);
        
        // 4. Ellenőrzések
        Assertions.assertTrue(allProducts.size() > 0, 
                "Hiba: Egyetlen terméket sem találtunk a listában!");
        
        Assertions.assertTrue(pageCount > 1, 
                "Hiba: A lapozógomb nem működött, vagy nincs legalább 9 db új termék a webshopban a 2. oldalhoz!");
	}

}
