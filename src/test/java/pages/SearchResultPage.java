package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

public class SearchResultPage extends BasePage {
	
	
	/**
	 * Modul/Oldal: Keresési találatok (Search Results)
	 * Funkció:  A PrestaShop keresési eredményeit megjelenítő oldal (Search Result Page) reprezentációja.
	 * Ez az osztály felelős a találati lista vagy a hibaüzenetek (pl. "Nincs találat") lekérdezéséért.
	 */

	//Lokátor: Az oldal fő tartalmi blokkja, amely magában foglalja a találatokat és a rendszerüzeneteket is.
	private By mainContent = By.id("main");
	
	public SearchResultPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Kiolvassa és visszaadja a keresési eredmények oldal teljes látható szövegét.
     * Az oldal teljes main blokkját letölti, hogy egyszerű validációval (tartalmazza-e a szöveget)
     * el lehessen dönteni, hogy a keresett kulcsszóra adott válasz megjelent-e.
     * @return Az oldal fő tartalmi blokkjának (main) nyers szövege (String).
     */
	@Step("Keresési eredmények teljes szövegének lekérdezése az oldalról")
	public String getSearchResult() {
		return getText(mainContent);
	}
}
