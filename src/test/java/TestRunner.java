import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;

/*
Модуль "Стикеры" 2.1.0 + модуль "Видео галерея" 3.2.1 + тема Юни2(UltRu) 4.16.1b. Лучше установить весь пакет темы Юни2.
Запускать через файл TestNG.xml
Скриншоты смотреть в папке: reports -> tests
*/

public class TestRunner {
    public static final String BASIC_URL = "https://abd-24639d9c7e.demos.abt.team/admin.php";

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