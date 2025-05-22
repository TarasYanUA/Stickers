import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import storefront.AssertsOnStorefront;
import storefront.StCategoryPage;
import storefront.StProductPage;

import static com.codeborne.selenide.Selenide.$x;

/*
ссылка на тест-кейс: https://docs.google.com/spreadsheets/d/1UdXKRCHxD7XP7W3UzDN28ff10LyiJPbZKrdvZllpUCU/edit#gid=1582514111
Проверяем следующее:
- CS-Cart: Быстрый просмотр + мини-иконки галереи
- Модуль "Видео галерея": Горизонтальное отображение галереи
- Модуль "Стикеры":
    - Позиция отображения: справа + сверху/снизу
    - Способ отображения: в колонку
    - Внешний вид пиктограмм: Каплевидный
    - Позиция пиктограмм: Позиция 2
- Страница товара: все шаблоны
- Страница категории: все шаблоны + окно Быстрого просмотра
- Блок с товарами: шаблоны АВ: Легкий скроллер + Сетка
- Страница Избранных
*/

public class TestCase_3 extends TestRunner {

    CsCartSettings csCartSettings = new CsCartSettings();
    AssertsOnStorefront assertsOnStorefront = new AssertsOnStorefront();

    @Test(priority = 10)
    public void TestCase_3_ConfigureSettings() {
        //Включаем Горизонтальное отображение мини-иконок (Модуль "Видео галерея")
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.setVerticalView(false);

        //Настраиваем позицию пиктограмм (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.setPictogramPositionsAtProductListTab("position_2");
        //Настраиваем Комбинации формаций изображений галереи товара
        uniThemeSettings.goToProductTab();
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("3");
        Utils.saveSettings();

        //Настраиваем блок с товарами
        LayoutSettings layoutSettings = csCartSettings.navigateToSection_Layouts();
        layoutSettings.layout_TabProducts.click();
        layoutSettings.openBlockProperties("Самые популярные");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_native_scroller_advanced.tpl");
        layoutSettings.saveBlockProperties();
        layoutSettings.openBlockProperties("Распродажа");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_multicolumns.tpl");
        layoutSettings.saveBlockProperties();

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.configureStickerSettings("R", "column", "3");
        stickerSettings.configureAppearanceOfPictograms("teardrop");

        //Настраиваем страницу товара
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.productTemplate.selectOptionByValue("default_template");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_3_ConfigureSettings")
    public void TestCase_3_ProductPage() {
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что галерея мини-иконок горизонтальная
        assertsOnStorefront.assertElementExists(assertsOnStorefront.horizontalThumbnails, "on product page", "");

        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "on product page", "");

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "on product page", "");

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "on product page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on product page", "");

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "on product page", "");

        takeScreenshot("3100 ProdPage - HorizontalIcons, RightColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("3105 BlockPopular - HorizontalIcons, RightColumn, LightScroller");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("3110 BlockHits - HorizontalIcons, RightColumn, Grid");
        shiftLanguage("ar");
        takeScreenshot("3115 ProdPage(RTL) - HorizontalIcons, RightColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("3120 BlockPopular(RTL) - HorizontalIcons, RightColumn, LightScroller");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("3125 BlockHits(RTL) - HorizontalIcons, RightColumn, Grid");

        //Смотрим другие шаблоны страницы товара
        Utils.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        productSettings.navigateTo_StProductPage(2);
        takeScreenshot("3130 ProdPage - HorizontalIcons, RightColumn, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("3135 ProdPage(RTL) - HorizontalIcons, RightColumn, BigPictureTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        productSettings.navigateTo_StProductPage(3);
        takeScreenshot("3140 ProdPage - HorizontalIcons, RightColumn, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("3145 ProdPage(RTL) - HorizontalIcons, RightColumn, BigPictureFlatTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        productSettings.navigateTo_StProductPage(4);
        takeScreenshot("3150 ProdPage - HorizontalIcons, RightColumn, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("3155 ProdPage(RTL) - HorizontalIcons, RightColumn, ThreeColumned");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        productSettings.navigateTo_StProductPage(5);
        takeScreenshot("3160 ProdPage - HorizontalIcons, RightColumn, CascadeGallery f3");
        shiftLanguage("ar");
        takeScreenshot("3165 ProdPage(RTL) - HorizontalIcons, RightColumn, CascadeGallery f3");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(6);
        takeScreenshot("3170 ProdPage - HorizontalIcons, RightColumn, Gallery");
        shiftLanguage("ar");
        takeScreenshot("3175 ProdPage(RTL) - HorizontalIcons, RightColumn, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_3_ConfigureSettings")
    public void TestCase_3_CategoryPage(){
        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);
        
        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "on category page", "");

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "on category page", "");

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "on category page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page", "");

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "on category page", "");

        takeScreenshot("3200 Category - HorizontalIcons, RightColumn, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("3202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.openQuickViewWindow();

        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "in quick view window", assertsOnStorefront.quickViewWindow);

        takeScreenshot("3205 QuickView - HorizontalIcons, RightColumn");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("3210 Category - HorizontalIcons, RightColumn, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersExist, "on category page as Compact list", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page as Compact list", "");

        takeScreenshot("3215 Category - HorizontalIcons, RightColumn, CompactList");
        shiftLanguage("ar");
        takeScreenshot("3220 Category(RTL) - HorizontalIcons, RightColumn, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("3225 Category(RTL) - HorizontalIcons, RightColumn, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("3230 Category(RTL) - HorizontalIcons, RightColumn, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("3232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.openQuickViewWindow();
        takeScreenshot("3235 QuickView(RTL) - HorizontalIcons, RightColumn");
        stCategoryPage.button_CloseQuickView.hover().click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.addProductToWishListAndNavigateToWishListPage();

        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "on wishlist page", "");

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "on wishlist page", "");

        //Проверяем, что стикеры расположены в колонку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.columnFilling, "on wishlist page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on wishlist page", "");

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "on wishlist page", "");

        stCategoryPage.productInList.hover();
        takeScreenshot("3250 WishList - HorizontalIcons, RightColumn");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        takeScreenshot("3255 WishList(RTL) - HorizontalIcons, RightColumn");
    }
}