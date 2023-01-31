package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CsCartSettings {
    public CsCartSettings(){super();}
    public SelenideElement button_Save = $(".cm-product-save-buttons");
    public SelenideElement menuProducts = $x("//li[@class='dropdown nav__header-main-menu-item ']//a[@href='#products']");
    public SelenideElement sectionProducts = $x("//span[text()='Товары']");
    public SelenideElement menuSettings = $(".dropdown-toggle.settings");
    public SelenideElement sectionAppearance = $("#elm_menu_settings_Appearance");
    public SelenideElement settingMiniThumbnailAsGallery = $("#field___thumbnails_gallery_147");
    public SelenideElement settingQuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");
    public SelenideElement menuAddons = $("#elm_menu_addons");
    public SelenideElement sectionManageAddons = $("#elm_menu_addons_manage_addons");
    public SelenideElement menuOfStickerAddon = $("tr#addon_ab__stickers button.btn.dropdown-toggle");
    public SelenideElement sectionStickerList = $("tr#addon_ab__stickers a[href*='ab__stickers.manage']");
    public SelenideElement menuOFVideoGalleryAddon = $("tr#addon_ab__video_gallery button.btn.dropdown-toggle");
    public SelenideElement sectionVideoGalleryGeneralSettings = $("tr#addon_ab__video_gallery a[href*='selected_section=settings']");


    public AdmProductPage navigateToEditingProductPage(){
        menuProducts.hover();
        sectionProducts.click();
        return new AdmProductPage();
    }
    public void navigateToAddonsPage(){
        menuAddons.hover();
        sectionManageAddons.click();
    }
    public StickerPage navigateToStickerListPage(){
        menuOfStickerAddon.click();
        sectionStickerList.click();
        return new StickerPage();
    }
    public VideoGalleryPage navigateToVideoGalleryPage(){
        menuOFVideoGalleryAddon.click();
        sectionVideoGalleryGeneralSettings.click();
        return new VideoGalleryPage();
    }
}
