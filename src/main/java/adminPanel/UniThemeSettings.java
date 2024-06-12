package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class UniThemeSettings {
    public UniThemeSettings(){super();}

    public SelenideElement tab_ProductList = $("#product_list");
    public SelenideElement tab_Product = $("li#products");
    public SelenideElement fieldOfPictogramPosition_Grid = $("select[id='settings.abt__ut2.product_list.products_multicolumns.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_ListWithoutOptions = $("select[id='settings.abt__ut2.product_list.products_without_options.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_CompactList = $("select[id='settings.abt__ut2.product_list.short_list.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_Product = $("select[id='settings.abt__ut2.products.view.ab__s_pictogram_position.desktop']");
}