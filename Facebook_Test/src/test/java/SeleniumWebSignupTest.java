import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumWebSignupTest {

    //set up your driver first, by defining it
    WebDriver driver;

    @BeforeTest
    public void Setup() throws InterruptedException {
        //set up the system to locate your web driver
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();  //add this option to allow origins issues in browser
        options.addArguments("--remote-allow-origins=*");

        //first initialize your web driver and open your Chrome browser
        driver = new ChromeDriver(options);

        //input the url of the page you want to test so the driver can open the page
        //at https://selenium-blog.herokuapp.com
        driver.get("https://selenium-blog.herokuapp.com");

        //Test: Verify that the user is on the right url page
        //get the current url which is the actual and compare it with the expected,
        // if they match, then the test passed, else it fails

        String expectedUrl = "https://selenium-blog.herokuapp.com";  //actual is gotten from driver.getCurrent()

        //Test 1: verify user is on correct url
        String currentUrl = driver.getCurrentUrl();
        if(currentUrl.contains(expectedUrl)){
            System.out.println("Verify Url Passed!");
        }else{
            System.out.println("Verify Url Failed!");
        }

        //wait globally for the driver to open and load the webpage
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  //tells the driver to wait
        // for 10 seconds in order to complete the loading of the activities

        Thread.sleep(2000);  //causes the UI to sleep for 10 milliseconds for the loading activity to complete
        //nothing wil happen within this time as the UI will ignore all inputs and commands given to it within this time duration

        //Next, driver should maximize the browser
        driver.manage().window().maximize();
        Thread.sleep(3000);

    }


    @Test()
    public void PositiveSignup() throws InterruptedException {
        //After maximizing, click on the signup button to sign up
        //but first,you need to locate and identify the signup button before you can click on it
        //hence, you will use findBy method to get the specific item you are looking for. There are 3 ways to find it
        //first way is findElementById, second is findElementByName and third is findElementByxPath
        // which is what we will use to find the signup button
        //driver.findElement(By.xpath("/html/body/div[2]/div/a[2]"));
        //since we have seen the element, next click on the button
        driver.findElement(By.xpath("/html/body/div[2]/div/a[2]")).click();

        //The signup page will open and once it opens, find the various items and fill them up
        //find the input box by the id since it has id. This is user_username and use this
        String username = "Bantale5";
        driver.findElement(By.id("user_username")).sendKeys(username);

        //next, find email address input box and supply your email. it also has an id called user_email
        driver.findElement(By.id("user_email")).sendKeys("Bantele5@mailinator.com");

        //next, find the password box and fill in the password. it also has an id called user_password
        driver.findElement(By.id("user_password")).sendKeys("Test1234");

        //Lastly, find the signup button either by id if it has, or by name, if it has or by xpath. it has id called submit
        driver.findElement(By.id("submit")).click();

        //Test 4: Verify that user can sign up successfully
        String expectedSuccessMessage = "Welcome to the alpha blog "+ username;
        String actualMsg = driver.findElement(By.id("flash_success")).getText();
        if(expectedSuccessMessage.contains(actualMsg)){
            System.out.println("User SignUp Successful!");
        }else{
            System.out.println("User SignUp Failed!!!");
        }

        Thread.sleep(3000); //waits for 10 seconds to finish the signup process
    }


    @Test(priority = 1)
    public void ClickItem1() throws InterruptedException {
        //next find the item we are looking for and click on it. it has xpath with /html/body/div[2]/div[1]/ul/div/div/li[1]
        driver.findElement(By.xpath("/html/body/div[2]/div[1]/ul/div/div/li[1]")).click();

        //Test 3: Verify that when user clicks on signup button, he is navigated to the signup page
        String expectedSignupUrl = "https://selenium-blog.herokuapp.com/users/1";
        String actualSignupUrl = driver.getCurrentUrl();

        if(expectedSignupUrl.contains(actualSignupUrl)){
            System.out.println("User is navigated to https://selenium-blog.herokuapp.com/users/1 on item click");
        }else{
            System.out.println("User is not navigated to https://selenium-blog.herokuapp.com/users/1, failed!!!!");
        }
        Thread.sleep(3000);

    }


    @Test(priority = 2)
        public void ClickAndVerifyItemExists() throws InterruptedException {
        //Next, we are told to find the text "Using Python with Selenium" and confirm if it exists after we clicked on the above
        //we will use driver to locate the text using xpath. The xPath is /html/body/div[2]/div/div/div/div[1]/a
        //to confirm if it exists, we will need to note the text and compare it with the actual text we got. it if its equal, then
        //it passes, but if the text are not equal, then it fails

        //Test 5: Verify that when user clicks on item, it exists
        String expectedMessage = "Using Python with Selenium";

        String actualMessage = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/a")).getText();

        if(actualMessage.contains(expectedMessage)){
            System.out.println("Test Passed!");
        }else{
            System.out.println("Test Failed!!");
        }

        //After confirming the above, then logout. //find the logout button and click on it
        driver.findElement(By.xpath("//*[@id=\"bs-example-navbar-collapse-1\"]/ul/li[3]/a")).click();
        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void VerifyPageTitle() throws InterruptedException {
        //Test 6: Verify Page Title
        String expectedTitle = "AlphaBlog";
        String actualTitle = driver.getTitle();

        if(expectedTitle.contains(actualTitle)){
            System.out.println("Verify Page Title Passed!");
        }else{
            System.out.println("Verify Page Title Failed!");
        }
        Thread.sleep(3000);

    }

    @Test(priority = 4) //Test 3: Cannot sign up with username less than 3 characters
    public void NegativeSignupInvalidUsername() throws InterruptedException {

        driver.findElement(By.xpath("/html/body/div[2]/div/a[2]")).click();  //click on the signup button

        //The signup page will open and once it opens, find the various items and fill them up
        //find the input box by the id since it has id. This is user_username and use this

        driver.findElement(By.id("user_username")).sendKeys("fa");

        //next, find email address input box and supply your email. it also has an id called user_email
        driver.findElement(By.id("user_email")).sendKeys("Fa@mailinator.com");

        //next, find the password box and fill in the password. it also has an id called user_password
        driver.findElement(By.id("user_password")).sendKeys("Test1234");

        //Lastly, find the signup button either by id if it has, or by name, if it has or by xpath. it has id called submit
        driver.findElement(By.id("submit")).click();


        String expectedSuccessMessage = "Username is too short (minimum is 3 characters)";
        String actualMsg = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/ul/li")).getText();
        if(expectedSuccessMessage.contains(actualMsg)){
            System.out.println("User cannot signup with less than 3 characters Successful!");
        }else{
            System.out.println("User cannot signup with less than 3 characters failed!");
        }

        Thread.sleep(3000); //waits for 30 seconds to finish the signup process
    }


    @Test(priority = 5) //Test 4: invalid email signup
    public void NegativeSignupInvalidEMail() throws InterruptedException {
        //The signup page is already open, just supply the details
        //find the input box by the id since it has id. This is user_username and use this

        driver.findElement(By.id("user_username")).sendKeys("Tosin");

        //next, find email address input box and supply your email. it also has an id called user_email
        driver.findElement(By.id("user_email")).sendKeys("Tosin@com");

        //next, find the password box and fill in the password. it also has an id called user_password
        driver.findElement(By.id("user_password")).sendKeys("Test1234");

        //Lastly, find the signup button either by id if it has, or by name, if it has or by xpath. it has id called submit
        driver.findElement(By.id("submit")).click();


        String expectedSuccessMessage = "Email is invalid";
        String actualMsg = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/ul/li")).getText();
        if(expectedSuccessMessage.contains(actualMsg)){
            System.out.println("Invalid Email test Successful!");
        }else{
            System.out.println("Invalid Email test failed!");
        }

        Thread.sleep(3000); //waits for 30 seconds to finish the signup process
    }


    @Test(priority = 6) //Test 5: login with password equal to or less than 1 character
    public void NegativeLoginInvalidPassword() throws InterruptedException {

        //click on the login button in the navigation bar to navigate you to the login page
        driver.findElement(By.xpath("//*[@id=\"bs-example-navbar-collapse-1\"]/ul/li[1]/a")).click();

        //next, find email address input box and supply your email. it also has an id called session_email
        driver.findElement(By.id("session_email")).sendKeys("ajayiadewalejoshua@gmail.com");

        //next, find the password box and fill in the password. it also has an id called session_password
        driver.findElement(By.id("session_password")).sendKeys("T");

        //Lastly, find the Login button either by id if it has, or by name, if it has or by xpath. it has id called submit
        driver.findElement(By.name("commit")).click();

        String expectedSuccessMessage = "There was a problem logging in";
        String actualMsg = driver.findElement(By.xpath("//*[@id=\"flash_danger\"]")).getText();
        if(expectedSuccessMessage.contains(actualMsg)){
            System.out.println("Login with password less than 1 character Test passed!!");
        }else{
            System.out.println("\"Login with password less than 1 character Test passed!!");
        }

        Thread.sleep(3000); //waits for 30 seconds to finish the signup process
    }

    @Test(priority = 7) //Test 6: cannot sign up with either or all blank fields
    public void NegativeSignupWithEmptyFields() throws InterruptedException {
        //The login page is already open, you need to navigate to the signup page to test the blank signup fileds

        //find the signup button by the xpath.
        driver.findElement(By.xpath("//*[@id=\"bs-example-navbar-collapse-1\"]/ul/li[2]/a")).click();

        driver.findElement(By.id("user_username")).sendKeys("");  //username box, leave blank

        //next, find email address input box and supply your email. it also has an id called user_email
        driver.findElement(By.id("user_email")).sendKeys("Tosin@com");  //emailBox

        //next, find the password box and fill in the password. it also has an id called user_password
        driver.findElement(By.id("user_password")).sendKeys("Test1234");  //password box

        //Lastly, find the signup button either by id if it has, or by name, if it has or by xpath. it has id called submit
        driver.findElement(By.id("submit")).click();

        String expectedMessage1 = "Username can't be blank";
        String expectedMessage2 = "Username is too short (minimum is 3 characters)";
        String actualMsg1 = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/ul/li[1]")).getText();
        String actualMsg2 = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/ul/li[2]")).getText();

        if( expectedMessage1.contains(actualMsg1) && expectedMessage2.contains(actualMsg2) ){
            System.out.println("Cannot signup with blank fields test Successful!!");
        }else{
            System.out.println("Cannot signup with blank fields test failed!!");
        }

        Thread.sleep(3000); //waits for 30 seconds to finish the signup process
    }


    @Test(priority = 8) //Test 10: User redirected to login page when logout button is pressed
    public void UserRedirectedToLoginOnLogout(){
        //user has to be logged in for this to be tested. at this point, the user is logged out, so log in first,click the logout button and
        //verify user is back in home page

        //click on the login button in the navigation bar to navigate you to the login page
        driver.findElement(By.xpath("//*[@id=\"bs-example-navbar-collapse-1\"]/ul/li[1]/a")).click();

        //next, find email address input box and supply your email. it also has an id called session_email
        driver.findElement(By.id("session_email")).sendKeys("ajayiadewalejoshua@gmail.com");

        //next, find the password box and fill in the password. it also has an id called session_password
        driver.findElement(By.id("session_password")).sendKeys("Test123");

        //Lastly, find the Login button either by id if it has, or by name, if it has or by xpath. it has id called submit
        driver.findElement(By.name("commit")).click();

        //within login page, locate the logout button and click on it to sign out
        driver.findElement(By.xpath("//*[@id=\"bs-example-navbar-collapse-1\"]/ul/li[3]/a")).click();

        //in the screen where you are logged out to, check and confirm the message you got there
        String expectedMessage = "You have logged out";
        String actualMessage = driver.findElement(By.id("flash_success")).getText();
        //compare the two
        if(expectedMessage.contains(actualMessage)){
            System.out.println("User redirected to login page when logout button is pressed test Successful!!");
        }
        else{
            System.out.println("User redirected to login page when logout button is pressed test Failed!!");
        }

    }



    @AfterTest
    private void closeBrowser(){
        //after the workflow, always quit the browser
        driver.quit();
    }
}
