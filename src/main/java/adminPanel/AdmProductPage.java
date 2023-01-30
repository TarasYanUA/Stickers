package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AdmProductPage {
    public AdmProductPage(){super();}
    public SelenideElement productImage = $(".file-uploader__file-section.file-uploader__file-section_image");
    public SelenideElement button_DeleteProductImage = $(".file-uploader__file-button-delete");
}
