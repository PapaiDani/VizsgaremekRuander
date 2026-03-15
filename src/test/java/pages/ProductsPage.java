package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import io.qameta.allure.Step;
	
	/**
	* Modul/Oldal: Termékoldal (Product Page)
	* Funkció: Egy konkrét termék részletes adatlapját reprezentálja. Konkrét műveleteket végez a terméken: 
	* kiválasztja a méretet, a kosárba rakja és bejárja az esetleges felugró ablakokat.
	*/

public class ProductsPage extends BasePage {

	// Lokátorok a kosárba rakáshoz és a felugró (Modal) ablakhoz
    private By addToCartButton = By.cssSelector("button.add-to-cart");
    private By proceedToCheckoutButton = By.cssSelector(".cart-content-btn a.btn-primary");
    
    // Lokátor: Vásárlás folytatása gomb
    private By continueShoppingButton = By.cssSelector(".cart-content-btn button.btn-secondary");
   
    // Lokátor: méretválasztásra
    private By sizeDropdown = By.id("group_1");
	
	public ProductsPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Navigálás a megadott fix URL-re
	 */
	@Step("All products megnyitása")
	public void openAllProducts() { 
		driver.get("http://localhost/2-home");
	}
	
	/**
     * Kosárba teszi a terméket, megvárja a felugró ablakot, majd átnavigál a kosárba.
     * Egymás után hívja meg a BasePage click() metódusait.
     * @return Egy új CartPage osztály példánya.
     */
    @Step("Termék kosárba tétele és navigálás a kosárhoz")
    public CartPage addProductAndProceedToCart() {
        // Kattintás a kosárba gombon
        click(addToCartButton);
        click(proceedToCheckoutButton);
        return new CartPage(driver);
    }
    
    /**
     * Kosárba teszi a terméket, de a felugró ablakban a "Vásárlás folytatása" gombot választja.
     * Nem tér vissza új oldallal, mert a felhasználó a vásárlás folytatásakor a weblapon marad.
     */
    @Step("Termék kosárba tétele és vásárlás folytatása")
    public void addProductAndContinueShopping() {
        click(addToCartButton);
        click(continueShoppingButton);
    }
    
    /**
     * Rákattint a listában szereplő megadott sorszámú termékre.
     * Segít a termékek rugalmasabb kiválasztásában, egy 1-től induló 
     * listából hív meg WebElementeket (az index - 1). Ez kötelező várakozással manipulálja a DOM-ot.
     * @param index A termék sorszáma a listában (1-től kezdődik)
     */
    @Step("Kattintás a(z) {index}. termékre az Új termékek oldalon")
    public void clickProductByIndex(int index) {
    	// 1. Megkeresünk minden egyes termékképet az oldalon
        By allProductsLocator = By.cssSelector(".product-miniature a.thumbnail");
        
        // Megvárjuk, amíg legalább az első betöltődik a képernyőn
        wait.until(ExpectedConditions.visibilityOfElementLocated(allProductsLocator));
        
        // 2. Betesszük az összes talált elemet egy Java Listába
        List<WebElement> products = driver.findElements(allProductsLocator);
        
        // 3. Biztonsági ellenőrzés: Van egyáltalán ennyi termék?
        if (products.size() < index) {
            throw new RuntimeException("Kritikus hiba: Csak " + products.size() + 
                    " termék van az oldalon, de te a " + index + "-ikat kérted! Nincs mire kattintani.");
        }
        
        // 4. Kiválasztjuk a listából a megfelelőt 
        WebElement productToClick = products.get(index - 1);
        
        // 5. Megvárjuk, hogy rákattintható legyen, és klikk!
        wait.until(ExpectedConditions.elementToBeClickable(productToClick)).click();
    }
    
    /**
     * Kiválasztja a kért méretet a legördülő menüből (pl. "S", "M", "L", "XL").
     * Select osztállyal módosítja a DOM legördülő elemeit.
     * @param size A kiválasztani kívánt méret Stringként. 
     */
    @Step("Méret kiválasztása: {size}")
    public void selectSize(String size) {
        // Megvárjuk, amíg a legördülő menü láthatóvá válik
        WebElement dropdownElement = find(sizeDropdown);
        
        // Rárakjuk a Selenium Select osztályát
        Select select = new Select(dropdownElement);
        
        // Kiválasztjuk a látható szöveg alapján
        select.selectByVisibleText(size);
    }

    /**
     * Kiolvassa, hogy jelenleg melyik méret van kiválasztva.
     * Ez a validációhoz  kell.
     * getFirstSelectedOption().getText() a Select osztály egy beépített vizsgálója.
     * @return A kiválasztott méret (String).
     */
    @Step("Kiválasztott méret ellenőrzése")
    public String getSelectedSize() {
        WebElement dropdownElement = find(sizeDropdown);
        Select select = new Select(dropdownElement);
        
        // visszaadja az éppen aktív (kiválasztott) opciót
        return select.getFirstSelectedOption().getText();
    }
}
