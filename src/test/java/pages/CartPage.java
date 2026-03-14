package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Kosár (Cart) felület
	 * Funkció: A kosárba tett termékek áttekintő felületét modellezi.
	 * Képes a termékek törlésére és az összesített árak kiolvasására.
	 */
public class CartPage extends BasePage {
	
	
	// Lokátorok a kosár kezeléséehez
	private By deleteIcon = By.cssSelector(".remove-from-cart");
	private By noItemsMessage = By.cssSelector(".no-items");
	
	// Lokátor: A kosár teljes végösszege
	private By totalPrice = By.cssSelector(".cart-total .value");
	
	// Lokátor: A kosárban lévő termék mérete
		private By productSize = By.xpath("//div[contains(@class, 'product-line-info')]//span[contains(text(), 'Size:')]/following-sibling::span");

	public CartPage(WebDriver driver) {
		super(driver);
	}
	
	/*
	 * A delete ikonra kattint. Törli az első elérhető terméket a kosárból.
	 */
	@Step("Kattintás az első termék törlés ikonjára")
	public void removeFirstItem() {
		click(deleteIcon);
	}
	
	/**
	 * A BasePage getText() metódusa megvárja a visibilityOfElementLocated-et,
     * így kivárja, amíg a törlési animáció befejeződik a háttérben.
	 * @return Az üres kosár üzenet szövege.
	 */
	@Step("Üres kosár üzenet lekérdezése")
    public String getEmptyCartMessage() {
 
        return getText(noItemsMessage);
    }
	
	/**
	 * A kosár végösszegének beolvasása és formázása.
	 * String formátumból (pl. "€ 35.90") speciális
	 * RegExp kifejezés ([^0-9.,]) segítségével minden nem-szám karaktert kitöröl,
	 * ezután a Double.parseDouble() metódussal tört számmá alakítja a pontos
	 * matematikai ellenőrzés érdekében.
	 * @return A kosár teljes értéke (Double).
	 */
	@Step("A kosár végösszege számmá alakítva")
	public double getTotalPrice() {
		// 1. Lekérjük a szöveget (pl. "€ 35.90")
        String priceText = getText(totalPrice);
        // 2.  Eltávolítunk minden betűt, szóközt és pénznemet, csak a számokat és a pontot/vesszőt hagyjuk meg!
        priceText = priceText.replaceAll("[^0-9.,]", "").replace(",", ".");
        // 3. Tört számmá (Double) alakítjuk
        return Double.parseDouble(priceText);
	}
	
	/**
	 * Lekérdezi a kosárban lévő első termék kiválasztott méretét. 
	 * A kosár oldalon a méret egy speciális span elemben jelenik meg a "Size:" szöveg után.
	 * @return A méret Stringként.
	 */
	@Step("Kosárban lévő termék méretének lekérdezése")
	public String getProductSize() {
		return getText(productSize);
		
	}
	
}
