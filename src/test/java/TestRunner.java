import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;

/*
Модуль "Стикеры" 2.1.0 + модуль "Видео галерея" 3.2.1 + тема Юни2(UltRu) 4.16.1a. Лучше установить весь пакет темы Юни2.
Запускать через файл testng.xml
*/

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4161ultru/admin.php";

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = false; //не закрываем браузер пока ведём разработку
        Configuration.screenshots = true; //делаем скриншоты при падении
        Configuration.browserSize = "1920x1050"; //Увеличиваем размер экрана
        open(BASIC_URL);
        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }

    @AfterClass
    public void closeBrowser() {
        Selenide.closeWebDriver();
    }

    public void shiftLanguage(int index) {
        $("a[id*='_wrap_language_']").hover().click();
        $$("div[id*='_wrap_language_'] li.ty-select-block__list-item").get(index).click();
    }
    public void selectBigPictureFlatTemplate() {
        $("#elm_details_layout").click();
        $x("//option[@value='abt__ut2_bigpicture_flat_template']").click();
    }
    public void selectBigPictureTemplate() {
        $("#elm_details_layout").click();
        $x("//option[@value='bigpicture_template']").click();
    }
    public void selectDefaultTemplate() {
        $("#elm_details_layout").click();
        $x("//option[@value='default_template']").click();
    }
    public void selectThreeColumnTemplate() {
        $("#elm_details_layout").click();
        $x("//option[@value='abt__ut2_three_columns_template']").click();
    }
}