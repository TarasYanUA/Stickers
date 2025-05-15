package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CsCartSettings implements CheckMenuToBeActive {
    public CsCartSettings() {
        super();
    }

    public static SelenideElement notification = $(".cm-notification-close");
    public static SelenideElement popupWindow = $(".ui-dialog-title");
    public static SelenideElement cookieNotice = $(".cookie-notice");
    public static SelenideElement gearWheelOnTop = $(".nav__actions-bar .dropdown-icon--tools");
    public static SelenideElement button_Preview = $x("//ul[@class='dropdown-menu']//a[contains(text(), 'Предпросмотр')]");
    public static SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    SelenideElement menu_Website = $("a[href$='dispatch=themes.manage'].main-menu-1__link");
    SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    SelenideElement section_Themes = $("#website_themes");
    SelenideElement section_Layouts = $(".nav__actions-bar a[href$='block_manager.manage']");
    SelenideElement section_Products = $(By.id("products_products"));
    SelenideElement section_Categories = $(By.id("products_categories"));


    public static void waitForPopupWindowAppear() {
        popupWindow.shouldBe(Condition.visible);
        Selenide.sleep(1000);
    }

    public static void waitForPopupWindowDisappear() {
        popupWindow.shouldBe(Condition.disappear);
        Selenide.sleep(1000);
    }


    public static void shiftBrowserTab(int tabNumber) {
        getWebDriver().getWindowHandle();
        switchTo().window(tabNumber);
    }

    public static void closeNotificationIfExists() {
        if (notification.exists())
            notification.click();
    }

    public static void closeCookieNoticeIfExists() {
        if (cookieNotice.exists()){
            cookieNotice.shouldBe(Condition.interactable);
            $(".cm-btn-success").click();
        }
    }

    public static void openPreviewPage(int tabNumber) {
        gearWheelOnTop.click();
        button_Preview.click();
        shiftBrowserTab(tabNumber);
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


    //Меню "Настройки -- Общие настройки"
    SelenideElement menu_Settings = $("#administration");
    SelenideElement section_GeneralSettings = $("a[href$='section_id=General']");
    SelenideElement section_Appearance = $("a[href*='section_id=Appearance']");
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

    public static void saveSettings() {
        button_Save.click();
        Selenide.sleep(1500);
    }
}