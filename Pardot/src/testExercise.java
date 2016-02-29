
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class testExercise {

	private static String url         = "https://pi.pardot.com/"; //URL of the page to be navigated to
	private static String email1      = "pardot.applicant@pardot.com"; //Email id for login
	private static String password1   = "Applicant2012"; //Password corresponding to the email id for login
	private static String menuOption1 = "Marketing"; //Main menu option
	private static String menuXpath1  = ".//*[@id='dropmenu-marketing']/li"; //xpath for locating Main menu sub menu options list
	private static String subMenu1    = "Segmentation"; //Sub menu option under main menu
	private static String listName1    = "List checkyuttuiuuiyliyriu"; //List name
	private static String renameListName    = "Renamed List test3ioiiiluoiiortt"; //List name to be renamed to
	private static String menuOption2 = "Prospects"; //Main menu option
	private static String menuXpath2  = ".//*[@id='dropmenu-prospects']/li"; //xpath for locating Main menu sub menu options list
	private static String subMenu2    = "Prospect List"; //Sub menu option under main menu
	private static String emailForProspect    = "aauyitytuyyyuulurp@bb.com"; //Email to be entered in Prospects page
	private static String campaignForProspect    = "Adil Yellow Jackets"; //Campaign to be selected in Prospects page
	private static String profileIDForProspect    = "Adil Yellow Jackets 1"; //Profile ID to be selected in Prospects page
	private static String nameInEmail = "Name in emoaiiuliilitrouiyy"; //Name to be entered in Email information
	private static String campaignForEmail    = "Adil Yellow Jackets"; //campaign to be selected in Email information
	private static String subMenu3    = "Emails"; //sub menu option

	
	private static WebDriver driver   = new FirefoxDriver();
	
	public static void main(String[] args) {
		
		//Main flow that will be executed
		System.out.println("Opening the Pardot Login page");
		driver.get(url);
		driver.manage().window().maximize();
		System.out.println("Successfully opened Pardot Login page");
		System.out.println("Entering login credentials");
		login(email1,password1);
		System.out.println("Successfully logged in");
		System.out.println("Opening Marketing->Segmentation");
		listMenuOptions(menuOption1,menuXpath1,subMenu1);
		System.out.println("Successfully opened Marketing->Segmentation");
		System.out.println("Opening the Add List page");
		openAddListPage();
		System.out.println("Successfully opened Add List page");
		System.out.println("Adding a list with name "+listName1);
		addList(listName1);
		System.out.println("Successfully added a new List");
		System.out.println("Renaming an existing List page");
		renameList(renameListName);
		System.out.println("Successfully renamed");
		System.out.println("Process of creating list with already existing name");
		listMenuOptions(menuOption1,menuXpath1,subMenu1);
		openAddListPage();
		addSameList(renameListName);
		System.out.println("Successfully verified validation message for unique list name functionality");
		System.out.println("Navigating to Prospects->Prospect List page");
		listMenuOptions(menuOption2,menuXpath2,subMenu2);
		System.out.println("Successfully opened Prospects->Prospect List page");
		System.out.println("Adding a new prospect");
		addProspect(emailForProspect,campaignForProspect,profileIDForProspect);
		System.out.println("Successfully created a prospect");
		System.out.println("Processing to add a list to the prospect");
		addListToProspect(renameListName);
		System.out.println("Successfully added the list "+renameListName+" to the prospect");
		System.out.println("Processing to navigate to Marketing->Emails");
		listMenuOptions(menuOption1,menuXpath1,subMenu3);
		System.out.println("Successfully navigated to Emails page");
		System.out.println("Processing to send an email");
		sendEmail(nameInEmail,campaignForEmail, listName1);
		System.out.println("Successfully saved email information");
		System.out.println("Processing to logout");
		logout();
		driver.quit();
		}
	
	/**
	 * Business component to Login to the application
	 */
	public static void login(String email,String password){
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
	}
	
	/**
	 * Business component to list menu options and select sub menu option
	 */
	public static void listMenuOptions(String menuOption,String menuXpath,String subMenu) {
		Actions builder = new Actions(driver);
		WebElement we = driver.findElement(By.linkText(menuOption));
		builder.moveToElement(we).build().perform();
		selectMenuOption(menuXpath,subMenu);
	}
	
	/**
	 * Business component to select menu options
	 */
	public static void selectMenuOption(String menuXpath,String subMenu) {		
		List<WebElement> menuOptions	= driver.findElements(By.xpath(menuXpath));
		boolean dataFound				= false;
		for(int i=0; i<menuOptions.size();i++) {
			WebElement menuOption	= menuOptions.get(i);
			String option			= menuOption.getText().trim();			
			if(option.equalsIgnoreCase(subMenu)) {
				menuOption.click();
				dataFound= true;				
				break;
			}
		}if(!dataFound) {
			System.out.println("Not able to find the menuoption");
		}
	} 
	
	/**
	 * Business component to Open Add List page
	 */
	public static void openAddListPage(){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Add List")).click();
		WebElement nameField = (new WebDriverWait(driver, 20))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
		Boolean listPage = nameField.isDisplayed();
		if(listPage){
			System.out.println("Add List page is loaded");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		}
		else{
			System.out.println("Could not open Add Lists page");

		}
	}
	
	/**
	 * Business component to Add List
	 */
	public static void addList(String listNam){
		driver.findElement(By.id("name")).sendKeys(listNam);
		driver.findElement(By.xpath("//button[@id='save_information']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Business component to verify functionality of validation for adding same named list
	 */
	public static void addSameList(String listNam){
		driver.findElement(By.id("name")).sendKeys(listNam);
		driver.findElement(By.xpath("//button[@id='save_information']")).click();
		if(driver.findElement(By.id("error_for_name")).isDisplayed()){
			System.out.println("Error message for name got displayed: to enter unique value in it");
			driver.findElement(By.xpath("//a[contains(@class,'btn-default')]")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else{
			System.out.println("No validation message got displayed");
		}
	}
	
	/**
	 * Business component to search for a list in Lists page
	 */
	 public static void searchList(String searchListName){
		 driver.findElement(By.xpath("//input[contains(@id,table_filter)]")).sendKeys(searchListName);
		 WebElement listRow = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='"+searchListName+"']")));
		 listRow.click();
		 WebElement listPage = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='content']/h1")));
		 String listName = listPage.getText();
		 if(listName.equalsIgnoreCase(searchListName)){
			 System.out.println("Successfully opened searched list");
		 }
		 else{
			 System.out.println("Could not find the list");
		 }
		 
	 }
	
	 /**
		 * Business component to rename a list
		 */
	 public static void renameList(String renameList){
		 driver.findElement(By.xpath("//a[text()='Edit']")).click();
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 WebElement list = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
		 list.clear();
		 list.sendKeys(renameList);
		 driver.findElement(By.xpath("//button[@id='save_information']")).click();
		 WebElement listPage = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='content']/h1")));
		 String listName = listPage.getText();
		 if(listName.equalsIgnoreCase(renameList)){
			 System.out.println("Successfully renamed the list");
		 }
		 else{
			 System.out.println("Could not rename the list");
		 }
	 }
	 
	 /**
		 * Business component to add a prospect
		 */
	 public static void addProspect(String emailPr,String campaignValue,String profileIDValue){
		 driver.findElement(By.id("pr_link_create")).click();
		 WebElement email = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
		 email.sendKeys(emailPr);
		 Select campaign = new Select(driver.findElement(By.id("campaign_id")));
		 campaign.selectByVisibleText(campaignValue);
		 Select profileID = new Select(driver.findElement(By.id("profile_id")));
		 profileID.selectByVisibleText(profileIDValue);
		 driver.findElement(By.xpath("//input[contains(@value, 'Create prospect')]")).click();
		 WebElement prospect = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-info')]")));
		 String prospectSuccessNote = prospect.getText();
		 if(prospectSuccessNote.equalsIgnoreCase("Prospect saved successfully")){
			 System.out.println("Prospect successfully got saved");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 }
		 else{
			 System.out.println("There was a problem in adding prospect");
		 }
	 }

	 /**
		 * Business component to add a list to the prospect
		 */
	 public static void addListToProspect(String listName){
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		 driver.findElement(By.xpath(".//*[@id='center-stage']/div[2]/div/ul/li[2]/a")).click();
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 driver.findElement(By.xpath(".//*[@class='chzn-container chzn-container-single']/a")).click();
		 WebElement listSearchKey = driver.findElement(By.xpath("//*[@class='chzn-search']/input"));
		 listSearchKey.sendKeys(listName);
		 listSearchKey.sendKeys(Keys.ENTER);
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 String listSelected = driver.findElement(By.xpath("//*[@class='chosen-multi-select']/label")).getText();
		 if(listSelected.equalsIgnoreCase("1 list selected")){
			 System.out.println("List is selected for adding to the Prospect");
			 driver.findElement(By.xpath("//input[contains(@value,'Save lists')]")).click();
		 }
		 else{
			 System.out.println("List is not selected for adding to the Prospect");
		 }
	 }
	 
	 /**
		 * Business component to send email
		 */
	 public static void sendEmail(String nameInEmail,String campaign, String listName){
		 driver.findElement(By.xpath("//a[contains(@class,'btn-warning')]")).click();
		 WebElement name = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
		 name.sendKeys(nameInEmail);
		 driver.findElement(By.xpath("//*[@class='control-group required campaign_errors']//button[contains(@class,'choose-asset')]")).click();
		 WebElement filter = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@class,'filter-by')]")));
		 filter.sendKeys(campaign);
		 driver.findElement(By.xpath("//*[contains(@class,'well well-small media folder-row clearfix')]")).click();
		 driver.findElement(By.xpath("//button[@id='select-asset']")).click();
		 driver.findElement(By.id("email_type_text_only")).click();
		 driver.findElement(By.xpath(".//input[@id='from_template']")).click();
		 driver.findElement(By.xpath(".//a[@id='save_information']")).click();
		 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	 }
	 
	 /**
		 * Business component to Logout
		 */
	 public static void logout(){
		 Actions action = new Actions(driver);
		 action.moveToElement(driver.findElement(By.id("acct-tog"))).build().perform();
		 driver.findElement(By.xpath(".//*[@id='dropmenu-account']/li[9]/a")).click();
		 if(driver.findElement(By.id("email_address")).isDisplayed()){
			 System.out.println("Successfuly logged out");
		 }
		 else{
			 System.out.println("Could not logout");
		 }
	 }
}
		
