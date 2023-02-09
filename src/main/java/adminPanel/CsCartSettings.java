package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.Select;
import storefront.StProductPage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CsCartSettings {
    public CsCartSettings(){super();}
    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement popupWindow = $(".ui-dialog-title");

    public SelenideElement menuProducts = $x("//li[@class='dropdown nav__header-main-menu-item ']//a[@href='#products']");
    public SelenideElement sectionProducts = $x("//span[text()='Товары']");
    public SelenideElement sectionCategories = $("a[href$='categories.manage']");
    public SelenideElement menuSettings = $(".dropdown-toggle.settings");
    public SelenideElement sectionAppearance = $("#elm_menu_settings_Appearance");
    public SelenideElement settingMiniThumbnailAsGallery = $("#field___thumbnails_gallery_147");
    public SelenideElement settingQuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");
    public SelenideElement menuDesign = $("#elm_menu_design");
    public SelenideElement sectionLayouts = $("#elm_menu_design_layouts");
    public SelenideElement layout_TabProducts = $x("//a[contains(@href, 'selected_location')][text()='Товары']");
    public SelenideElement layout_GearwheelOfBlockPopular = $("#snapping_714 div.bm-action-properties");
    public SelenideElement layout_GearwheelOfBlockHits = $("#snapping_715 div.bm-action-properties");
    public SelenideElement layout_BlockTemplate = $("select[id*='products_template']");
    public SelenideElement layout_ButtonSaveBlock = $("input[name='dispatch[block_manager.update_block]']");
    public SelenideElement layoutBlock_TabContent = $("li[id*='block_contents'] a");
    public SelenideElement layout_FieldFilling = $("select[id*='content_items_filling']");
    public SelenideElement layout_FieldMaxLimit = $("input[id*='content_items_properties_items_limit']");

    public SelenideElement menuAddons = $("#elm_menu_addons");
    public SelenideElement sectionDownloadedAddons = $("#elm_menu_addons_downloaded_add_ons");
    public SelenideElement menuOfUniTheme = $("tr#addon_abt__unitheme2 button.btn.dropdown-toggle");
    public SelenideElement sectionThemeSettings = $("div.nowrap a[href*='abt__ut2.settings']");
    public SelenideElement menuOfStickerAddon = $("tr#addon_ab__stickers button.btn.dropdown-toggle");
    public SelenideElement sectionStickerSettings = $("div.nowrap a[href*='addon=ab__stickers']");
    public SelenideElement sectionStickerList = $("tr#addon_ab__stickers a[href*='ab__stickers.manage']");
    public SelenideElement menuOFVideoGalleryAddon = $("tr#addon_ab__video_gallery button.btn.dropdown-toggle");
    public SelenideElement sectionVideoGalleryGeneralSettings = $("tr#addon_ab__video_gallery a[href*='selected_section=settings']");
    public SelenideElement statusActive_Category = $("#elm_category_status_0_a");
    public SelenideElement gearWheelOnTop = $(".dropdown-icon--tools");
    public SelenideElement button_Preview = $x("//a[contains(text(), 'Предпросмотр')]");
    public SelenideElement button_ViewProducts = $("a[href*='products.manage&cid']");


    public ProductSettings navigateToEditingProductPage(){
        menuProducts.hover();
        sectionProducts.click();
        return new ProductSettings();
    }
    public void navigateToEditingCategoryPage(){
        menuProducts.hover();
        sectionCategories.click();
    }
    public void navigateToAddonsPage(){
        menuAddons.hover();
        sectionDownloadedAddons.click();
    }
    public UniThemeSettings navigateToUniThemeSettings(){
        menuOfUniTheme.click();
        sectionThemeSettings.click();
        return new UniThemeSettings();
    }
    public StickerSettings navigateToStickerSettingsPage(){
        menuOfStickerAddon.click();
        sectionStickerSettings.click();
        return new StickerSettings();
    }
    public StickerSettings navigateToStickerListPage(){
        menuOfStickerAddon.click();
        sectionStickerList.click();
        return new StickerSettings();
    }
    public VideoGallerySettings navigateToVideoGalleryPage(){
        menuOFVideoGalleryAddon.click();
        sectionVideoGalleryGeneralSettings.click();
        return new VideoGallerySettings();
    }
    public StProductPage navigateToStProductPage(int tabNumber){
        button_Save.click();
        Selenide.sleep(2000);
        gearWheelOnTop.click();
        button_Preview.click();
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
        $(".cookie-notice").shouldBe(Condition.interactable);
        $(".cm-btn-success").click();
        return new StProductPage();
    }

    public Select getLayout_BlockTemplate(){return new Select(layout_BlockTemplate);}
    public void selectBlockTemplate(String value){
        getLayout_BlockTemplate().selectByValue(value);
    }
    public Select getLayout_FieldFilling(){return new Select(layout_FieldFilling);}
    public void selectLayout_FieldFilling(){
        getLayout_FieldFilling().selectByValue("newest");}
    public void clickAndType_Layout_FieldMaxLimit(){
        layout_FieldMaxLimit.click();
        layout_FieldMaxLimit.clear();
        layout_FieldMaxLimit.setValue("4");
    }
}