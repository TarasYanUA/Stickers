package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.Select;
import static com.codeborne.selenide.Selenide.$;

public class ProductSettings {
    public ProductSettings(){super();}

    public SelenideElement statusActive_Product = $("#elm_product_status_0_a");
    public SelenideElement field_ListPrice = $("#elm_list_price");
    public SelenideElement productName = $(".product-name-column a");
    public SelenideElement productTemplate = $("#elm_details_layout");
    public SelenideElement tab_General = $("#detailed");
    public SelenideElement tab_Shippings = $("#shippings");
    public  SelenideElement field_ProductWeight = $("#product_weight");


    public Select getProductTemplate(){return new Select(productTemplate);}
    public void selectProductTemplate(String value){
        getProductTemplate().selectByValue(value);
    }
    public void clickAndType_ProductWeight(String value){
        field_ProductWeight.click();
        field_ProductWeight.clear();
        field_ProductWeight.sendKeys(value);
    }
}
