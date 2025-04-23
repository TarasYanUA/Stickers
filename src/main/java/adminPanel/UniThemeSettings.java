package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class UniThemeSettings {
    public UniThemeSettings() {
        super();
    }

    public SelenideElement tab_ProductList = $("#product_list");
    public SelenideElement tab_Product = $("li#products");
    public SelenideElement fieldOfPictogramPosition_Grid = $("select[id='settings.abt__ut2.product_list.products_multicolumns.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_ListWithoutOptions = $("select[id='settings.abt__ut2.product_list.products_without_options.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_CompactList = $("select[id='settings.abt__ut2.product_list.short_list.ab__s_pictogram_position.desktop']");
    public SelenideElement fieldOfPictogramPosition_Product = $("select[id='settings.abt__ut2.products.view.ab__s_pictogram_position.desktop']");
    public SelenideElement setting_CombinationsOfProductGalleryImageFormations = $(By.id("settings.abt__ut2.products.abt__ut2_cascade_gallery_template.formation_multiple_product_images.desktop"));
    public SelenideElement setting_PriceDisplayFormat = $(By.id("settings.abt__ut2.product_list.price_display_format"));
}