import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import storefront.AssertsOnStorefront;
import storefront.StCategoryPage;
import storefront.StProductPage;

/*
ссылка на тест-кейс: https://docs.google.com/spreadsheets/d/1UdXKRCHxD7XP7W3UzDN28ff10LyiJPbZKrdvZllpUCU/edit#gid=1582514111
Проверяем следующее:
- CS-Cart: Быстрый просмотр + мини-иконки галереи
- Модуль "Видео галерея": Вертикальное отображение галереи
- Модуль "Стикеры":
    - Позиция отображения: слева + сверху/снизу
    - Способ отображения: в колонку
    - На изображении товара
    - Полный вид
    - Позиция пиктограмм: Позиция 1
- Страница товара: все шаблоны
- Страница категории: все шаблоны + окно Быстрого просмотра
- Блок с товарами: шаблоны АВ: Сетка (с кнопкой "Показать ещё") + АВ: Расширенный скроллер товаров
- Страница Избранных
*/

public class TestCase_1 extends TestRunner {

    BasicPage basicPage = new BasicPage();
    ProductSettings productSettings = new ProductSettings();
    AssertsOnStorefront assertsOnStorefront = new AssertsOnStorefront();

    @Test(priority = 10)
    public void TestCase_1_ConfigureSettings() {
        //Включаем мини-иконки в виде галереи и окно Быстрого просмотра
        basicPage.navigateTo_AppearanceSettings();
        Utils.setCheckbox(basicPage.settingMiniThumbnailAsGallery, true, "settingMiniThumbnailAsGallery");
        Utils.setCheckbox(basicPage.settingQuickView, true, "settingQuickView");
        Utils.saveSettings();

        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        VideoGallerySettings videoGallerySettings = basicPage.navigateToVideoGalleryPage();
        videoGallerySettings.setVerticalView(true);

        //Настраиваем позицию пиктограмм (тема Uni2)
        UniThemeSettings uniThemeSettings = basicPage.navigateToUniThemeSettings();
        uniThemeSettings.setPictogramPositionsAtProductListTab("position_1");
        uniThemeSettings.goToProductTab();
        uniThemeSettings.fieldOfPictogramPosition_Product.selectOptionByValue("position_1");
        Utils.saveSettings();

        //Настраиваем блок с товарами
        LayoutSettings layoutSettings = basicPage.navigateToSection_Layouts();
        layoutSettings.layout_TabProducts.click();
        layoutSettings.openBlockProperties("Самые популярные");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/ab__grid_list.tpl");
        layoutSettings.setFillingForBlockContent("newest");
        layoutSettings.saveBlockProperties();
        layoutSettings.openBlockProperties("Распродажа");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_scroller_advanced.tpl");
        layoutSettings.saveBlockProperties();

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = basicPage.navigateToStickerSettingsPage();
        stickerSettings.configureStickerSettings("L", "column", "3");

        //Три верхних стикера
        basicPage.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerSettings.sticker_Promotion.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        //Стикер "Sale > 10% < 30%" (оранжевый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_SaleOrange.click();
        stickerSettings.statusActive.click();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        //Стикер "Популярный" (фиолетовый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_PopularProduct.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        stickerSettings.generateStickerLinks();

        //Три нижних стикера
        //Стикер "Высокий рейтинг" (оранжевый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_TopRated.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        //Стикер "Бесплатная доставка" (цвет сине-белый)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_Free_Delivery.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        stickerSettings.generateStickerLinks();
        //Стикер "Вес" (цвет серый)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_Weight.click();
        stickerSettings.statusActive.click();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");

        //Настраиваем страницу товара
        CategorySettings categorySettings = basicPage.navigateToSection_Categories();
        categorySettings.openCategoryPage("AB: Телефоны");
        categorySettings.activateCategoryAndSave();
        categorySettings.viewCategoryProducts();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.statusActive_Product.click();
        productSettings.field_ListPrice.setValue("2000");
        productSettings.productTemplate.selectOptionByValue("default_template");
        productSettings.setValueTo_ProductWeight("9");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_1_ConfigureSettings")
    public void TestCase_1_ProductPage() {
        basicPage.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что галерея мини-иконок вертикальная
        assertsOnStorefront.assertElementExists(assertsOnStorefront.verticalThumbnails, "on product page", "");

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "on product page", "");

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "on product page", "");

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "on product page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on product page", "");

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "on product page", "");

        takeScreenshot("1100 ProdPage - VerticalIcons, LeftColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("1105 BlockPopular - VerticalIcons, LeftColumn, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("1110 BlockHits - VerticalIcons, LeftColumn, AdvancedScroller");
        shiftLanguage("ar");
        takeScreenshot("1115 ProdPage(RTL) - VerticalIcons, LeftColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("1120 BlockPopular(RTL) - VerticalIcons, LeftColumn, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("1125 BlockHits(RTL) - VerticalIcons, LeftColumn, AdvancedScroller");

        //Смотрим другие шаблоны страницы товара
        Utils.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(2);
        takeScreenshot("1130 ProdPage - VerticalIcons, LeftColumn, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("1135 ProdPage(RTL) - VerticalIcons, LeftColumn, BigPictureTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(3);
        takeScreenshot("1140 ProdPage - VerticalIcons, LeftColumn, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("1145 ProdPage(RTL) - VerticalIcons, LeftColumn, BigPictureFlatTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(4);
        takeScreenshot("1150 ProdPage - VerticalIcons, LeftColumn, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("1155 ProdPage(RTL) - VerticalIcons, LeftColumn, ThreeColumned");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(5);
        takeScreenshot("1160 ProdPage - VerticalIcons, LeftColumn, CascadeGallery");
        shiftLanguage("ar");
        takeScreenshot("1165 ProdPage(RTL) - VerticalIcons, LeftColumn, CascadeGallery");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(6);
        takeScreenshot("1170 ProdPage - VerticalIcons, LeftColumn, Gallery");
        shiftLanguage("ar");
        takeScreenshot("1175 ProdPage(RTL) - VerticalIcons, LeftColumn, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_1_ConfigureSettings")
    public void TestCase_1_CategoryPage_WishList() {
        CategorySettings categorySettings = basicPage.navigateToSection_Categories();
        categorySettings.openCategoryPage("AB: Телефоны");
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "on category page", "");

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "on category page", "");

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "on category page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page", "");

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "on category page", "");

        takeScreenshot("1200 Category - VerticalIcons, LeftColumn, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("1202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.openQuickViewWindow();

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "in quick view window", assertsOnStorefront.quickViewWindow);

        takeScreenshot("1205 QuickView - VerticalIcons, LeftColumn");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();

        //Проверяем, что стикеры присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersExist, "on category page as List without options", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page", "");

        Utils.waitForSpinnerDisappear();
        takeScreenshot("1210 Category - VerticalIcons, LeftColumn, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersExist, "on category page as Compact list", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page as Compact list", "");

        takeScreenshot("1215 Category - VerticalIcons, LeftColumn, CompactList");
        shiftLanguage("ar");
        takeScreenshot("1220 Category(RTL) - VerticalIcons, LeftColumn, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("1225 Category(RTL) - VerticalIcons, LeftColumn, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("1230 Category(RTL) - VerticalIcons, LeftColumn, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("1232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.openQuickViewWindow();
        takeScreenshot("1235 QuickView(RTL) - VerticalIcons, LeftColumn");
        stCategoryPage.button_CloseQuickView.click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.addProductToWishListAndNavigateToWishListPage();

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "on wishlist page", "");

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "on wishlist page", "");

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "on wishlist page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on wishlist page", "");

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "on wishlist page", "");

        stCategoryPage.productInList.hover();
        takeScreenshot("1250 WishList - VerticalIcons, LeftColumn");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        takeScreenshot("1255 WishList(RTL) - VerticalIcons, LeftColumn");
    }
}