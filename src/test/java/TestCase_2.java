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
    - Позиция отображения: слева + сверху/снизу
    - Способ отображения: в строку
    - Позиция пиктограмм: Позиция 1
- Страница товара: все шаблоны
- Страница категории: все шаблоны + окно Быстрого просмотра
- Блок с товарами: шаблоны АВ: Сетка (с кнопкой "Показать ещё") + АВ: Расширенный скроллер товаров
- Страница Избранных
*/

public class TestCase_2 extends TestRunner {

    CsCartSettings csCartSettings = new CsCartSettings();
    AssertsOnStorefront assertsOnStorefront = new AssertsOnStorefront();

    @Test(priority = 10)
    public void TestCase_2_ConfigureSettings() {
        //Включаем Горизонтальное отображение мини-иконок (Модуль "Видео галерея")
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.setVerticalView(false);

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.configureStickerSettings("L", "row", "3");

        //Настраиваем Комбинации формаций изображений галереи товара (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.setPictogramPositionsAtProductListTab("position_1");
        uniThemeSettings.goToProductTab();
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("2");
        Utils.saveSettings();

        //Переходим на страницу редактирования товара
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.productTemplate.selectOptionByValue("default_template");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_2_ConfigureSettings")
    public void TestCase_2_ProductPage() {
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что галерея мини-иконок горизонтальная
        assertsOnStorefront.assertElementExists(assertsOnStorefront.horizontalThumbnails, "on product page", "");

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "on product page", "");

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "on product page", "");

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "on product page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on product page", "");

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "on product page", "");

        takeScreenshot("2100 ProdPage - HorizontalIcons, LeftRow, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("2105 BlockPopular - HorizontalIcons, LeftRow, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("2110 BlockHits - HorizontalIcons, LeftRow, AdvancedScroller");
        shiftLanguage("ar");
        takeScreenshot("2115 ProdPage(RTL) - HorizontalIcons, LeftRow, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("2120 BlockPopular(RTL) - HorizontalIcons, LeftRow, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("2125 BlockHits(RTL) - HorizontalIcons, LeftRow, AdvancedScroller");

        //Смотрим другие шаблоны страницы товара
        Utils.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(2);
        takeScreenshot("2130 ProdPage - HorizontalIcons, LeftRow, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("2135 ProdPage(RTL) - HorizontalIcons, LeftRow, BigPictureTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(3);
        takeScreenshot("2140 ProdPage - HorizontalIcons, LeftRow, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("2145 ProdPage(RTL) - HorizontalIcons, LeftRow, BigPictureFlatTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(4);
        takeScreenshot("2150 ProdPage - HorizontalIcons, LeftRow, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("2155 ProdPage(RTL) - HorizontalIcons, LeftRow, ThreeColumned");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(5);
        takeScreenshot("2160 ProdPage - HorizontalIcons, LeftRow, CascadeGallery f2");
        shiftLanguage("ar");
        takeScreenshot("2165 ProdPage(RTL) - HorizontalIcons, LeftRow, CascadeGallery f2");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(6);
        takeScreenshot("2170 ProdPage - HorizontalIcons, LeftRow, Gallery");
        shiftLanguage("ar");
        takeScreenshot("2175 ProdPage(RTL) - HorizontalIcons, LeftRow, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_2_ConfigureSettings")
    public void TestCase_2_CategoryPage() {
        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "on category page", "");

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "on category page", "");

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "on category page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page", "");

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "on category page", "");

        takeScreenshot("2200 Category - HorizontalIcons, LeftRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("2202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.openQuickViewWindow();

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "in quick view window", assertsOnStorefront.quickViewWindow);

        takeScreenshot("2205 QuickView - HorizontalIcons, LeftRow");
        stCategoryPage.button_CloseQuickView.hover().click();
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("2210 Category - HorizontalIcons, LeftRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersExist, "on category page as Compact list", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page as Compact list", "");

        takeScreenshot("2215 Category - HorizontalIcons, LeftRow, CompactList");
        shiftLanguage("ar");
        takeScreenshot("2220 Category(RTL) - HorizontalIcons, LeftRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("2225 Category(RTL) - HorizontalIcons, LeftRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("2230 Category(RTL) - HorizontalIcons, LeftRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("2232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.openQuickViewWindow();
        takeScreenshot("2235 QuickView(RTL) - HorizontalIcons, LeftRow");
        stCategoryPage.button_CloseQuickView.hover().click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.addProductToWishListAndNavigateToWishListPage();

        //Проверяем, что присутствуют стикеры слева и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopLeft, "on wishlist page", "");

        //Проверяем, что присутствуют стикеры слева и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomLeft, "on wishlist page", "");

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "on wishlist page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on wishlist page", "");

        //Проверяем, что пиктограммы расположены в позиции 1
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_1, "on wishlist page", "");

        stCategoryPage.productInList.hover();
        takeScreenshot("2250 WishList - HorizontalIcons, LeftRow");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        takeScreenshot("2255 WishList(RTL) - HorizontalIcons, LeftRow");
    }
}