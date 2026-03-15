package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
	
	/**
	 * Modul/Oldal: Bejelentkezés (Login)
	 * Funkció: A regisztrált felhasználók bejelentkezési űrlapját, annak validációs 
	 * üzeneteit és a fiók létrehozása (Regisztráció) oldalra történő továbbítást modellezi.
	 */

public class LoginPage extends BasePage{
	
	// Lokátor: A regisztrációs űrlapra mutató link data-link-action attribútumával
	private By createAccountButton = By.cssSelector("a[data-link-action='display-register-form']");
	
	//Lokátorok email, jelszó és login button mezők id beállítása 
	private By emailField = By.id("field-email");
	private By passwordField = By.id("field-password");
	private By loginButton = By.id("submit-login");
	
	//Lokátor: "Authentication failed." mezőhöz.
	private By errorMessage = By.cssSelector(".alert-danger");
	
	//Lokátor: teljes weblap (<body>) nyers szövege
	private By body = By.tagName("body");
			
	public LoginPage(WebDriver driver) {  
		super(driver);
	}
	
	/**
	 * Navigálás a megadott fix URL-re
	 */
	public void open() { 
		driver.get("http://localhost/login?back=http%3A%2F%2Flocalhost%2Flogin%3Fback%3Dmy-account");
	}
	
	/**
	 * Rákattint a fiók létrehozása ("No account? Create one here") linkre.
     * A regisztráció első lépése, amely átviszi a munkamenetet a RegistrationPage-re.
     */
	@Step("Rákattint a fiók létrehozása gombra")
	public void clickCreateAccount() {
		click(createAccountButton);
	}
	
	/**
	 * Kitölti és elküldi a bejelentkezési űrlapot az e-mail+jelszó párossal.
	 * A BasePage-ben deklarált type() metódust használja, ami 
	 * gondoskodik az elemek megtalálásáról és kiürítéséről gépelés előtt.
	 * Végül a click() megvárja, és lenyomja a Sign In gombot.
	 * @param email A tesztelendő e-mail cím.
	 * @param password A tesztelendő jelszó.
	 */
	@Step("Bejelentkezés a következő fiókkal: {0}")
	public void login(String email, String password) { 
		type(emailField, email); 
		type(passwordField, password);
		click(loginButton); 
	}
	
	/**
     * Lekérdezi a sikertelen bejelentkezés után megjelenő hibaüzenet szövegét.
     * @return a hibaüzenet Stringként.
     */
    @Step("A bejelentkezési hibaüzenet lekérdezése")
    public String getErrorMessage() {
        return getText(errorMessage);
    }
	
	/**
	 * Biztonsági ellenőrzésekhez, ahol a PrestaShop 
	 * globális hibaüzenetet szór el a DOM-ban egy osztálynevet sem megjelölve.
	 * Beolvassa a `body` tag nyers szövegét.
	 * @return A teljes oldal látható szövege Stringént.
	 */
	@Step("A teljes weblap (<body>) nyers szövegét leolvasni, és abban keresni a várt szavakat.")
	public String bodyMessage() {
		return getText(body);
	}
}
