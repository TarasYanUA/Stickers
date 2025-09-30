package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import storefront.StProductPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class ProductSettings extends BasicPage {

    //Меню "Товары -- Товары"
    public ProductSettings(){super();}

    SelenideElement field_productSearch = $("input[form='search_filters_form']");
    public SelenideElement statusActive_Product = $("#elm_product_status_0_a");
    public SelenideElement field_ListPrice = $("#elm_list_price");
    public SelenideElement productTemplate = $("#elm_details_layout");
    public SelenideElement tab_General = $("#detailed");
    SelenideElement tab_Shippings = $("#shippings");
    SelenideElement field_ProductWeight = $("#product_weight");


    public void setValueTo_ProductWeight(String value){
        tab_Shippings.hover().click();
        field_ProductWeight.setValue(value);
    }

    public void selectProductByName(String productName){
        field_productSearch.setValue(productName);
        $x(String.format("//td[@class='product-name-column wrap-word']//a[contains(text(), '%s')]", productName))
                .shouldBe(Condition.visible, Duration.ofSeconds(8))
                .click();
    }

    public StProductPage navigateTo_StProductPage(int tabNumber) {
        openPreviewPage(tabNumber);
        return new StProductPage();
    }
}