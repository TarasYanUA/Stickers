package adminPanel;

import com.codeborne.selenide.SelenideElement;
import storefront.StCategoryPage;

import static adminPanel.CsCartSettings.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CategorySettings {

    //Меню "Товары -- Категории"
    public CategorySettings() {super();}

    SelenideElement statusActive_Category = $("#elm_category_status_0_a");
    public SelenideElement button_ViewProducts = $("a[href*='products.manage&cid']");


    public StCategoryPage navigateTo_StCategoryPage(int tabNumber) {
        closeNotificationIfExists();
        openPreviewPage(tabNumber);
        return new StCategoryPage();
    }

    public void openCategoryPage(String categoryName) {
        $x(String.format("//a[contains(text(), '%s']", categoryName)).click();
    }

    public void activateCategoryAndSave() {
        statusActive_Category.click();
        saveSettings();
    }

    public void viewCategoryProducts() {
        gearWheelOnTop.click();
        button_ViewProducts.click();
    }
}
