package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Utils {
    // Утилитарные методы - это методы, которые предоставляют функциональность для выполнения общих, повторяющихся или вспомогательных задач в различных частях программы.

    public static void saveSettings() {
        $(".btn.btn-primary.cm-submit").click();
        sleep(1500);
    }

    public static void setCheckbox(SelenideElement checkbox, boolean shouldBeChecked, String name) {
        if (checkbox.isSelected() != shouldBeChecked) {
            checkbox.scrollIntoCenter().click();
            System.out.printf("Настройка '%s' установлена на: %s%n", name, shouldBeChecked ? "включена" : "отключена");
        }
    }

    public static void waitForPopupWindow(boolean shouldAppear) {
        SelenideElement popupWindow = $(".ui-dialog-title");
        if (shouldAppear) {
            popupWindow.shouldBe(Condition.visible);
        } else {
            popupWindow.shouldBe(Condition.disappear);
        }
        Selenide.sleep(1000);
    }

    public static void shiftBrowserTab(int tabNumber) {
        getWebDriver().getWindowHandle();
        switchTo().window(tabNumber);
    }

    public static void waitForSpinnerDisappear() {
        $("div#ajax_loading_box[style=\"display: block;\"]").shouldBe(Condition.disappear, Duration.ofSeconds(10));
        sleep(1000);
    }
}
