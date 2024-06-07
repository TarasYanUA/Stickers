import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;

public class TestRunner {
    public static final String BASIC_URL = "https://abd-c625afeb8e.demos.abt.team/admin.php?dispatch=settings.manage&section_id=Appearance";

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        open(BASIC_URL);
        Configuration.holdBrowserOpen = true; //не закрываем браузер пока ведём разработку
        Configuration.screenshots = true; //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран
        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }

    @AfterClass
    public void closeBrowser() {
        Selenide.closeWebDriver();
    }

    public void selectLanguage_RTL() {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='ar']").click();
    }
    public void selectLanguage_RU() {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='ru']").click();
    }
    public void makePause(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}