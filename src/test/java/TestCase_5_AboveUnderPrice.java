import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StCategoryPage;
import storefront.StProductPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/*
Проверяем следующее:
- CS-Cart: Быстрый просмотр
- Модуль "Стикеры":
    - Позиция отображения: Над ценой/ Под ценой
- Страница товара: все шаблоны
- Страница категории: все шаблоны + окно Быстрого просмотра
- Блок с товарами: шаблоны АВ: Сетка (с кнопкой "Показать ещё") + АВ: Расширенный скроллер товаров
- Страница Избранных
- UniTheme2:
    - Списки товаров -- Формат отображения цен -- Вариант 6
*/
public class TestCase_5_AboveUnderPrice extends TestRunner {
    @Test(priority = 10)
    public void TestCase_5_ConfigureSettings() {
        //Включаем мини-иконки в виде галереи и окно Быстрого просмотра
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateTo_AppearanceSettings();
        if (!csCartSettings.settingQuickView.isSelected()) {
            csCartSettings.settingQuickView.click();
            csCartSettings.button_Save.click();
        }

        //Настраиваем два блока с товарами
        csCartSettings.navigateToSectionLayouts();
        csCartSettings.layout_TabProducts.click();
        csCartSettings.layout_GearwheelOfBlockPopular.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/ab__grid_list.tpl");
        csCartSettings.layoutBlock_TabContent.click();
        csCartSettings.layout_FieldFilling.selectOptionByValue("newest");
        csCartSettings.clickAndType_Layout_FieldMaxLimit();
        csCartSettings.layout_ButtonSaveBlock.click();
        csCartSettings.layout_GearwheelOfBlockHits.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_scroller_advanced.tpl");
        csCartSettings.layout_ButtonSettings.click();
        if (!csCartSettings.layoutSettings_ShowPrice.isSelected())
            csCartSettings.layoutSettings_ShowPrice.click();
        if (csCartSettings.layoutSetting_OutsideNavigation.isSelected())
            csCartSettings.layoutSetting_OutsideNavigation.scrollIntoCenter().click();
        csCartSettings.layout_ButtonSaveBlock.click();

        //Настраиваем два стикера
        StickerSettings stickerSettings = new StickerSettings();
        csCartSettings.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerSettings.sticker_Promotion.click();
        stickerSettings.statusActive.click();
        addConditionOfPrice(stickerSettings);
        stickerSettings.setSettingsAt_DisplayTab("price.before", "T", "small_size");
        stickerSettings.button_SaveSticker.click();
        //Стикер "Популярный" (фиолетовый цвет)
        stickerSettings.abMenu_dropDownToggle.click();
        stickerSettings.abMenu_StickerList.click();
        stickerSettings.sticker_PopularProduct.click();
        stickerSettings.statusActive.click();
        addConditionOfPrice(stickerSettings);
        stickerSettings.setSettingsAt_DisplayTab("price.after", "T", "small_size");
        stickerSettings.button_SaveSticker.click();
        stickerSettings.gearWheel.click();
        stickerSettings.generateStickerLinks.click();
        Selenide.sleep(3000);

        //Настраиваем формат цены в теме UniTheme2
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_ProductList.click();
        uniThemeSettings.setting_PriceDisplayFormat.selectOptionByValue("col-rev-mix");
        csCartSettings.button_Save.click();

        //Настраиваем страницу товара
        csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        csCartSettings.statusActive_Category.click();
        csCartSettings.saveSettings();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_ViewProducts.click();
        ProductSettings productSettings = new ProductSettings();
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        productSettings.statusActive_Product.click();
        productSettings.field_ListPrice.click();
        productSettings.field_ListPrice.clear();
        productSettings.field_ListPrice.sendKeys("2000");
        productSettings.productTemplate.selectOptionByValue("default_template");
        csCartSettings.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_5_ConfigureSettings")
    public void TestCase_5_ProductPage() {
        CsCartSettings csCartSettings = new CsCartSettings();
        ProductSettings productSettings = new ProductSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Products();
        productSettings.clickAndType_ProductSearch("Apple iPhone 14");
        csCartSettings.chooseAnyProduct();
        StProductPage stProductPage = csCartSettings.navigateToStProductPage(1);

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        softAssert.assertTrue($(".ab-stickers-container__price_before").exists(),
                "There is no sticker, located BEFORE price on the product page!");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        softAssert.assertTrue($(".ab-stickers-container__price_after").exists(),
                "There is no sticker, located AFTER price on the product page!");

        takeScreenshot("5100 ProdPage - AboveUnderPrice, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("5105 BlockPopular - AboveUnderPrice, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("5110 BlockHits - AboveUnderPrice, AdvancedScroller");
        shiftLanguage("ar");
        takeScreenshot("5115 ProdPage(RTL) - AboveUnderPrice, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("5120 BlockPopular(RTL) - AboveUnderPrice, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("5125 BlockHits(RTL) - AboveUnderPrice, AdvancedScroller");

        //Смотрим другие шаблоны страницы товара
        csCartSettings.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        csCartSettings.saveSettings();
        csCartSettings.navigateToStProductPage(2);
        takeScreenshot("5130 ProdPage - AboveUnderPrice, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("5135 ProdPage(RTL) - AboveUnderPrice, BigPictureTemplate");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        csCartSettings.saveSettings();
        csCartSettings.navigateToStProductPage(3);
        takeScreenshot("5140 ProdPage - AboveUnderPrice, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("5145 ProdPage(RTL) - AboveUnderPrice, BigPictureFlatTemplate");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        csCartSettings.saveSettings();
        csCartSettings.navigateToStProductPage(4);
        takeScreenshot("5150 ProdPage - AboveUnderPrice, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("5155 ProdPage(RTL) - AboveUnderPrice, ThreeColumned");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        csCartSettings.saveSettings();
        csCartSettings.navigateToStProductPage(5);
        takeScreenshot("5160 ProdPage - AboveUnderPrice, CascadeGallery");
        shiftLanguage("ar");
        takeScreenshot("5165 ProdPage(RTL) - AboveUnderPrice, CascadeGallery");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        csCartSettings.saveSettings();
        csCartSettings.navigateToStProductPage(6);
        $(".ty-product-prices").scrollIntoCenter();
        takeScreenshot("5170 ProdPage - AboveUnderPrice, Gallery");
        shiftLanguage("ar");
        $(".ty-product-prices").scrollIntoCenter();
        takeScreenshot("5175 ProdPage(RTL) - AboveUnderPrice, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_5_ConfigureSettings")
    public void TestCase_5_CategoryPage_WishList() {
        CsCartSettings csCartSettings = new CsCartSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = csCartSettings.navigateToStCategoryPage(1);

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        softAssert.assertTrue($(".ab-stickers-container__price_before").exists(),
                "There is no sticker, located BEFORE price on the category page 'Grid'!");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        softAssert.assertTrue($(".ab-stickers-container__price_after").exists(),
                "There is no sticker, located AFTER price on the category page 'Grid'!");

        takeScreenshot("5200 Category - AboveUnderPrice, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("5202 Category - AboveUnderPrice, Grid on hover");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        softAssert.assertTrue($(".ui-dialog .ab-stickers-container__price_before").exists(),
                "There is no sticker, located BEFORE price on the on quick view window!");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        softAssert.assertTrue($(".ui-dialog .ab-stickers-container__price_after").exists(),
                "There is no sticker, located AFTER price on the on quick view window!");

        takeScreenshot("5205 QuickView - AboveUnderPrice");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        softAssert.assertTrue($(".ab-stickers-container__price_before").exists(),
                "There is no sticker, located BEFORE price on the category page 'List without options'!");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        softAssert.assertTrue($(".ab-stickers-container__price_after").exists(),
                "There is no sticker, located AFTER price on the category page 'List without options'!");

        takeScreenshot("5210 Category - AboveUnderPrice, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        waitForSpinnerDisappear();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        softAssert.assertTrue($(".ab-stickers-container__price_before").exists(),
                "There is no sticker, located BEFORE price on the category page 'Compact list'!");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        softAssert.assertTrue($(".ab-stickers-container__price_after").exists(),
                "There is no sticker, located AFTER price on the category page 'Compact list'!");

        takeScreenshot("5215 Category - AboveUnderPrice, CompactList");
        shiftLanguage("ar");
        takeScreenshot("5220 Category(RTL) - AboveUnderPrice, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("5225 Category(RTL) - AboveUnderPrice, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        waitForSpinnerDisappear();
        takeScreenshot("5230 Category(RTL) - AboveUnderPrice, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("5232 Category(RTL) - AboveUnderPrice, Grid on hover");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
        takeScreenshot("5235 QuickView(RTL) - AboveUnderPrice");
        stCategoryPage.button_CloseQuickView.click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        waitForSpinnerDisappear();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(3000);

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        softAssert.assertTrue($(".ab-stickers-container__price_before").exists(),
                "There is no sticker, located BEFORE price on Wishlist page!");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        softAssert.assertTrue($(".ab-stickers-container__price_after").exists(),
                "There is no sticker, located AFTER price on Wishlist page!");

        stCategoryPage.productInList.hover();
        takeScreenshot("5250 WishList - AboveUnderPrice");
        shiftLanguage("ar");
        Selenide.sleep(3000);
        stCategoryPage.productInList.hover();
        takeScreenshot("5255 WishList(RTL) - AboveUnderPrice");
    }

    private static void addConditionOfPrice(StickerSettings stickerSettings) {
        stickerSettings.tab_Conditions.hover().click();
        if (stickerSettings.tableOfConditions.exists()) {
            stickerSettings.tableOfConditions.hover();
            stickerSettings.button_DeleteCondition.click();
        }
        stickerSettings.button_AddCondition.shouldBe(Condition.interactable).click();
        stickerSettings.fieldOfConditions.selectOptionByValue("price");
        stickerSettings.fieldOfOperator.selectOptionByValue("gte");
        stickerSettings.clickAndType_PriceCondition("1400");
    }
}