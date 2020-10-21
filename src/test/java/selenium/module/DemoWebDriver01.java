package selenium.module;

import user.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DemoWebDriver01 {
     private WebDriver driver;

    @BeforeClass
    public void setUpClass(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUpTest() throws Exception {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();

        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.driver.get("http://automationpractice.com/index.php");
        Assert.assertEquals(driver.getTitle(),"My Store");
    }

    @Test
    public void testFindElement() throws Exception {
        WebElement element = driver.findElement(By.linkText("Sign in"));
    }

    @Test
    public void testFindElements() throws Exception {
        List<WebElement> products = driver.findElements(By.cssSelector(".ajax_block_product"));
        System.out.println(String.format("Number of products in sections: %d", products.size()));
    }

    @Test
    public void testElementGetText() throws Exception {
        WebElement element = driver.findElement(By.className("login"));
        String elementText = element.getText();
        System.out.println(String.format("The element text is: %s", elementText));
    }

    @Test
    public void testElementClick() throws Exception {
        navigateToAuthenticationPage(driver);
    }

    @Test
    public void testLogin() throws Exception {
        User user = new User("qa-academy@mentormate.com", System.getenv("USER_PASSWORD"));

        navigateToAuthenticationPage(driver);

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys(user.getEmail());

        WebElement passwordField = driver.findElement(By.id("passwd"));
        passwordField.clear();
        passwordField.sendKeys(user.getPassword());

        WebElement signButton = driver.findElement(By.id("SubmitLogin"));
        signButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=my-account");
    }

    public static void navigateToAuthenticationPage(WebDriver driver){
        WebElement signInLink = driver.findElement(By.className("login"));
        signInLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=authentication&back=my-account");
    }

    @AfterMethod
    public void tearDownTest() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        // Clean any generated test data
    }
}
