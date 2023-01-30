package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CsCartSettings {
    public CsCartSettings(){super();}
    public SelenideElement menuProducts = $x("//li[@class='dropdown nav__header-main-menu-item ']//a[@href='#products']");
    public SelenideElement sectionProducts = $x("//span[text()='Товары']");
    public SelenideElement button_Save = $(".cm-product-save-buttons");

    public AdmProductPage navigateToEditingProductPage(){
        menuProducts.hover();
        sectionProducts.click();
        return new AdmProductPage();
    }
}
