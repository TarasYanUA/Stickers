package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Selenide.$;

public class UniThemeSettings {
    public UniThemeSettings(){super();}

    public SelenideElement tab_ProductList = $("#product_list");
    public SelenideElement tab_Product = $("#products");
    public SelenideElement fieldOfPictogramPosition_Grid = $("select[id='settings.abt__ut2.product_list.products_multicolumns.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_ListWithoutOptions = $("select[id='settings.abt__ut2.product_list.products_without_options.ab__s_pictogram_position.desktop'");
    public SelenideElement fieldOfPictogramPosition_CompactList = $("select[id='settings.abt__ut2.product_list.short_list.ab__s_pictogram_position.desktop'");

    public Select getFieldOfPictogramPosition_Grid(){return new Select(fieldOfPictogramPosition_Grid);}
    public void selectPictogramPosition_Grid(String value){
        getFieldOfPictogramPosition_Grid().selectByValue(value);
    }
    public Select getFieldOfPictogramPosition_ListWithoutOptions(){return new Select(fieldOfPictogramPosition_ListWithoutOptions);}
    public void selectPictogramPosition_ListWithoutOptions(String value){
        getFieldOfPictogramPosition_ListWithoutOptions().selectByValue(value);
    }
    public Select getFieldOfPictogramPosition_CompactList(){return new Select(fieldOfPictogramPosition_CompactList);}
    public void selectPictogramPosition_CompactList(String value){
        getFieldOfPictogramPosition_CompactList().selectByValue(value);
    }
}