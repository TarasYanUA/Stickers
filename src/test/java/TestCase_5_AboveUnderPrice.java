import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import storefront.AssertsOnStorefront;
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

    CsCartSettings csCartSettings = new CsCartSettings();
    StickerSettings stickerSettings = new StickerSettings();
    ProductSettings productSettings = new ProductSettings();
    AssertsOnStorefront assertsOnStorefront = new AssertsOnStorefront();

    @Test(priority = 10)
    public void TestCase_5_ConfigureSettings() {
        //Включаем мини-иконки в виде галереи и окно Быстрого просмотра
        csCartSettings.navigateTo_AppearanceSettings();
        if (!csCartSettings.settingQuickView.isSelected()) {
            csCartSettings.settingQuickView.click();
            Utils.saveSettings();
        }

        //Настраиваем два блока с товарами
        LayoutSettings layoutSettings = csCartSettings.navigateToSection_Layouts();
        layoutSettings.layout_TabProducts.click();
        layoutSettings.openBlockProperties("Самые популярные");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/ab__grid_list.tpl");
        layoutSettings.setFillingForBlockContent("newest");
        layoutSettings.saveBlockProperties();
        layoutSettings.openBlockProperties("Распродажа");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_scroller_advanced.tpl");
        layoutSettings.setBlockSettings();
        layoutSettings.saveBlockProperties();

        //Настраиваем два стикера
        csCartSettings.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerSettings.sticker_Promotion.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("price.before", "T", "small_size");
        //Стикер "Популярный" (фиолетовый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_PopularProduct.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("price.after", "T", "small_size");
        stickerSettings.generateStickerLinks();

        //Настраиваем формат цены в теме UniTheme2
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_ProductList.click();
        uniThemeSettings.setting_PriceDisplayFormat.selectOptionByValue("col-rev-mix");
        Utils.saveSettings();

        //Настраиваем страницу товара
        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        categorySettings.openCategoryPage("AB: Телефоны");
        categorySettings.activateCategoryAndSave();
        categorySettings.viewCategoryProducts();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.statusActive_Product.click();
        productSettings.field_ListPrice.setValue("2000");
        productSettings.productTemplate.selectOptionByValue("default_template");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_5_ConfigureSettings")
    public void TestCase_5_ProductPage() {
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedBeforePrice, "on product page", "");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedAfterPrice, "on product page", "");

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
        Utils.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(2);
        takeScreenshot("5130 ProdPage - AboveUnderPrice, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("5135 ProdPage(RTL) - AboveUnderPrice, BigPictureTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(3);
        takeScreenshot("5140 ProdPage - AboveUnderPrice, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("5145 ProdPage(RTL) - AboveUnderPrice, BigPictureFlatTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(4);
        takeScreenshot("5150 ProdPage - AboveUnderPrice, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("5155 ProdPage(RTL) - AboveUnderPrice, ThreeColumned");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(5);
        takeScreenshot("5160 ProdPage - AboveUnderPrice, CascadeGallery");
        shiftLanguage("ar");
        takeScreenshot("5165 ProdPage(RTL) - AboveUnderPrice, CascadeGallery");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(6);
        $(".ty-product-prices").scrollIntoCenter();
        takeScreenshot("5170 ProdPage - AboveUnderPrice, Gallery");
        shiftLanguage("ar");
        $(".ty-product-prices").scrollIntoCenter();
        takeScreenshot("5175 ProdPage(RTL) - AboveUnderPrice, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_5_ConfigureSettings")
    public void TestCase_5_CategoryPage_WishList() {
        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedBeforePrice, "on category page as Grid", "");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedAfterPrice, "on category page as Grid", "");

        takeScreenshot("5200 Category - AboveUnderPrice, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("5202 Category - AboveUnderPrice, Grid on hover");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.openQuickViewWindow();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedBeforePrice, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedAfterPrice, "in quick view window", assertsOnStorefront.quickViewWindow);

        takeScreenshot("5205 QuickView - AboveUnderPrice");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedBeforePrice, "on category page as List without options", "");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedAfterPrice, "on category page as List without options", "");

        takeScreenshot("5210 Category - AboveUnderPrice, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedBeforePrice, "on category page as Compact list", "");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedAfterPrice, "on category page as Compact list", "");

        takeScreenshot("5215 Category - AboveUnderPrice, CompactList");
        shiftLanguage("ar");
        takeScreenshot("5220 Category(RTL) - AboveUnderPrice, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("5225 Category(RTL) - AboveUnderPrice, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("5230 Category(RTL) - AboveUnderPrice, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("5232 Category(RTL) - AboveUnderPrice, Grid on hover");
        stCategoryPage.openQuickViewWindow();
        takeScreenshot("5235 QuickView(RTL) - AboveUnderPrice");
        stCategoryPage.button_CloseQuickView.click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.addProductToWishListAndNavigateToWishListPage();

        //Проверяем, что присутствует стикер ПЕРЕД ценой
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedBeforePrice, "on wishlist page", "");

        //Проверяем, что присутствует стикер ПОСЛЕ цены
        assertsOnStorefront.assertElementExists(assertsOnStorefront.locatedAfterPrice, "on wishlist page", "");

        stCategoryPage.productInList.hover();
        takeScreenshot("5250 WishList - AboveUnderPrice");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        takeScreenshot("5255 WishList(RTL) - AboveUnderPrice");
    }
}