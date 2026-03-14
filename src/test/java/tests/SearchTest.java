package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.HomePage;
import pages.SearchResultPage;



	/**
	 * Teszt célja: A globális keresőmező funkcionalitásának tesztelése, több különböző adatkészlettel. 
	 * Funkcionális terület: Termékkezelés és böngészés / Globális kereső
	 * Előfeltételek: Futó PrestaShop, elérhető "search_data.csv" tesztadat fájl.
	 */

@Epic("Termékkezelés és Böngészés")
@Feature("Globális kereső funkció")

class SearchTest extends BaseTest {

	/**
     * TC-6.:
     * Cél: Keresőmező tesztelése többféle bemeneti adattal
     * Lépések: 
     * 1. "seach_data.csv" fájl beolvasása.
     * 2. Ugrás a kezdőoldalra.
     * 3. Keresés indítása a beolvasott kulcsszóra.
     * 4. A találat szövegének összehasonlítása az elvárt eredménnyel. 
     * Elvárt eredmény: A kereső helyes találatot ad minden bemenetre.
     * @param keyword A keresőmezőbe beírandó tesztadat (az 1. oszlop a CsvSource-ból).
     * @param expectedText A szöveg, aminek kötelezően meg kell jelennie a találati oldalon (a 2. oszlop a CsvSource-ból). 
     */
    @ParameterizedTest(name = "TC-6.{index}: Keresés -> ''{0}'' | Várt eredmény -> ''{1}''")
    @Severity(SeverityLevel.CRITICAL)
    @Story("A felhasználónak képesnek kell lennie termékekre keresni, és egyértelmű üzenetet kapni találat vagy annak hiánya esetén.")
    @Description("A keresőmező letesztelése többféle bemeneti adattal. Ellenőrzi a létező termékeket (pozitív), a nem létező termékeket (negatív), valamint a speciális karakterek kezelését.")
    // numLinesToSkip = 1 pedig azért kell, hogy a "Keyword,ExpectedText" fejlécet ne akarja beírni a keresőbe a gép.
    @CsvFileSource(resources = "/search_data.csv", numLinesToSkip = 1)
    public void dataDrivenSearchTest(String keyword, String expectedText) {
    	
    	
    	// 1. Oldalak példányosítása és azonnali ugrás a HomePage-re
        HomePage homePage = new HomePage(driver);
        homePage.open();
        // Végrehajtja a keresést, majd a betöltődő új Találati Oldalt (SearchResultPage) eltárolja egy objektumban
        SearchResultPage resultPage = homePage.searchFor(keyword);
        // Kiolvassa a megjelenő találati szöveget az új oldalról az ellenőrzéshez
        String actualPageText = resultPage.getSearchResult();
        
        
        // Mindkét szöveget csupa kisbetűssé alakítjuk (.toLowerCase()), 
        // így nem fog elbukni a teszt egy elgépelt T-Shirt vagy t-shirt miatt!
        boolean isTextFound = actualPageText.toLowerCase().contains(expectedText.toLowerCase());
        Assertions.assertTrue(isTextFound, 
                "Hiba a keresésnél! A várt szöveg ('" + expectedText + "') nem jelent meg.");  
    }

}
