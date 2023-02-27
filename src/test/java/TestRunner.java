import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;

/*
Модуль "Стикеры" 2.1.0 + модуль "Видео галерея" 3.2.1 + тема Юни2(UltRu) 4.16.1a. Лучше установить весь пакет темы Юни2.
Запускать через файл testng.xml
*/

/*//Это удалить после разработки
        CsCartSettings csCartSettings = new CsCartSettings();
                csCartSettings.navigateToEditingCategoryPage();
                $x("//a[text()='AB: Телефоны']").click();
                csCartSettings.gearWheelOnTop.click();
                csCartSettings.button_ViewProducts.click();
                $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
                csCartSettings.navigateToStProductPage(1);
                csCartSettings.cookieNotice();*/

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4161ultru/admin.php";

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = true; //не закрываем браузер пока ведём разработку
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
}