package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Kategória / Terméklistázó felület
	 * Funkció: Bizonyos kategóriába (ruházat, kiegészítők stb.) tartozó termékeket
	 * listázó oldalakat modellezi. Tartalmazza a termékadatok tömeges kinyerését,
	 * illetve a többoldalas lapozót.
	 */

public class CategoryPage extends BasePage {
	
	// Lokátor: A kategória oldalon lévő termékek nevei
    private By productTitles = By.cssSelector(".product-title a");
    
    // Lokátor: A lapozógombokhoz
    private By nextButton = By.cssSelector("a.next");

	public CategoryPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Lekérdezi az éppen nyitott kategória oldalán lévő összes termék nevét.
	 * A findElements lista (List<WebElement>) alapján egy foreach ciklus segítségével 
	 * String listát hoz létre az azonosított termékek szövegeiből elemzés céljából.
	 * @return A termékneveket tartalmazó lista.
	 */
	@Step("A kategória oldalon listázott termékek neveinek lekérdezése")
	public List<String> getProductNames(){
		List<WebElement> elements = driver.findElements(productTitles);
		List<String> names = new ArrayList<>();
		
		for (WebElement element : elements) {
			names.add(element.getText());
			
		}
		return names;
	}
	
	/**
	 * Eldönti, hogy látható-e a következő oldal lapozógombja az oldalon.
	 * findElements és .size() > 0 logikát alkalmaz,
	 * hogy kivédje a NoSuchElementException dobását, ha a gomb nincs az oldalon.
	 * @return true, ha a lapozógomb létezik, egyébként false.
	 */
	@Step("Megvizsgáljuk, hogy létezik-e következő oldal a listában")
	public boolean existNextPage() {
		// Ha a lista mérete nagyobb mint 0, akkor van lapozógomb
        return driver.findElements(nextButton).size() > 0;	
	}
	
	/**
	 * Rákattint a Következő lapozógombra.
	 * Kattintás után Thread.sleep(2000) használatával kötelező 
	 * altatást iktat be a szálban, így biztosítva, hogy a PrestaShop teljes egészében renderelje és betöltse
	 * az új oldal adatait, mielőtt a kód tovább fut.
	 */
	@Step("Lapozás a következő oldalra")
	public void clickNextPage() throws InterruptedException {
		click(nextButton);
        Thread.sleep(2000);
	}
	
	
}
