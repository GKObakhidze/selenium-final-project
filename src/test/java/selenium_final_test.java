import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.testng.Assert.assertTrue;


public class selenium_final_test {

        WebDriver driver;

        @BeforeTest
        @Parameters("browser")

        public void setup(String browser) throws Exception {

            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();

            }

            else if (browser.equalsIgnoreCase("Edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();

            } else {
                throw new Exception("Browser is not correct");
            }

        }

        @Test
        public void Test() throws InterruptedException {

            driver.get("http://tutorialsninja.com/demo/");
            driver.manage().window().maximize();

            //Go to 'My Account' and click on 'Register' button

            driver.findElement(By.xpath("//div[@id='top-links']/ul/li[2]/a[1]")).click();
            driver.findElement(By.xpath("//a[@href='http://tutorialsninja.com/demo/index.php?route=account/register']")).click();
            //Fill personal information, choose 'Subscribe' Yes and click on 'Continue' button
            driver.findElement(By.name("firstname")).sendKeys("Giga");
            driver.findElement(By.name("lastname")).sendKeys("kobakhidze");
            driver.findElement(By.name("email")).sendKeys("kobakhidzegiga@gmail.com");
            driver.findElement(By.name("telephone")).sendKeys("598282743");
            driver.findElement(By.name("password")).sendKeys("12345");
            driver.findElement(By.name("confirm")).sendKeys("12345");
            driver.findElement(By.className("radio-inline")).click();
            driver.findElement(By.xpath("//input[@type='checkbox']")).click();
            driver.findElement(By.className("btn-primary")).click();

            //  if E-Mail already exist

            boolean existemail = driver.findElement(By.cssSelector("#account-register>div.alert.alert-danger.alert-dismissible")).isDisplayed();

            if (existemail == true) {
                driver.findElement(By.cssSelector("#content>p>a")).click();
                driver.findElement(By.name("email")).sendKeys("kobakhidzegiga@gmail.com");
                driver.findElement(By.name("password")).sendKeys("12345");
                driver.findElement(By.xpath("//input[@type='submit']")).click();

            } else {
                driver.findElement(By.cssSelector("#content>div.buttons>div.pull-right>a")).click();
                System.out.println("Account Has Been Created successfully!");
            }

            //Move to 'Desktops' and select 'Show all Desktops'

            WebElement ele = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]"));
            Actions action = new Actions(driver);
            action.moveToElement(ele).perform();

            List<WebElement> elements = driver.findElements(By.xpath("//nav[@id='menu']/div[2]//div[@class='dropdown-menu']//*[2][name()=\"a\"][1][contains(text(),\"Show All Desktops\")]"));
            for (WebElement element : elements) {
                if (element.getText().equals("Show All Desktops")) {
                    element.click();
                    break;
                }
            }


            //Choose 'MP3 Players' item
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement mp3item = driver.findElement(By.xpath("//aside[@id='column-left']/div[1]//*[last()][name()=\"a\"]"));
            js.executeScript("arguments[0].click();", mp3item);


            //Move to 'iPod Classic' image and check that 'iPod Classic' text is visible on mouse hover

            Actions products = new Actions(driver);
            WebElement product_Name = driver.findElement(By.xpath("//div[@class='product-thumb']/div[1]/a/img[@alt='iPod Classic']"));
            products.moveToElement(product_Name).build().perform();
            WebElement toolTip = driver.findElement(By.xpath("//div[@class='product-thumb']/div[1]/a/img[@alt='iPod Classic']"));
            String toolTipText = toolTip.getAttribute("title");
            //System.out.println("toolTipText-->"+toolTipText);

            if (toolTipText.equalsIgnoreCase("iPod Classic")) {
                System.out.println("visible text is: " + toolTipText);
            } else {
                System.out.println("text is not visible");
            }


            //Click on 'iPod Classic' link
            driver.findElement(By.cssSelector("div.image:nth-child(1)>[href$=\"http://tutorialsninja.com/demo/index.php?route=product/product&path=34&product_id=34\"]")).click();

            //Click on first image and move on another images before text '4 of 4' is present (check Pic1)
            driver.findElement(By.xpath("//ul[@class='thumbnails']/li[1]/a[1]/img")).click();
            for (int i = 0; i < 4; i++) {
                WebElement imageSlider = driver.findElement(By.cssSelector("body"));
                imageSlider.sendKeys(Keys.ARROW_RIGHT);

            }
            WebElement imageSlideresc = driver.findElement(By.cssSelector("body"));
            imageSlideresc.sendKeys(Keys.ESCAPE);

            //Click on 'Write a review' , fill information and submit
            driver.findElement(By.xpath("//div[@class='rating']/p/a[last()]")).click();
            driver.findElement(By.name("text")).sendKeys("this products is so cool da interested, also so awesome, but price is expensive");
            driver.findElement(By.xpath("//div[@class='col-sm-12']/input[@type='radio' and @value='5']")).click();
            driver.findElement(By.className("btn-primary")).click();

            //Click on 'Add to Cart'

            driver.findElement(By.id("button-cart")).click();

