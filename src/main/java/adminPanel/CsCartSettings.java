package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CsCartSettings implements CheckMenuToBeActive{
    public CsCartSettings(){super();}
    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement popupWindow = $(".ui-dialog-title");

    public void cookieNotice(){
        $(".cookie-notice").shouldBe(Condition.interactable);
        $(".cm-btn-success").click();
    }

    public void shiftBrowserTab(int tabNumber){
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }

    //Меню "Веб-сайт -- Темы -- Макеты"
    private SelenideElement menu_Website = $("a[href$='dispatch=themes.manage'].main-menu-1__link");
    private SelenideElement section_Themes = $("#website_themes");
    private SelenideElement sectionLayouts = $(".nav__actions-bar a[href$='block_manager.manage']");
    public SelenideElement layout_TabProducts = $x("//a[contains(@href, 'selected_location')][text()='Товары']");
    public SelenideElement layout_GearwheelOfBlockPopular = $x("//div[@title=\"Самые популярные\"]/..//div[contains(@class, 'bm-action-properties')]");
    public SelenideElement layout_GearwheelOfBlockHits = $x("//div[@title=\"Распродажа\"]/..//div[contains(@class, 'bm-action-properties')]");
    public SelenideElement layout_BlockTemplate = $("select[id*='products_template']");
    public SelenideElement layout_ButtonSaveBlock = $("input[name='dispatch[block_manager.update_block]']");
    public SelenideElement layoutBlock_TabContent = $("li[id*='block_contents'] a");
    public SelenideElement layout_FieldFilling = $("select[id*='content_items_filling']");
    public SelenideElement layout_FieldMaxLimit = $("input[id*='content_items_properties_items_limit']");

    public void navigateToSectionLayouts() {
        checkMenuToBeActive("dispatch=themes.manage", menu_Website);
        section_Themes.click();
        sectionLayouts.click();
    }
    public void clickAndType_Layout_FieldMaxLimit(){
        layout_FieldMaxLimit.click();
        layout_FieldMaxLimit.clear();
        layout_FieldMaxLimit.setValue("4");
    }

    //Меню "Товары --Товары"
    private SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    private SelenideElement section_Products = $(By.id("products_products"));

    public ProductSettings navigateToSection_Products(){
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Products.click();
        return new ProductSettings();
    }

    //Меню "Товары -- Категории"
    private SelenideElement section_Categories = $(By.id("products_categories"));
    public SelenideElement statusActive_Category = $("#elm_category_status_0_a");
    public SelenideElement gearWheelOnTop = $(".dropdown-icon--tools");
    public SelenideElement button_Preview = $x("//a[contains(text(), 'Предпросмотр')]");
    public SelenideElement button_ViewProducts = $("a[href*='products.manage&cid']");

    public void navigateToSection_Categories(){
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Categories.click();
    }

    //Меню "Настройки -- Общие настройки"
    private SelenideElement menu_Settings = $("#administration");
    private SelenideElement section_GeneralSettings = $("a[href$='section_id=General']");
    private SelenideElement section_Appearance = $("a[href*='section_id=Appearance']");
    public SelenideElement settingMiniThumbnailAsGallery = $("#field___thumbnails_gallery_147");
    public SelenideElement settingQuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");

    public void navigateTo_AppearanceSettings() {
        menu_Settings.click();
        section_GeneralSettings.click();
        section_Appearance.click();
    }

    //Меню "Модули -- Скачанные модули"
    public SelenideElement menu_Addons = $("a[href$='dispatch=addons.manage'].main-menu-1__link");
    public SelenideElement section_DownloadedAddons = $("#addons_downloaded_add_ons");
    public SelenideElement menuOfUniTheme = $("tr#addon_abt__unitheme2 button.btn.dropdown-toggle");
    public SelenideElement sectionThemeSettings = $("div.nowrap a[href*='abt__ut2.settings']");
    public SelenideElement menuOfStickerAddon = $("tr#addon_ab__stickers button.btn.dropdown-toggle");
    public SelenideElement sectionStickerSettings = $("div.nowrap a[href*='addon=ab__stickers']");
    public SelenideElement sectionStickerList = $("tr#addon_ab__stickers a[href*='ab__stickers.manage']");
    public SelenideElement menuOFVideoGalleryAddon = $("tr#addon_ab__video_gallery button.btn.dropdown-toggle");
    public SelenideElement sectionVideoGalleryGeneralSettings = $("tr#addon_ab__video_gallery a[href*='selected_section=settings']");

    private void navigateTo_DownloadedAddonsPage() {
        checkMenuToBeActive("dispatch=addons.manage", menu_Addons);
        section_DownloadedAddons.click();
    }
    public UniThemeSettings navigateToUniThemeSettings(){
        navigateTo_DownloadedAddonsPage();
        menuOfUniTheme.click();
        sectionThemeSettings.click();
        return new UniThemeSettings();
    }
    public StickerSettings navigateToStickerSettingsPage(){
        navigateTo_DownloadedAddonsPage();
        menuOfStickerAddon.click();
        sectionStickerSettings.click();
        return new StickerSettings();
    }
    public void navigateToStickerListPage(){
        navigateTo_DownloadedAddonsPage();
        menuOfStickerAddon.click();
        sectionStickerList.click();
    }
    public VideoGallerySettings navigateToVideoGalleryPage(){
        navigateTo_DownloadedAddonsPage();
        menuOFVideoGalleryAddon.click();
        sectionVideoGalleryGeneralSettings.click();
        return new VideoGallerySettings();
    }
    public void navigateToStProductPage(int tabNumber){
        button_Save.click();
        Selenide.sleep(2000);
        gearWheelOnTop.click();
        button_Preview.click();
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }
}