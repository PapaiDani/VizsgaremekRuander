package tests;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

	/**
	 * Minden tesztosztály ősosztálya. 
	 * Felelős a WebDriver elindításáért és leállításáért tiszta (inkognító) környezetben.
	 * Továbbá támogatja a CI/CD (pl. GitHub Actions) pipeline-okban való futtatást:
	 * ha a RUN_HEADLESS környezeti változó értéke "true", a böngésző headless 
	 * módban, a konténeres futáshoz szükséges beállításokkal indul.
	 */

public class BaseTest {

	protected WebDriver driver;
	
	/**
	 * Minden teszt futás előtt lefutó inicializáló metódus.
	 * Beállítja a Chrome opciókat (inkognitó mód), valamint a "RUN_HEADLESS" 
	 * környezeti változótól függően a headless és CI konténer-specifikus paramétereket
	 * (no-sandbox, dev-shm-usage, stb.). Ezután elindítja és maximalizálja a böngészőt.
	 */
	@BeforeEach
	public void setup() {
		
		
		ChromeOptions options = new ChromeOptions();  
		options.addArguments("--incognito");
		
		//BEÁLLÍTÁSOK ÖSSZEGYŰJTÉSE
		String runInHeadless = System.getenv("RUN_HEADLESS");
			if ("true".equals(runInHeadless)) {
		        System.out.println("-> GitHub Actions érzékelve: Headless paraméterek hozzáadása...");
				options.addArguments("--headless=new");
		        options.addArguments("--no-sandbox"); 
		        options.addArguments("--disable-dev-shm-usage"); // Docker memóriakorlát miatt kell!
		        options.addArguments("--window-size=1920,1080"); // Virtuális monitor felbontása
				}

				driver = new ChromeDriver(options);
				
				driver.manage().window().maximize(); 
			}
	
	
	/**
	 * Driver bontása
	 */
	@AfterEach
	public void tearDown() {  
		driver.quit();
	}

}
