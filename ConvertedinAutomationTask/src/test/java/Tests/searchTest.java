package Tests;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.junit.Assert.*;
import Pages.searchPage;

public class searchTest {

private static WebDriver driver;
	
	
	@BeforeTest	
		public void setUp() throws Exception {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.get("https://www.airbnb.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label = 'Airbnb homepage']")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,100)");
			}
	
	
	@Test
	public static void searchForVacationPlace() {
		searchPage search_Page = new searchPage(driver);
		
		String Destination = "Rome, Italy";
		int numOfAdults = 2;
		int numOfChildren = 1;
		String expectedFilterLocation = "Rome";
		String expectedGuestsNumber = String.valueOf(numOfAdults + numOfChildren);
		String expectedGuestText = expectedGuestsNumber + " guests";
		
		search_Page.openSearchBar();
		search_Page.enterDestination(Destination);
		
		search_Page.selectDates();
		search_Page.guestsNumber(numOfAdults, numOfChildren);
		
		search_Page.clickSearch();
		search_Page.verifyFilters(expectedFilterLocation, expectedGuestText);
		search_Page.verifySearchResultsHeader(expectedFilterLocation);
		
	}
	
	
	
	@AfterTest
    public void tearDown()
    {
       driver.quit();
  
    }
	
}
