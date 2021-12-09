import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        driver = Init.initWebDriver();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void correctLoginData() {
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@name='user']")).click();
        driver.findElement(By.xpath("//input[@name='user']")).sendKeys("fominaelena");
        driver.findElement(By.xpath("//input[@name='password']")).click();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1P73BP4Z");
        driver.findElement(By.xpath("//button[@id='button_submit_login_form']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/div/div/div/div/div[2]")));
        assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div/div/div[2]")).getText(), "Фомина Елена Сергеевна");
    }

    @Test
    void incorrectLoginData() {
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@name='user']")).click();
        driver.findElement(By.xpath("//input[@name='user']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='password']")).click();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@id='button_submit_login_form']")).click();
        Alert alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "Неверные данные для авторизации");
        alert.accept();
        assertEquals(driver.findElement(By.xpath("//div[2]/div/div")).getText(), "Вход в систему");
    }

    @Test
    void passwordIsVisible() {
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@name='password']")).click();
        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("admin");
        assertEquals(password.getAttribute("type"), "password");
        driver.findElement(By.xpath("//button[@id='show_password']")).click();
        assertEquals(password.getAttribute("type"), "text");
    }

    @Test
    void correctPasswordRecovery() {
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//form[@id='login_form_panel']/table[2]/tbody/tr/td/a/div")).click();
        driver.findElement(By.xpath("//input[@name='loginOrEmail']")).click();
        driver.findElement(By.xpath("//input[@name='loginOrEmail']")).sendKeys("fominaelena");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals(driver.findElement(By.xpath("//div[2]/div[2]")).getText(), "На ваш электронный адрес отправлена инструкция по восстановлению пароля.");
        driver.findElement(By.xpath("//a/div")).click();
        assertEquals(driver.findElement(By.xpath("//div[2]/div/div")).getText(), "Вход в систему");
    }

    @Test
    void incorrectPasswordRecovery() {
        driver.get("https://lmslite47vr.demo.mirapolis.ru/mira/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//form[@id='login_form_panel']/table[2]/tbody/tr/td/a/div")).click();
        driver.findElement(By.xpath("//input[@name='loginOrEmail']")).click();
        driver.findElement(By.xpath("//input[@name='loginOrEmail']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals(driver.findElement(By.xpath("//div[2]/div[2]")).getText(), "Пользователь с таким именем не найден.");
        driver.findElement(By.xpath("//a/div")).click();
        assertEquals(driver.findElement(By.xpath("//div[2]/div/div")).getText(), "Вход в систему");
    }
}
