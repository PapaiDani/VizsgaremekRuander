package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Pénztár (Checkout)
	 * Funkció: A PrestaShop teljes fizetési folyamatának reprezentációja.
	 * Modellezi a kosárból a pénztárba való átlépést, a cím és szállítási mód jóváhagyását,
	 * valamint az utánvétes fizetés és az ÁSZF elfogadását.
	 */
public class CheckoutPage extends BasePage {
	
	// Lokátorok a fizetési folyamathoz
	private By proceedToCheckoutButton = By.cssSelector(".cart-summary a.btn-primary");
	private By confirmAddressButton = By.name("confirm-addresses");
	private By confirmShippingButton = By.name("confirmDeliveryOption");
	
	// Lokátorok a fizetési módokhoz
	private By paymentCashOnDelivery = By.cssSelector("label[for='payment-option-3']");
	private By paymentBankWire = By.cssSelector("label[for='payment-option-2']");
	private By paymentCheck = By.cssSelector("label[for='payment-option-1']");
	
	// Lokátor: Terms of service checkboxhoz
	private By termsOfService = By.cssSelector("label[for^='conditions_to_approve']");
	
	// Lokátor: Place order gomb
	private By placeOrderButton = By.cssSelector("#payment-confirmation button");
	
	// Lokátor: Sikeres rendelés visszaigazoló szöveg
	private By orderConfirmationText = By.cssSelector("h3.h1.card-title, .page-order-confirmation h3");

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Rákattint a "Proceed to checkout" gombra a kosár összesítő felületén.
	 * A BasePage.click() metódus megvárja az elem kattinthatóságát, 
	 * majd továbbviszi a felhasználót a tényleges fizetési folyamat első lépésére.
	 */
	@Step("Kattintás a 'tovább a pénztárhoz' gombra")
	public void clickProceedToCheckout() {
		click(proceedToCheckoutButton);
	}
	
	/**
	 * Jóváhagyja az alapértelmezett szállítási címet a Checkout 1. lépéseként.
	 */
	@Step("1. lépés: cím jóváhagyása")
	public void confirmAddress() {
		click(confirmAddressButton);
	}
	
	/**
	 * Jóváhagyja az alapértelmezett szállítási módot a Checkout 2. lépéseként.
	 */
	@Step("2. lépés: szállítási mód kiválasztása")
	public void confirmShipping() {
		click(confirmShippingButton);
	}
	
	/**
	 * Befejezi a megrendelést a 3. lépésben.
	 * Kiválasztja az utánvétes fizetést (Cash on Delivery), 
	 * bepipálja az ÁSZF (Terms of Service) checkboxot kötelező jelleggel, 
	 * majd rákattint a "Place Order" gombra.
	 */
	@Step("3.lépés: utánvétes fizetési mód kiválasztása és az ÁSZF elfogadása majd rendelés végelegsítése")
	public void placeOrder() {
		click(paymentCashOnDelivery);
		click(termsOfService);
		click(placeOrderButton);
	}
	
	/**
	 * Lekérdezi a sikeres rendelés esetén megjelenő jóváhagyó szöveget.
	 * @return A rendelést visszaigazoló üzenet.
	 */
	@Step("Rendelés visszaigazolásának lekérdezése") 
		public String getConfirmationText() {
			return getText(orderConfirmationText);
	}
	
}
