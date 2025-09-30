package adminPanel;

import com.codeborne.selenide.SelenideElement;
import storefront.StCategoryPage;

import static com.codeborne.selenide.Selenide.*;

public class CategorySettings extends BasicPage {

    //Меню "Товары -- Категории"
    public CategorySettings() {super();}

    SelenideElement statusActive_Category = $("#elm_category_status_0_a");
    SelenideElement button_ViewProducts = $("a[href*='products.manage&cid']");


    public StCategoryPage navigateTo_StCategoryPage(int tabNumber) {
        openPreviewPage(tabNumber);
        return new StCategoryPage();
    }

    public void openCategoryPage(String categoryName) {
        $x(String.format("//a[contains(text(), '%s')]", categoryName)).click();
    }

    public void activateCategoryAndSave() {
        statusActive_Category.click();
        Utils.saveSettings();
    }

    public void viewCategoryProducts() {
        sleep(2000);
        gearWheelOnTop.click();
        button_ViewProducts.click();
    }
}
