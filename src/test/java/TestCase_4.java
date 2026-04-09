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
    - Позиция отображения: справа + сверху/снизу
    - Способ отображения: в строку
    - Внешний вид пиктограмм: Каплевидный
    - Позиция пиктограмм: Позиция 2
- Страница товара: все шаблоны
- Страница категории: все шаблоны + окно Быстрого просмотра
- Блок с товарами: шаблоны АВ: Легкий скроллер + Сетка
- Страница Избранных
*/

public class TestCase_4 extends TestRunner {

    BasicPage basicPage = new BasicPage();
    AssertsOnStorefront assertsOnStorefront = new AssertsOnStorefront();

    @Test(priority = 10)
    public void TestCase_4_ConfigureSettings() {
        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        VideoGallerySettings videoGallerySettings = basicPage.navigateToVideoGalleryPage();
        videoGallerySettings.setVerticalView(true);

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = basicPage.navigateToStickerSettingsPage();
        stickerSettings.configureStickerSettings("R", "row", "3");
        stickerSettings.configureAppearanceOfPictograms("teardrop");

        //Настраиваем Комбинации формаций изображений галереи товара (тема Uni2)
        UniThemeSettings uniThemeSettings = basicPage.navigateToUniThemeSettings();
        uniThemeSettings.setPictogramPositionsAtProductListTab("position_2");
        uniThemeSettings.goToProductTab();
        uniThemeSettings.fieldOfPictogramPosition_Product.selectOptionByValue("position_2");
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("1");
        Utils.saveSettings();

        //Переходим на страницу редактирования товара
        ProductSettings productSettings = basicPage.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.productTemplate.selectOptionByValue("default_template");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_4_ConfigureSettings")
    public void TestCase_4_ProductPage() {
        ProductSettings productSettings = basicPage.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что галерея мини-иконок вертикальная
        assertsOnStorefront.assertElementExists(assertsOnStorefront.verticalThumbnails, "on product page", "");

        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "on product page", "");

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "on product page", "");

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "on product page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on product page", "");

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "on product page", "");

        takeScreenshot("4100 ProdPage - VerticalIcons, RightRow, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("4105 BlockPopular - VerticalIcons, RightRow, LightScroller");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("4110 BlockHits - VerticalIcons, RightRow, Grid");
        shiftLanguage("ar");
        takeScreenshot("4115 ProdPage(RTL) - VerticalIcons, RightRow, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        stProductPage.stickersContainerAtProductBlock.shouldBe(Condition.visible);
        takeScreenshot("4120 BlockPopular(RTL) - VerticalIcons, RightRow, LightScroller");
        stProductPage.block_Hits.scrollIntoCenter().click();
        Selenide.sleep(3000);
        takeScreenshot("4125 BlockHits(RTL) - VerticalIcons, RightRow, Grid");

        //Смотрим другие шаблоны страницы товара
        Utils.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        productSettings.navigateTo_StProductPage(2);
        takeScreenshot("4130 ProdPage - VerticalIcons, RightRow, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("4135 ProdPage(RTL) - VerticalIcons, RightRow, BigPictureTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        productSettings.navigateTo_StProductPage(3);
        takeScreenshot("4140 ProdPage - VerticalIcons, RightRow, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("4145 ProdPage(RTL) - VerticalIcons, RightRow, BigPictureFlatTemplate");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        productSettings.navigateTo_StProductPage(4);
        takeScreenshot("4150 ProdPage - VerticalIcons, RightRow, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("4155 ProdPage(RTL) - VerticalIcons, RightRow, ThreeColumned");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        productSettings.navigateTo_StProductPage(5);
        takeScreenshot("4160 ProdPage - VerticalIcons, RightRow, CascadeGallery f1");
        shiftLanguage("ar");
        takeScreenshot("4165 ProdPage(RTL) - VerticalIcons, RightRow, CascadeGallery f1");

        Utils.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        Utils.saveSettings();
        productSettings.navigateTo_StProductPage(6);
        takeScreenshot("4170 ProdPage - VerticalIcons, RightRow, Gallery");
        shiftLanguage("ar");
        takeScreenshot("4175 ProdPage(RTL) - VerticalIcons, RightRow, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_4_ConfigureSettings")
    public void TestCase_4_CategoryPage(){
        CategorySettings categorySettings = basicPage.navigateToSection_Categories();
        categorySettings.openCategoryPage("AB: Телефоны");
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);
      
        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "on category page", "");

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "on category page", "");

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "on category page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page", "");

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "on category page", "");

        takeScreenshot("4200 Category - VerticalIcons, RightRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("4202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.openQuickViewWindow();
      
        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "in quick view window", assertsOnStorefront.quickViewWindow);

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "in quick view window", assertsOnStorefront.quickViewWindow);

        takeScreenshot("4205 QuickView - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("4210 Category - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersExist, "on category page as Compact list", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on category page as Compact list", "");

        takeScreenshot("4215 Category - VerticalIcons, RightRow, CompactList");
        shiftLanguage("ar");
        takeScreenshot("4220 Category(RTL) - VerticalIcons, RightRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("4225 Category(RTL) - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Utils.waitForSpinnerDisappear();
        takeScreenshot("4230 Category(RTL) - VerticalIcons, RightRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("4232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.openQuickViewWindow();
        takeScreenshot("4235 QuickView(RTL) - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.hover().click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.addProductToWishListAndNavigateToWishListPage();

        //Проверяем, что присутствуют стикеры справа и вверху
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_TopRight, "on wishlist page", "");

        //Проверяем, что присутствуют стикеры справа и внизу
        assertsOnStorefront.assertElementExists(assertsOnStorefront.stickersContainer_BottomRight, "on wishlist page", "");

        //Проверяем, что стикеры расположены в строку
        assertsOnStorefront.assertElementExists(assertsOnStorefront.rowFilling, "on wishlist page", "");

        //Проверяем, что пиктограммы присутствуют
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsExist, "on wishlist page", "");

        //Проверяем, что пиктограммы расположены в позиции 2
        assertsOnStorefront.assertElementExists(assertsOnStorefront.pictogramsPosition_2, "on wishlist page", "");

        stCategoryPage.productInList.hover();
        takeScreenshot("4250 WishList - VerticalIcons, RightRow");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        takeScreenshot("4255 WishList(RTL) - VerticalIcons, RightRow");
    }
}