package Pages;

import java.time.Duration;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class searchPage {
	WebDriver driver = null;
	
	By searchBar = By.xpath("//div/header/div/div[2]/div[1]");
	By whereLabel = By.xpath("//label[@for = 'bigsearch-query-location-input']");
	By locationInput = By.id("bigsearch-query-location-input");
	By locationsListbox = By.id("bigsearch-query-location-listbox");
	By checkinLabel = By.xpath("//div[@data-testid = 'structured-search-input-field-split-dates-0']");
	By guestsLabel = By.xpath("//div[@data-testid = 'structured-search-input-field-guests-button']");
	By guestsPanel = By.xpath("//div[@data-testid = 'structured-search-input-field-guests-panel']");
	By adultsIncreaseButton = By.xpath("//button[@data-testid = 'stepper-adults-increase-button']");
	By childrenIncreaseButton = By.xpath("//button[@data-testid = 'stepper-children-increase-button']");
	By searchButton = By.xpath("//button[@data-testid = 'structured-search-input-search-button']");
	By locationDiv = By.xpath("//button[@data-index = '0']/div");
	By dateDiv = By.xpath("//button[@data-index = '1']/div");
	By guestsDiv = By.xpath("//button[@data-index = '2']/div");
	
	
	public searchPage(WebDriver driver) {
		this.driver = driver;
	}
	

	public void openSearchBar() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
		driver.findElement(searchBar).click();
	}
	
	
	public void enterDestination(String destination) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(whereLabel));
		
		driver.findElement(whereLabel).click();
		driver.findElement(locationInput).sendKeys(destination);
		
		String destinationDiv = "//div[text() = '" + destination + "']";
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(locationsListbox));
        driver.findElement(By.xpath(destinationDiv)).click();
	}
	
	public void selectDates() {
		LocalDate currentDate = LocalDate.now();
        LocalDate checkInDate = currentDate.plusWeeks(1);
        LocalDate checkOutDate = checkInDate.plusWeeks(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String checkInDateStr = checkInDate.format(formatter);
        String checkOutDateStr = checkOutDate.format(formatter);
		driver.findElement(checkinLabel).click();
		driver.findElement(By.xpath("//div[@data-testid = 'calendar-day-" + checkInDateStr + "']")).click();
		driver.findElement(By.xpath("//div[@data-testid = 'calendar-day-" + checkOutDateStr + "']")).click();
	}
	
	public void guestsNumber(int adults, int children) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(guestsLabel).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(guestsPanel));
		
		
		for (int i = 0; i < adults; i++) {
	        driver.findElement(adultsIncreaseButton).click();
	    }

	    for (int i = 0; i < children; i++) {
	    	driver.findElement(childrenIncreaseButton).click();
	    }
		
		}
	
	public void clickSearch() {
		
		driver.findElement(searchButton).click();
	}

	public void verifyFilters(String destination, String guests) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
		
		WebElement currentLocation = driver.findElement(locationDiv);
        String expectedlocation = destination;
        WebElement currentGuests = driver.findElement(guestsDiv);
        String expectedGuests = guests;
        
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = 'Rome']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(guestsDiv));
        driver.findElement(By.xpath("//div[text() = 'Rome']")).isDisplayed();
        
        Assert.assertEquals(expectedlocation, currentLocation.getText());
        Assert.assertEquals(expectedGuests, currentGuests.getText());
        checkSelectedDates();
        
	}
	
	public void verifySearchResultsHeader(String expectedInHeader) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@elementtiming='LCP-target']/span")));
		
		String actualText = driver.findElement(By.xpath("//h1[@elementtiming='LCP-target']/span")).getText();
		Assert.assertTrue(actualText.contains(expectedInHeader));
	}
	
	public void checkSelectedDates() {
		
		openSearchBar();
		LocalDate currentDate = LocalDate.now();
        LocalDate checkInDate = currentDate.plusWeeks(1);
        LocalDate checkOutDate = checkInDate.plusWeeks(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String checkInDateStr = checkInDate.format(formatter);
        String checkOutDateStr = checkOutDate.format(formatter);
		//driver.findElement(checkinLabel).click();
		WebElement checkInDay = driver.findElement(By.xpath("//div[@data-testid = 'calendar-day-" + checkInDateStr + "']/parent::td"));
		WebElement checkOutDay = driver.findElement(By.xpath("//div[@data-testid = 'calendar-day-" + checkOutDateStr + "']/parent::td"));
		
		String checkInAtt = checkInDay.getAttribute("aria-label");
		String checkOutAtt = checkOutDay.getAttribute("aria-label");
		
		Assert.assertTrue(checkInAtt.contains("Selected check-in date."));
		Assert.assertTrue(checkOutAtt.contains("Selected checkout date."));
	}
	
}

