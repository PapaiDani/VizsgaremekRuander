package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Eszközkészlet / Felhasználói fiók
	 * Funkció: Kiegészítő oldal, részben helyettesítve más Page osztályokkal 
	 * A bejelentkezés utáni "Your account" oldalt modellezi.
	 */

public class InventoryPage extends BasePage {
	
	// Lokátor: Címsor
	private By title = By.className("page-header");
	
	// Lokátor: Kosár ikon
	private By cartIcon = By.className("shopping-cart");
	

	public InventoryPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Ellenőrzi, hogy az oldal teljesen betöltött-e, a címsor vizsgálatával.
	 * @return igaz, ha a cím "Your account", különben hamis.
	 */
	@Step("Ellenőrzi, hogy az oldal teljesen betöltött-e, a címsor vizsgálatával.")
	public boolean isLoaded() {  
		return getText(title).equals("Your account");
	}
	
	/**
	 * Termék kosárba helyezése (data-test attribútum alapján)
	 * @param dataTestname Az elem megkülönböztető data-test attribútuma.
	 */
	@Step("kosárba helyezés data-test alapján")
	public void addProductsToCart(String dataTestname) {
		By addToCartButton = By.cssSelector("[data-test'" + dataTestname+ "']");
		click(addToCartButton);
	}
	
	/**
	 * Kosár ikon megnyitása a fejlécből.
	 */
	@Step("Kosárra ikonra kattintás") 
	public void openCart() {
		click(cartIcon);
	}
	
	
}
