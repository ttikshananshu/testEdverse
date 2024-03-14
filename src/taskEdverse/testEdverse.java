package taskEdverse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class EdverseWebsite {
	private WebDriver driver;

	public EdverseWebsite() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tusha\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	public void openWebsite(String url) {
		driver.get(url);
	}

	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	public void checkBrowserStatus() {
		String title = driver.getTitle();
		System.out.println(title);
		String url = driver.getCurrentUrl();
		System.out.println(url);
		Assert.assertEquals(url, "https://www.edverse.com/");
	}

	public void loginWithExcelData(String excelFilePath) throws IOException, InterruptedException {

		FileInputStream excelFile = new FileInputStream(excelFilePath);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheet("login");
		// Loop through each row in the sheet starting from the second row
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				// Extract username and password
				String username = row.getCell(0).getStringCellValue();
				String password = row.getCell(1).getStringCellValue();

				// Perform login using extracted credentials
				WebDriverWait wait = new WebDriverWait(driver, 10);
				WebElement loginButton = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[@class='nav-link puprple_btn loginbtn']")));
				loginButton.click();
				// Perform login using extracted credentials
				WebDriverWait wait1 = new WebDriverWait(driver, 10);

				// Wait for the email input field to be clickable and visible
				WebElement emailInput = wait1.until(ExpectedConditions.elementToBeClickable(By.id("login_email")));

				// Send keys to the email input field
				emailInput.sendKeys(username);

				// Find the password input field and wait for it to be clickable and visible
				WebElement passwordInput = wait1
						.until(ExpectedConditions.elementToBeClickable(By.id("login_password")));

				// Send keys to the password input field
				passwordInput.sendKeys(password);

				// Find the login submit button and wait for it to be clickable and visible
				WebElement loginSubmitButton = wait1
						.until(ExpectedConditions.elementToBeClickable(By.id("loginsubmit")));

				// Click the login submit button
				loginSubmitButton.click();

				// Wait for next iteration (optional)
				try {
					// Wait for logout button to be clickable (indicates successful login)
					WebElement logoutButton = wait1.until(
							ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Logout']")));
					System.out.println("Login successful for username: " + username);
					// Proceed with further actions after successful login
					// Logout code or other actions can be added here
					logoutButton.click(); // Example: Click logout button after successful login
				} catch (TimeoutException e) {
					// TimeoutException indicates login failed
					WebElement errorMessage = driver.findElement(By.id("login_msg"));
					System.out.println(
							"Login failed for username: " + username + ". Error message: " + errorMessage.getText());
					// Proceed with next iteration (next username/password)
					driver.navigate().refresh();
					continue;
				}
			}
		}
		// Close workbook
		workbook.close();
	}

	public void closeBrowser() {
		driver.quit();
	}

	public void signUpWithExcelData(String excelFilePath) throws IOException, InterruptedException {

		// Read data from Excel sheet
		FileInputStream excelFile = new FileInputStream(excelFilePath);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheet("signup");

		// Assuming the data starts from the second row
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				// Extract data from the Excel row
				String email = row.getCell(0).getStringCellValue();
				String firstName = row.getCell(1).getStringCellValue();
				String lastName = row.getCell(2).getStringCellValue();
				String city = row.getCell(3).getStringCellValue();
				String password = row.getCell(4).getStringCellValue();
				String organization = row.getCell(5).getStringCellValue();
				String designation = row.getCell(6).getStringCellValue();

				// Fill form fields with extracted data
				WebElement loginButton = driver
						.findElement(By.xpath("//button[@class='nav-link puprple_btn loginbtn']"));
				loginButton.click();
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

				// Get handles of all open tabs
				Set<String> handles = driver.getWindowHandles();
				// Switch to the new tab
				for (String handle : handles) {
					driver.switchTo().window(handle);
					WebDriverWait wait = new WebDriverWait(driver, 10);
					// Clicking on signUp web element
					WebElement signUp = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//button[@onclick='return open_register_modal()']")));
					signUp.click();
					WebElement emailSignup = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_email")));
					emailSignup.sendKeys(email);

					WebElement nameSignup = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_f_name")));
					nameSignup.sendKeys(firstName);

					WebElement lastnameSignup = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_l_name")));
					lastnameSignup.sendKeys(lastName);

					WebElement cityField = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_city")));
					cityField.sendKeys(city);

					WebElement signupPassword = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_password")));
					signupPassword.sendKeys(password);

					WebElement confirmPassword = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_confirm_password")));
					confirmPassword.sendKeys(password);

					WebElement organizationField = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_institute")));
					organizationField.sendKeys(organization);

					WebElement designationField = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_designation")));
					designationField.sendKeys(designation);

					// click on captcha and verify manually
					WebElement captcha = wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='reCAPTCHA']")));
					captcha.click();
					// Click on signup submit button
					Thread.sleep(10000);
					WebElement signupButton = driver.findElement(By.id("signupsubmit"));
					signupButton.click();

					// Wait for next iteration (optional)
					driver.navigate().refresh();
				}
			}

			// Close the workbook
			workbook.close();
		}
	}

	public void usecaseTab() {
		driver.navigate().refresh();
		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement usecases = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown02")));
		usecases.click();

		System.out.println("clicked on drop-down use cases");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void openMenuTab() throws InterruptedException {

		usecaseTab();
		driver.findElement(By.xpath("//a[@href='https://www.edverse.com/boardroom']")).click();
		System.out.println("clicked on team collaboration");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Client Meetings']")).click();
		System.out.println("clicked on client meetings");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Virtual Onboarding']")).click();
		System.out.println("clicked on virtual onboarding");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Learning']")).click();
		System.out.println("clicked on virtual learning");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Training and Development']")).click();
		System.out.println("clicked on virtual training");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Workshops']")).click();
		System.out.println("clicked on virtual workshop");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Product Launches']")).click();
		System.out.println("clicked on product launch");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Company Townhalls']")).click();
		System.out.println("clicked on townhall");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		usecaseTab();

		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Ted Talks']")).click();
		System.out.println("clicked on ted talk");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		usecaseTab();
		driver.findElement(By.xpath("//span[@class='fw-500' and text()='Award Shows']")).click();
		System.out.println("clicked on award show");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	public void pricingTab() {

		driver.findElement(By.xpath("//a[@href='https://www.edverse.com/pricing']")).click();
		System.out.println("I am inside pricing tab..");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void resoucesDropdown() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement resources = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='drp_btn'])[1]")));
		resources.click();

		System.out.println("clicked on drop-down resources");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void resources() {
		resoucesDropdown();
		driver.findElement(By.xpath("(//a[@href='https://www.edverse.com/blog'])[1]")).click();
		System.out.println("I am Blogs inside resources");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		resoucesDropdown();
		driver.findElement(By.xpath("//a[@href='https://www.edverse.com/asset-library']")).click();
		System.out.println("I am Library inside resources");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		resoucesDropdown();
		driver.findElement(By.xpath("//a[@href='https://www.edverse.com/recruin-case-study']")).click();
		System.out.println("I am case-study inside resources");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		resoucesDropdown();
		driver.findElement(By.xpath("//a[@href='https://www.edverse.com/virtual-guide']")).click();
		System.out.println("I am virtual-guide inside resources");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		resoucesDropdown();

	}

	public void companyDropdown() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement resources = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='drp_btn'])[2]")));
		resources.click();

		System.out.println("clicked on drop-down resources");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void comapanyCases() {

		companyDropdown();
		driver.findElement(By.xpath("//a[@href='https://www.edverse.com/about-us']")).click();
		System.out.println("I am about-us inside company page ");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		companyDropdown();
		driver.findElement(By.xpath("(//a[@href='https://www.edverse.com/contact-us'])[1]")).click();
		System.out.println("I am about-us inside company page ");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	public void demo() {

		driver.findElement(By.xpath("(//a[@href='https://www.edverse.com/contact-us'])[2]")).click();
		System.out.println("I am contact-us inside demo page ");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
}

public class testEdverse {
	public static void main(String[] args) throws InterruptedException, IOException {
		String websiteUrl = "https://www.edverse.com";
		String excelFilePath = "C://Users//tusha//eclipse-workspace//taskEdverse//src//testdata//testdata.xlsx";
		EdverseWebsite edverse = new EdverseWebsite();
		edverse.openWebsite(websiteUrl);

		edverse.maximizeBrowser();
		edverse.checkBrowserStatus();
		// Sign Up
		edverse.signUpWithExcelData(excelFilePath);

		// Login
		edverse.loginWithExcelData(excelFilePath);

		// Open Menu Tab
		edverse.openMenuTab();
		edverse.pricingTab();
		edverse.resources();
		edverse.comapanyCases();
		edverse.demo();
		// Close browser
		edverse.closeBrowser();
	}
}
