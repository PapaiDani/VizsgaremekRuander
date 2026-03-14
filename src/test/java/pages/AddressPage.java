package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Szállítási cím (New Address / Update Address) űrlap
	 * Funkció: Ez a Page Object a felhasználó szállítási és számlázási címeinek kezelését modellezi. 
	 * Biztosítja a szükséges metódusokat új cím rögzítéséhez, vagy meglévő frissítéséhez.
	 */

public class AddressPage extends BasePage {

	//Lokátorok az űrlaphoz
	private By aliasField = By.id("field-alias"); 
	private By firstNameField = By.id("field-firstname");
	private By lastNameField = By.id("field-lastname");
	private By companyField = By.id("field-company");
	private By vatNumberField = By.id("field-vat_number");
	private By addressField = By.id("field-address1");
	private By addressComplementField = By.id("field-address2");
	private By cityField = By.id("field-city");
	private By zipCodeField = By.id("field-postcode");
	private By countryField = By.id("field-id_country");
	private By phoneField = By.id("field-phone");
	
	// Lokátorok a legördülő menükhöz
	private By countryDropdown = By.id("field-id_country");
	private By stateDropdown = By.id("field-id_state"); // Ez csak akkor jelenik meg, ha US van kiválasztva!
	
	// Lokátorok mentés gomb és sikeres mentést igazoló zöld doboz
	private By saveButton = By.cssSelector("button.btn-primary[type='submit']");
	private By succesFeedback = By.cssSelector("article.alert-success");
	private By unsuccessfulMessage = By.cssSelector("article.alert-danger");
	
	//Lokátor: Update gombhoz
	// Ha több cím is van, a Selenium alapértelmezetten a legelsőt fogja megtalálni és arra kattint.
	private By updateButton = By.cssSelector("a[data-link-action='edit-address']");
	
	public AddressPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Navigálás a megadott fix URL-re
	 */
	@Step("New address oldal megnyitása")
	public void openNewAddress() { 
		driver.get("http://localhost/address");
	}
	
	/**
	 * Navigálás a megadott fix URL-re
	 */
	@Step("New address oldal megnyitása")
	public void openNewAddresses() { 
		driver.get("http://localhost/addresses");
	}
	
	
	/**
     * Ország kiválasztása a legördülő menüből.
     * Példányosít egy Selenium Select objektumot a kapott WebElement-ből, majd 
     * a látható szöveg (visibleText) alapján kiválasztja az opciót.
     * @param countryName A kiválasztandó ország pontos neve.
     */
	@Step("Ország kiválasztása: {countryName}")
    public void selectCountry(String countryName) {
        // 1. Megkeressük az elemet a BasePage find() metódusával (így meg is várja!)
        WebElement countryElement = find(countryDropdown);
        
        // 2. Rátesszük a Selenium Select objektumát
        Select select = new Select(countryElement);
        
        // 3. Kiválasztjuk a látható szöveg alapján
        select.selectByVisibleText(countryName);
    }
	
	/**
     * Állam kiválasztása a legördülő menüből.
     * Csak akkor jelenik meg ez a mező, ha bizonyos országokat (pl. US) választunk,
     * emiatt a beépített explicit várakozás (find() metódus) kötelező az elem megtalálásához.
     * @param stateName A kiválasztandó állam neve.
	 */
	@Step("Állam kiválasztása: {stateName}")
    public void selectState(String stateName) {
        WebElement stateElement = find(stateDropdown);
        Select select = new Select(stateElement);
        select.selectByVisibleText(stateName);
    }
	
	/**
     * Kitölti a teljes cím űrlapot a paraméterben kapott adatokkal.
     * Végighalad a szükséges beviteli mezőkön a BasePage.type() metódus
     * segítségével, majd végül kattint a mentés (Save) gombra.
     */
	@Step("Address űrlap kitöltése")
	public void fillAddressForm (String country, String alias, String company, String address, String addressComplement, String city, String state, String zipCode, String phone) {
		
		selectCountry(country); 
		selectState(state); 
		type(aliasField, alias);
		type(companyField, company);
		type(addressField, address); 
		type(addressComplementField, addressComplement);
		type(cityField, city);
		type(zipCodeField, zipCode);
		type(phoneField, phone);
		
		click(saveButton);
	}
	
	/**
     * Kitörli a benne lévő szöveget, majd kitölti a teljes cím űrlapot a paraméterben kapott adatokkal.
     * a BasePage.type() metódusa tartalmazza a WebElement.clear() hívást,
     * a régi szöveget automatikusan törli a gépelés előtt.
     */
	@Step("Address űrlap kitöltése a benne lévő szöveg törlésével")
	public void clearAndfillAddressForm(String alias, String address, String city, String zipCode,  String phone) {
		type(aliasField, alias);
		type(addressField, address);
		type(cityField, city);
		type(zipCodeField, zipCode);
		type(phoneField, phone);
		click(saveButton);
	}
	
	
	/**
     * Lekérdezi a sikeres mentés után megjelenő zöld doboz szövegét.
     * A BasePage.getText() metódusát hívja meg, ami megvárja a DOM-ban az üzenet felbukkanását.
     * @return Az igazoló üzenet szövege.
     */
	
	@Step("Sikeres mentés üzenet lekérdezése")
	public String getSuccesMessage() {
		return getText(succesFeedback);
	}
	
	/**
	 * Hibás mentés (pl. rossz validáció) esetén megjelenő piros üzenet beolvasása.
	 * @return A hibaüzenet szövege.
	 */
	@Step("Sikertelen mentés üzenet lekérdezése")
	public String getUnsuccessfulMessage() {
		return getText(unsuccessfulMessage);
	}
	
	/**
     * Rákattint a listában szereplő legelső cím "Update" gombjára.
     * A cssSelector-hoz tartozó találatok közül a driver.findElement által 
     * visszaadott legelső elemre kattint, majd visszaadja önmagát (a frissült AddressPage oldalt).
     * @return Az új AddressPage objektum.
     */
    @Step("Kattintás az első mentett cím 'Update' gombjára")
    public AddressPage clickUpdateFirstAddress() {
        click(updateButton);
        return new AddressPage(driver);
    }

}
