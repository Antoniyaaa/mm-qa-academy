package selenium.module;

import org.apache.commons.lang3.RandomStringUtils;
import user.Address;
import user.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DemoWebDriver02 {
     private WebDriver driver;
     private User user;


    @BeforeClass
    public void setUpClass(){
        WebDriverManager.chromedriver().setup();
        this.user = new User(generateRandomEmail(), "selenium.course");
    }


    @BeforeMethod
    public void setUpTest() throws Exception {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.driver.get("http://automationpractice.com/index.php");

        //Explicit wait
        wait.until(ExpectedConditions.titleIs("My Store"));
        //Explicit wait

        //Assert.assertEquals(driver.getTitle(),homePageTitle);
    }

    @Test
    public void testImplicitWait() throws Exception {
        WebElement signInLink = driver.findElement(By.linkText("Sign in"));
        signInLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=authentication&back=my-account");
    }

    @Test
    public void testImplicitWaitElementNotVisible() throws Exception {
        List<WebElement> products = driver.findElements(By.cssSelector(".ajax_block_product"));
/*        System.out.println(String.format("Quick Button href: %s",products.get(0)
                .findElement(By.cssSelector(".quick-view"))
                .getAttribute("href")));*/

        products.get(0)
                .findElement(By.cssSelector(".quick-view"))
                .click();
    }

    @Test
    public void testExplicitWait() throws Exception {
        //System.out.println("Start testExplicitWait");

        //document.getElementById("SubmitCreate").disabled = true
        navigateToCreateAccountPage(this.driver, user);
    }

    public static void navigateToCreateAccountPage(WebDriver driver, User user){
        DemoWebDriver01.navigateToAuthenticationPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, 15);

        WebElement emailCreateField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email_create")));
        emailCreateField.sendKeys(user.getEmail());

        WebElement createAccountButton = driver.findElement(By.id("SubmitCreate"));
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        createAccountButton.click();

        wait.until(ExpectedConditions.urlToBe("http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation"));
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(),"CREATE AN ACCOUNT");
    }

    @Test
    public void testRadioButtons() throws Exception {
        List<String> radioButtonsValues = Arrays.asList("1", "2");
        String randomValue = radioButtonsValues.get(ThreadLocalRandom.current().nextInt(radioButtonsValues.size()));

        navigateToCreateAccountPage(this.driver, user);

        //Assert.assertTrue(selectRadioButton(this.driver, By.name("id_gender"), randomValue), "Title cannot be selected!");
        selectRadioButton(this.driver, By.name("id_gender"), randomValue);
    }

    @Test
    public void testCheckBoxes() throws Exception {
        navigateToCreateAccountPage(this.driver, user);

        selectCheckBox(this.driver, By.id("newsletter"));
        selectCheckBox(this.driver, By.id("optin"));
    }

    @Test
    public void testDropDown() throws Exception {
        navigateToCreateAccountPage(this.driver, user);

        Select daysDropdown = new Select(driver.findElement(By.id("days")));
        int dayIndex = ThreadLocalRandom.current().nextInt(daysDropdown.getOptions().size());
        System.out.println(String.format("Selected Day Index: %d", dayIndex));
        daysDropdown.selectByIndex(dayIndex);
        Assert.assertTrue(daysDropdown.getAllSelectedOptions().size()>0);

        Select monthDropdown = new Select(driver.findElement(By.id("months")));
        String month = monthDropdown.getOptions().get(ThreadLocalRandom.current().nextInt(monthDropdown.getOptions().size())).getText();
        System.out.println(String.format("Selected Month: %s", month));
        monthDropdown.selectByVisibleText(month);
        Assert.assertTrue(monthDropdown.getAllSelectedOptions().size()>0);

        Select yearDropdown = new Select(driver.findElement(By.id("years")));
        String year = yearDropdown.getOptions().get(ThreadLocalRandom.current().nextInt(yearDropdown.getOptions().size())).getAttribute("value");
        System.out.println(String.format("Selected Year value: %s", year));
        yearDropdown.selectByValue(year);
        Assert.assertTrue(yearDropdown.getAllSelectedOptions().size()>0);
    }

    public static void selectRadioButton(WebDriver driver, By radioButtonsLocator, String value){
        List<WebElement> titles = driver.findElements(radioButtonsLocator);
        for (WebElement title : titles) {
            if (title.getAttribute("value").equals(value)) {
                Assert.assertTrue(selectElement(title), String.format("Unable to select radio button with value: %s!!!", value));
                return;
            }
        }
        throw new IllegalArgumentException(String.format("There is no Radio Button with Value: %s", value));
    }
    
    public static void selectCheckBox(WebDriver driver, By checkBoxLocator){
        WebElement checkBox = driver.findElement(checkBoxLocator);
        Assert.assertTrue(selectElement(checkBox), String.format("Unable to select checkBox located by: %s", checkBoxLocator));
    }

    public static boolean selectElement(WebElement element){
        if(element.isSelected()){
            throw new IllegalStateException("The element is already selected!!!");
        }

        element.click();
        return element.isSelected();
    }

    @Test
    public void createAccount() throws Exception {
        User user = new User(generateRandomEmail(), RandomStringUtils.randomNumeric(9), User.Title.MR,"Test", "User", true, true, LocalDate.now().minusYears(30), "Mentormate", "+359845907234", "+359856907012");
        Address address = new Address("30 WALL ST, BINGHAMTON NY", "floor 4, ap. 5", "New York", "New York", "10013", "United States", "Security check required to enter", "Home");

        populatePersonalInformation(user);

        populateAddressInformation(user, address);

        //Click submit button
        driver.findElement(By.id("submitAccount")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=my-account");
        Assert.assertEquals(driver.findElement(By.className("page-heading")).getText(), "MY ACCOUNT");
        Assert.assertEquals( driver.findElement(By.className("account")).getText(),String.format("%s %s", user.getFirstName(), user.getLastName()));
    }

    private void populatePersonalInformation(User user) throws Exception {

        navigateToCreateAccountPage(this.driver, user);

        //Select title
        selectRadioButton(this.driver, By.name("id_gender"), user.getTitle().getTitleValue());

        //Populate first and last name
        driver.findElement(By.id("customer_firstname")).sendKeys(user.getFirstName());
        driver.findElement(By.id("customer_lastname")).sendKeys(user.getLastName());

        //Clear and populate email field
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys(user.getEmail());

        //Enter password
        driver.findElement(By.id("passwd")).sendKeys(user.getPassword());

        //Start Select birthDate
        Select daysDropdown = new Select(driver.findElement(By.id("days")));
        daysDropdown.selectByIndex(user.getBirthDate().getDayOfMonth());

        Select monthDropdown = new Select(driver.findElement(By.id("months")));
        monthDropdown.selectByIndex(user.getBirthDate().getMonthValue());

        Select yearDropdown = new Select(driver.findElement(By.id("years")));
        yearDropdown.selectByValue(Integer.toString(user.getBirthDate().getYear()));
        //End Select birthDate

        //Select checkboxes based on flag
        if(user.isSignedUpForNewsletter()){
            selectCheckBox(this.driver, By.id("newsletter"));
        }

        if(user.isReceivingSpecialOffersFromPartners()){
            selectCheckBox(this.driver, By.id("optin"));
        }
    }

    private void populateAddressInformation(User user, Address address) {
        //Populate first and last name
        driver.findElement(By.id("firstname")).sendKeys(user.getFirstName());
        driver.findElement(By.id("lastname")).sendKeys(user.getLastName());

        //Populate Company
        driver.findElement(By.id("company")).sendKeys(user.getCompany());

        //Populate address info
        driver.findElement(By.id("address1")).sendKeys(address.getStreet());
        driver.findElement(By.id("address2")).sendKeys(address.getBuildingInfo());
        driver.findElement(By.id("city")).sendKeys(address.getCity());

        Select stateDropdown = new Select(driver.findElement(By.id("id_state")));
        stateDropdown.selectByVisibleText(address.getState());

        driver.findElement(By.id("postcode")).sendKeys(address.getPostalCode());

        Select countryDropdown = new Select(driver.findElement(By.id("id_country")));
        countryDropdown.selectByVisibleText(address.getCountry());

        driver.findElement(By.id("other")).sendKeys(address.getAdditionalInformation());

        //Populate phones
        driver.findElement(By.id("phone")).sendKeys(user.getHomePhone());
        driver.findElement(By.id("phone_mobile")).sendKeys(user.getMobilePhone());

        //Populate Address Alias
        WebElement addressAlias = driver.findElement(By.id("alias"));
        addressAlias.clear();
        addressAlias.sendKeys(address.getAlias());
    }

    private String generateRandomEmail() {
        return String.format("qa-academy+%s@mentormate.com",RandomStringUtils.randomNumeric(5));
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
