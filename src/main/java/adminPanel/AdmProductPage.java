package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AdmProductPage {
    public AdmProductPage(){super();}

    public SelenideElement statusActive_Product = $("#elm_product_status_0_a");
    public SelenideElement field_ListPrice = $("#elm_list_price");
}
