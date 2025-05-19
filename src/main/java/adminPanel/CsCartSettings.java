package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class CsCartSettings implements CheckMenuToBeActive {
    public CsCartSettings() {
        super();
    }

    SelenideElement notification = $(".cm-notification-close");
    SelenideElement cookieNotice = $(".cookie-notice");
    SelenideElement gearWheelOnTop = $(".nav__actions-bar .dropdown-icon--tools");
    SelenideElement button_Preview = $x("//ul[@class='dropdown-menu']//a[contains(text(), 'Предпросмотр')]");
    SelenideElement menu_Website = $("a[href$='dispatch=themes.manage'].main-menu-1__link");
    SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    SelenideElement menu_Addons = $("a[href$='dispatch=addons.manage'].main-menu-1__link");
    SelenideElement section_Themes = $("#website_themes");
    SelenideElement section_Layouts = $(".nav__actions-bar a[href$='block_manager.manage']");
    SelenideElement section_Products = $(By.id("products_products"));
    SelenideElement section_Categories = $(By.id("products_categories"));
    SelenideElement section_DownloadedAddons = $("#addons_downloaded_add_ons");

    //Меню "Модули -- Скачанные модули"
    SelenideElement menuOfUniTheme = $("tr#addon_abt__unitheme2 button.btn.dropdown-toggle");
    SelenideElement sectionThemeSettings = $("div.nowrap a[href*='abt__ut2.settings']");
    SelenideElement menuOfStickerAddon = $("tr#addon_ab__stickers button.btn.dropdown-toggle");
    SelenideElement sectionStickerSettings = $("div.nowrap a[href*='addon=ab__stickers']");
    SelenideElement sectionStickerList = $("tr#addon_ab__stickers a[href*='ab__stickers.manage']");
    SelenideElement menuOFVideoGalleryAddon = $("tr#addon_ab__video_gallery button.btn.dropdown-toggle");
    SelenideElement sectionVideoGalleryGeneralSettings = $("tr#addon_ab__video_gallery a[href*='selected_section=settings']");

    //Меню "Настройки -- Общие настройки"
    SelenideElement menu_Settings = $("#administration");
    SelenideElement section_GeneralSettings = $("a[href$='section_id=General']");
    SelenideElement section_Appearance = $("a[href*='section_id=Appearance']");
    public SelenideElement settingMiniThumbnailAsGallery = $("#field___thumbnails_gallery_147");
    public SelenideElement settingQuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");


    public void closeNotificationIfExists() {
        if (notification.exists())
            notification.click();
    }

    public void closeCookieNoticeIfExists() {
        if (cookieNotice.exists()){
            cookieNotice.shouldBe(Condition.interactable);
            $(".cm-btn-success").click();
        }
    }

    public void openPreviewPage(int tabNumber) {
        closeNotificationIfExists();
        gearWheelOnTop.click();
        button_Preview.click();
        Utils.shiftBrowserTab(tabNumber);
        closeCookieNoticeIfExists();
    }

    public LayoutSettings navigateToSection_Layouts() {
        checkMenuToBeActive("dispatch=themes.manage", menu_Website);
        section_Themes.click();
        section_Layouts.click();
        return new LayoutSettings();
    }

    public ProductSettings navigateToSection_Products() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Products.click();
        return new ProductSettings();
    }

    public CategorySettings navigateToSection_Categories() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Categories.click();
        return new CategorySettings();
    }

    void navigateTo_DownloadedAddonsPage() {
        checkMenuToBeActive("dispatch=addons.manage", menu_Addons);
        section_DownloadedAddons.click();
    }

    public UniThemeSettings navigateToUniThemeSettings() {
        navigateTo_DownloadedAddonsPage();
        menuOfUniTheme.click();
        sectionThemeSettings.click();
        return new UniThemeSettings();
    }

    public StickerSettings navigateToStickerSettingsPage() {
        navigateTo_DownloadedAddonsPage();
        menuOfStickerAddon.click();
        sectionStickerSettings.click();
        return new StickerSettings();
    }

    public void navigateToStickerListPage() {
        navigateTo_DownloadedAddonsPage();
        menuOfStickerAddon.click();
        sectionStickerList.click();
    }

    public VideoGallerySettings navigateToVideoGalleryPage() {
        navigateTo_DownloadedAddonsPage();
        menuOFVideoGalleryAddon.click();
        sectionVideoGalleryGeneralSettings.click();
        return new VideoGallerySettings();
    }

    public void navigateTo_AppearanceSettings() {
        menu_Settings.click();
        section_GeneralSettings.click();
        section_Appearance.click();
    }
}