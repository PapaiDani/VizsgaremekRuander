package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Regisztráció (Create an account)
	 * Funkció: A regisztrációs űrlap oldalt modellezi.
	 * Tartalmazza a személyes adatok bevitelét és az adatkezelési / ÁSZF nyilatkozatok elfogadását.
	 */
public class RegistrationPage extends BasePage {
	
	// Lokátorok az űrlap mezőihez
	private By socialTitleMr = By.xpath("//input[@id='field-id_gender-1']/..");
	private By socialTitleMrs = By.xpath("//input[@id='field-id_gender-2']/..");
	private By firstNameField = By.id("field-firstname");
	private By lastNameField = By.id("field-lastname");
	private By emailField = By.id("field-email");
	private By passwordField = By.id("field-password");
	private By birthdateField = By.id("field-birthday");
	
	// Lokátorok a GDPR, adatvédelmi boxokhoz 
	private By termsAndConditionCheckbox = By.xpath("//input[@name='psgdpr']/..");
	private By custumerDataPrivacyCheckbox = By.xpath("//input[@name='customer_privacy']/..");
	
	// Lokátor a mentés gombhoz
	private By saveButton = By.cssSelector("button[data-link-action='save-customer']");
	
	// Lokátor a hibaüzenetre
	private By errorMessage = By.cssSelector(".alert-danger, .help-block .alert-danger");
	
	public RegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Navigálás a megadott fix URL-re
	 */
	public void open() {
		driver.get("http://localhost/registration");
	}

	/**
    * Kitölti a regisztrációs űrlapot a paraméterben kapott adatokkal.
    * Végighalad a szükséges beviteli mezőkön a BasePage.type() metódus segítségével. 
    * Külön paraméterekként veszi át az űrlap sorait.
    */
	@Step("Regisztrációs űrlap kitöltése: Név: {0} {1}, Email: {2}")
	public void registration(String firstName, String lastName, String email, String password, String birthdate) {
		type(firstNameField, firstName);
		type(lastNameField, lastName);
		type(emailField, email);
		type(passwordField, password);
		type(birthdateField, birthdate);
	}
	
	/**
	 * Bepipálja a "MR" Social Title boxot
	 */
	@Step("Bepipálja a Social title boxot")
	public void acceptSocialTitle() {
		click(socialTitleMr);
	}
	
	/**
 	 * Bepipálja a kötelező adatkezelési és felhasználási feltételek (GDPR, ÁSZF) checkboxokat.
	 * Egymás után hívja meg a két check boxra (TermsAndCondition és CustumerDataPrivacy) a klikkelést.
	 */
	@Step("Adatvédelmi nyilatkozat és ÁSZF elfogadása Checkboxok bepipálása")
	public void acceptPrivacyAndTerms() {
		click(custumerDataPrivacyCheckbox);
		click(termsAndConditionCheckbox);
	}
	
	/**
	 * Rákattint a Save gombra.
	 */
	@Step("Kattintás a Save gombra a regisztráció véglegesítéséhez")
	public void clickSaveButton() {
		click(saveButton);
	}
	
	/**
     * Kiolvassa a regisztrációs űrlapon megjelenő hibaüzenetet
     * A BasePage getText() metódusát használja, ami kifejezetten megvárja visibilityOfElementLocated
     * segítségével, amíg az üzenet felugrik a képernyőn.
     * @return A hibaüzenet (String).
     */
    @Step("Hibaüzenet lekérdezése az űrlapról")
    public String getRegistrationErrorMessage() {
        return getText(errorMessage);
    }
	
}



