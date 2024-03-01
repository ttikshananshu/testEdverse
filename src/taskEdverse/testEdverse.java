package taskEdverse;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
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
		Assert.assertEquals(url,"https://www.edverse.com/");
    }

    public void closeBrowser() {
        driver.quit();
    }

    public void signUp() throws InterruptedException {
        WebElement loginButton = driver.findElement(By.xpath("//button[@class='nav-link puprple_btn loginbtn']"));
        loginButton.click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    
     // Get handles of all open tabs
        Set<String> handles = driver.getWindowHandles();
        // Switch to the new tab
        for (String handle : handles) {
            driver.switchTo().window(handle);
            WebDriverWait wait = new WebDriverWait(driver, 10);
        // Clicking on signUp web element
            WebElement signUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@onclick='return open_register_modal()']")));
            signUp.click();
        // Adding further sign-up actions (fill form fields, etc.)
            
            
            for (String handle1 : handles) {
                driver.switchTo().window(handle1);
                WebDriverWait wait1 = new WebDriverWait(driver, 10);
                //to enter sign up email
           WebElement emailSignup = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_email")));
           emailSignup.click();
           emailSignup.sendKeys("ttikshananshu@gmail.com");
            	//to enter sign up name
           WebElement nameSignup = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_f_name")));
           nameSignup.click();
           nameSignup.sendKeys("Tushar");
           		//to enter sign up Last name
           WebElement lastnameSignup = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_l_name")));
           lastnameSignup.click();
           lastnameSignup.sendKeys("Tikshananshu");
           		//to enter city 
           WebElement city = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_city")));
           city.click();
           city.sendKeys("Gurgaon");
           		//to enter password
           WebElement signupPassword = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_password")));
           signupPassword.click();
           signupPassword.sendKeys("Dieanotherday");
           		//to re-enter password
           WebElement confirmPassword = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_confirm_password")));
           confirmPassword.click();
           confirmPassword.sendKeys("Dieanotherday");
           		//to enter organization
           WebElement organization = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_institute")));
           organization.click();
           organization.sendKeys("Edverse");
           		//to enter designation
           WebElement designation = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_designation")));
           designation.click();
           designation.sendKeys("QA Head");
           		//to click on captcha
           WebElement captcha = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='reCAPTCHA']")));
           captcha.click();
           		//creating a synchronized wait method to wait for manual captcha handling
           synchronized (captcha) {
               try {
                   captcha.wait(16000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           		// click on signup submit button
         //  WebElement signupButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupsubmit")));
           WebElement signupButton = driver.findElement(By.id("signupsubmit"));
           signupButton.click();
         //  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          
           for (String handle2 : handles) {
               driver.switchTo().window(handle2);
              // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
               //WebDriverWait wait2 = new WebDriverWait(driver, 40);
               //click on close button
              // WebElement close = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='close'])[2]")));
               driver.navigate().refresh();
               driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }
        }
      }
    }

    public void login(String username, String password) {
    	driver.findElement(By.xpath("//button[@class='nav-link puprple_btn loginbtn']")).click();
    	 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	Set<String> handles = driver.getWindowHandles();
        // Switch to the new tab
        for (String handle : handles) {
            driver.switchTo().window(handle);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            // Adding values to Email field on login page
            WebElement loginEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_email")));
            loginEmail.click();
            loginEmail.sendKeys(username);
            // Adding values to Password field on login page
            WebElement loginPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_password")));
            loginPassword.click();
            loginPassword.sendKeys(password);
            // clicking on login button
            WebElement loginSubmit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginsubmit")));
            loginSubmit.click();
            System.out.println("Redirecting to profile page");
            driver.findElement(By.xpath("//img[@src='https://www.edverse.com/public/assets/front/theme_new/images/edverse-logo-white.png']")).click();
            
           
           driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          // driver.navigate().back();
           String titleOfTheHomePage = driver.getCurrentUrl();
           System.out.println(titleOfTheHomePage);
           Assert.assertEquals(titleOfTheHomePage, "https://www.edverse.com/");
           
            
        }
    }
    
    public void usecaseTab() {
    	//Explicit wait
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
     	WebElement resources = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='drp_btn'])[1]")));
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
     	WebElement resources = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='drp_btn'])[2]")));
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
	   public static void main(String[] args) throws InterruptedException {
	        String websiteUrl = "https://www.edverse.com";
	        String username = "tushar.mcclane@gmail.com";
	        String password = "Dieanotherday";

	        EdverseWebsite edverse = new EdverseWebsite();
	        edverse.openWebsite(websiteUrl);

	        edverse.maximizeBrowser();
	        edverse.checkBrowserStatus();
	        // Sign Up
	        edverse.signUp();
	        // Login
	        edverse.login(username, password);
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




