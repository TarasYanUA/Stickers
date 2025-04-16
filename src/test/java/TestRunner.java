import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

public class TestRunner {
    public static final String BASIC_URL = "https://abd-b224bf2dfc.demos.abt.team/admin.php?dispatch=addons.manage";
    private SoftAssert softAssert;

    @BeforeMethod
    public void openBrowser() {
        Configuration.browser = "chrome";
        open(BASIC_URL);
        Configuration.screenshots = true; //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран

        softAssert = new SoftAssert();
        CollectAssertMessages.setSoftAssertions(softAssert);

        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }

    @AfterMethod
    public void closeBrowser() {
        softAssert = CollectAssertMessages.getSoftAssertions();
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("\nОшибки в asserts:");
            System.out.println(e.getMessage());
        }

        sleep(2000);
        Selenide.closeWebDriver();
    }

    public void shiftLanguage(String arRu) {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='" + arRu + "']").click();
    }

    public void takeScreenshot(String screenName) {
        sleep(3000);
        screenshot(screenName);
    }

    public void waitForSpinnerDisappear() {
        $("div#ajax_loading_box").shouldBe(Condition.disappear, Duration.ofSeconds(10));
        Selenide.sleep(1000);
    }
}