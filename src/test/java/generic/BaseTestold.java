package generic;

import java.lang.reflect.Method;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTestold {

	public static ExtentReports extentReports;
	
	public WebDriver driver;
	public WebDriverWait wait;
	public ExtentTest extentTest;
	
		
	@BeforeSuite
	public void createReport() {
		extentReports=new ExtentReports();
		ExtentSparkReporter spark=new ExtentSparkReporter("./result/MyReport.html");
		extentReports.attachReporter(spark); 
	}
	
	@AfterSuite
	public void publishReport() {
		extentReports.flush();	
	}
	
	@BeforeMethod
	public void openApp(Method testMethod) {

		String testName=testMethod.getName();
		extentTest = extentReports.createTest(testName);
		
		
		WebDriverManager.chromedriver().setup();
		String path=System.getProperty("webdriver.chrome.driver");
		
		extentTest.log(Status.INFO, "Set the path of driver exe:"+path);
		
		extentTest.log(Status.INFO, "Open the browser");
		driver=new ChromeDriver();
		
		extentTest.log(Status.INFO, "Set the ITO"); //Implicit Time Out
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		extentTest.log(Status.INFO, "Set the ETO"); //Explicit Time Out
		wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		
		extentTest.log(Status.INFO, "Enter the URL");
		driver.get("http://www.google.com");
	}
	
	@AfterMethod
	public void closeApp() {
		
		extentTest.log(Status.INFO, "Close the browser");
		driver.quit();
	}
}
