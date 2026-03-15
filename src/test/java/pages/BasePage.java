package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

	/**
	 * Modul/Oldal: Központi Page Object (BasePage)
	 * Funkció:  Minden más oldal (Page) ősosztálya. 
	 * A legfontosabb, minden oldalon ismétlődő metódusokat (kattintás, gépelés, szöveg lekérés) tartalmazza, 
	 * beépített implicit/explicit várakozásokkal (Wait). 
	 * Ezt az osztályt minden más Page Object kiterjeszti.
	 */


public class BasePage {
		
	/**
     * WebDriver példány, amelyen a tesztek futnak.
     * Védett (protected), hogy a leszármazott Page osztályok közvetlenül hozzáférjenek.
     */
	protected WebDriver driver;
	
	/**
	 * Explicit várakozást kezelőlő objektum
	 */
	protected WebDriverWait wait; 

	/**
	 * Konstruktor a BasePage osztályhoz
	 * @param driver a WebDriver példánya, amelyet a BaseTest osztály biztosít
	 */
		public BasePage(WebDriver driver) {  
			this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // GitHub szerver miatt megnövelt várakoztatás. 
		}
	
	/**
     * Elem keresése beépített várakozással.
     * Megvárja, amíg a megadott lokátor által azonosított elem VIZUÁLISAN láthatóvá válik az oldalon.
     * @param locator az elem keresési útvonala (pl. By.id, By.cssSelector)
     * @return az azonosított és már látható WebElement
     */
	protected WebElement find(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));  
	}
	
	/**
     * Kattintás egy elemre.
     * Megkeresi a lokátorral megadott elemet, és megvárja, amíg nem csak látható,
     * de ténylegesen kattintható állapotba is kerül 
     * * @param locator az elem keresési útvonala
     */
	protected void click(By locator) {  
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	
	/**
     * Szöveg bevitele egy beviteli mezőbe
     * Magában foglalja az elem megkeresését (a find() metódus hívásával),
     * a mező előzetes ürítését (clear), majd az új szöveg begépelését (sendKeys).
     * @param locator a beviteli mező keresési útvonala
     * @param text a begépelendő szöveg
     */
	protected void type(By locator, String text) { 
		WebElement element = find(locator); 
        element.clear(); 
        element.sendKeys(text);
	}
	
	/**
     * Szöveges tartalom lekérdezése egy webes elemről.
     * Magában foglalja az elem láthatóságának megvárását (find()).
     * @param locator a lekérdezni kívánt elem keresési útvonala
     * @return az elemben található szöveg String formátumban
     */
	protected String getText(By locator) { 
		return find(locator).getText();
	}
	
	
}