            //Check by item's count and price, that product was successfully added to cart (check Pic2)
            new WebDriverWait(driver, 30).until(ExpectedConditions.textToBe(By.xpath("//*[@id='cart-total']"), "1 item(s) - $100.00"));
            List<WebElement> product = Collections.singletonList(driver.findElement(By.xpath("//*[@id='cart-total']")));
            for (int i = 0; i < product.size(); i++) {
                String elementText = product.get(i).getText();
                Assert.assertTrue(elementText.contains("1 item(s) - $100.00"));
                System.out.println("product successfully added to cart");
            }


            //Click on Pic2 element and click on 'Checkout'
            JavascriptExecutor js_1 = (JavascriptExecutor) driver;
            WebElement cartbutton = driver.findElement(By.xpath("//div[@class='row']/div[3]/div[@id='cart']/button"));
            js_1.executeScript("arguments[0].scrollIntoView();", cartbutton);

            WebDriverWait picelement = new WebDriverWait(driver, 70);
            picelement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row']/div[3]/div[@id='cart']/button")));
            driver.findElement(By.xpath("//div[@class='row']/div[3]/div[@id='cart']/button")).click();
            WebDriverWait pic2 = new WebDriverWait(driver, 70);
            pic2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='text-right']/a[2]")));
            driver.findElement(By.xpath("//p[@class='text-right']/a[2]")).click();

            //Fill Billing Details, choose Georgia and Tbilisi
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("collapse-payment-address")));
            boolean existing_address = driver.findElement(By.id("collapse-payment-address")).isDisplayed();

            if (existing_address == true) {
                driver.findElement(By.id("button-payment-address")).click();

            } else {

                WebDriverWait billing = new WebDriverWait(driver, 30);
                billing.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
                driver.findElement(By.name("firstname")).sendKeys("Giga");
                driver.findElement(By.name("lastname")).sendKeys("kobakhidze");
                driver.findElement(By.name("address_1")).sendKeys("chavchavadze Street");
                driver.findElement(By.name("city")).sendKeys("Tbilisi");
                driver.findElement(By.name("postcode")).sendKeys("123456");

                Select drpCountry = new Select(driver.findElement(By.name("country_id")));
                drpCountry.selectByVisibleText("Georgia");
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text()='Tbilisi']")));
                driver.findElement(By.xpath("//option[text()='Tbilisi']")).click();
                driver.findElement(By.id("button-payment-address")).click();
            }

            //Leave Delivery Details and Methods default
            JavascriptExecutor js_2 = (JavascriptExecutor) driver;
            js_2.executeScript("window.scrollBy(0,300)");
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("button-shipping-address")));
            driver.findElement(By.id("button-shipping-address")).click();
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("button-shipping-method")));
            driver.findElement(By.id("button-shipping-method")).click();
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.name("agree")));
            driver.findElement(By.name("agree")).click();
            driver.findElement(By.id("button-payment-method")).click();

            //In 'Confirm Order' section check that Sub-Total, Flat Shipping Rate and Total amount is correct
            //Flat_Shipping_Rate
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='collapse-checkout-confirm']/div[@class='panel-body']/div[@class='table-responsive']/table[@class='table table-bordered table-hover']/tfoot/tr[2]/td[2]")));
            String Flat_Shipping_Rate = driver.findElement(By.xpath("//div[@id='collapse-checkout-confirm']/div[@class='panel-body']/div[@class='table-responsive']/table[@class='table table-bordered table-hover']/tfoot/tr[2]/td[2]")).getText();
            String strNew = Flat_Shipping_Rate.replace("$", "");
            //Sub-Total
            String Sub_Total = driver.findElement(By.xpath("//div[@id='collapse-checkout-confirm']/div[@class='panel-body']/div[@class='table-responsive']/table[@class='table table-bordered table-hover']/tfoot/tr[1]/td[2]")).getText();
            String strNew_1 = Sub_Total.replace("$", "");
            //check total amount is correct
            float d = Float.parseFloat(strNew);
            float e = Float.parseFloat(strNew_1);
            float Total_price = d + e;
            if (d + e == Total_price) {
                System.out.println("Sub-Total, Flat Shipping Rate and Total amount is correct");
            }

            driver.findElement(By.id("button-confirm")).click();

            //Click on 'history' link and check that status is 'Pending' and date equal to current date
            new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//a[@href='http://tutorialsninja.com/demo/index.php?route=account/order']")));
            driver.findElement(By.xpath("//div[@id='content']//a[@href='http://tutorialsninja.com/demo/index.php?route=account/order']")).click();

            String Pending = driver.findElement(By.xpath("//div[@id='content']/div[@class='table-responsive']/table/tbody/tr[1]/td[4]")).getText();
            Assert.assertTrue(Pending.contains("Pending"));
            System.out.println("status is 'Pending'");
                   //data format
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = myDateObj.format(myFormatObj);
                   //data equal check
            String current_date = driver.findElement(By.xpath("//div[@id='content']/div[@class='table-responsive']/table/tbody/tr[1]/td[6]")).getText();
            Assert.assertTrue(current_date.contains(formattedDate));
            System.out.println("Date Is Equal To Current Date");


            driver.quit();



        }


    }


