import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Init {
    static WebDriver driver;
    static Properties property = new Properties();

    static WebDriver initWebDriver() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            property.load(fileInputStream);

            if (property.getProperty("main.driver").equals("CHROME")) {
                driver = new ChromeDriver();
            } else if (property.getProperty("main.driver").equals("FIREFOX")) {
                driver = new FirefoxDriver();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return driver;
    }
}