package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.qameta.allure.Step;

	/**
	 * Modul/Oldal: Kezdőlap (Home Page) és globális fejléc
	 * Funkció:  A PrestaShop főoldalát és a minden oldalon jelen lévő fejléc/menü 
	 * komponenseket reprezentálja. Felelős a menüpontok közti navigációért,
	 * a globális kereső kezeléséért, és a kijelentkezés indításáért.
	 */

public class HomePage extends BasePage {
	
	// Lokátor: A fejlécben található "Sign in" gomb (CSS Szelektor a user-info osztályon belül)
	private By signInButton = By.cssSelector(".user-info a");
	
	// Lokátor: Sikeres belépés/regisztráció után itt jelenik meg a felhasználó neve
	private By loggedInUserName = By.cssSelector(".user-info .account span");
	
	// Lokátor: Clothes menü gombja
	private By clothesMenuButton = By.cssSelector("#category-3 a");
	
	// Lokátor: Accessories menü gombja
	private By accessoriesMenuButton = By.cssSelector("#category-6 a");
	
	//Lokátor: Art menü gombja
	private By artMenuButton = By.cssSelector("#category-9 a");
	
	//Lokátor: A fejlécben található keresőmező
	private By search = By.name("s");
	
	//Lokátor: Sign out gomb a fejlécben
	private By signOut = By.cssSelector(".user-info a.logout");

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Navigálás a megadott fix URL-re
	 */
	@Step("Főoldal megnyitása")
	public void open() { 
		driver.get("http://localhost/");
	}
	
	/**
	 * Az "Összes termék" listázó url közvetlen elérése.
	 * Visszatérési értékként egy friss CategoryPage objektumot ad vissza, 
	 * mivel az újonnan betöltött oldal ezen az osztályon alapszik.
	 * @return CategoryPage objektum.
	 */
	@Step("New products oldal megnyitása")
	public CategoryPage openAllProducts() {
		driver.get("http://localhost/2-home");
		return new CategoryPage(driver);
	}
	
	/**
	 * Rákattint a fejlécben lévő bejelentkezés gombra.
	 */
	@Step("Kattintás a 'Sign in' gombra a főoldalon")
	public void clickSignIn() {
		click(signInButton);
	}
	
	/**
	 * Lekérdezi a bejelentkezett felhasználó nevét a fejlécből
	 * @return a felhasználó neve Stringként. 
	 */
	@Step("A bejelentkezett felhasználó nevének lekérdezése a fejlécből")
	public String getLoggedInUserName() {
		return getText(loggedInUserName);
	}
	
	/** Rákattint a "Clothes" menüpontra a navigációs sávban.
	 * Kattintás után átkerülünk egy új oldalra, 
	 * a metódus egy új Page Objecttel (CategoryPage) tér vissza, amire láncolható a tesztelés.
	 * @return Újonnan inicializált CategoryPage objektum.
	 */
	@Step("Kattintás a 'Clothes' menüpontra")
	public CategoryPage clickClothesMenu() {
		click(clothesMenuButton);
		return new CategoryPage(driver);
	}
	
	/**
	 * Rákattint az "Accessories" menüpontra a navigációs sávban.
	 * A kategória váltás után átadja a vezérlést az új CategoryPage objektumnak.
	 * @return Újonnan inicializált CategoryPage objektum.
	 */
	@Step("Kattintás az 'Accessories' menüpontra")
	public CategoryPage clickAccessoriesMenu() {
		click(accessoriesMenuButton);
		return new CategoryPage(driver);
	}

	
	/**
	 * Rákattint az "Art" menüpontra a navigációs sávban.
	 * @return Újonnan inicializált CategoryPage objektum.
	 */
	@Step("Kattintás az 'Art' menüpontra")
	public CategoryPage clickArtMenu() {
		click(artMenuButton);
		return new CategoryPage(driver);
	}
	
	/**
     * Keresés indítása a megadott kulcsszóval.
     * A metódus kitörli a mező tartalmát, beírja a szót, majd a form beküldésével 
     * (submit) átnavigál a találati oldalra.
     * @param keyword A kifejezés vagy karakterlánc, amire keresni szeretnénk (pl. "Mug").
     * @return Egy új SearchResultPage objektum, mivel a keresés után megváltozik az oldal.
     */
	@Step("Keresés indítása a globális keresőben a következő szóra: '{keyword}'")
	public SearchResultPage searchFor (String keyword) {
		WebElement searchBox = find(search);
		
		// Biztonsági törlés gépelés előtt
		searchBox.clear();
		searchBox.sendKeys(keyword);
		
		// Enter billentyű megnyomását szimulálja
		searchBox.submit();
		
		return new SearchResultPage(driver);	
	}
	
	/**
     * Rákattint a Kijelentkezés gombra a fejlécben.
     */
	@Step("Kijelentkezés gombra kattintás")
	public void clickSignOut() {
        click(signOut);
	}
	
	/**
     * Ellenőrzi, hogy a "Sign in" (Bejelentkezés) gomb látható-e a fejlécben.
     * Ez a bizonyítéka annak, hogy a felhasználó sikeresen kijelentkezett.
     * @return true, ha a gomb látható, egyébként false.
     */
    public boolean isSignInButtonVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(signInButton)).isDisplayed();
        } catch (Exception e) {
            return false; // Ha letelik az idő és nem találja, akkor hamisat ad vissza
        }
    }
}
