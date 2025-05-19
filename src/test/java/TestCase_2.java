import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import storefront.StCategoryPage;
import storefront.StProductPage;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.$;
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
    @Test(priority = 10)
    public void TestCase_2_ConfigureSettings() {
        //Включаем Горизонтальное отображение мини-иконок (Модуль "Видео галерея")
        CsCartSettings csCartSettings = new CsCartSettings();
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.configureStickerSettings("L", "row", "3");

        //Настраиваем Комбинации формаций изображений галереи товара (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
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
        CsCartSettings csCartSettings = new CsCartSettings();
        ProductSettings productSettings = new ProductSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что галерея мини-иконок горизонтальная
        softAssert.assertFalse($(".ab-vg-vertical-thumbnails").exists(), "Gallery of mini-icons is not Horizontal!");

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side!");

        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Row!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1!");

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
        CsCartSettings csCartSettings = new CsCartSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on category page!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on category page!");

        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Row on category page!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on category page!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1 on category page!");

        takeScreenshot("2200 Category - HorizontalIcons, LeftRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("2202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on quick view window!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on quick view window!");

        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".ut2-pb__items .row-filling").exists(), "Position of stickers is not in Row on quick view window!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on quick view window!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1 on quick view window!");

        takeScreenshot("2205 QuickView - HorizontalIcons, LeftRow");
        stCategoryPage.button_CloseQuickView.hover().click();
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("2210 Category - HorizontalIcons, LeftRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");

        takeScreenshot("2215 Category - HorizontalIcons, LeftRow, CompactList");
        shiftLanguage("ar");
        takeScreenshot("2220 Category(RTL) - HorizontalIcons, LeftRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("2225 Category(RTL) - HorizontalIcons, LeftRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        waitForSpinnerDisappear();
        takeScreenshot("2230 Category(RTL) - HorizontalIcons, LeftRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("2232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
        takeScreenshot("2235 QuickView(RTL) - HorizontalIcons, LeftRow");
        stCategoryPage.button_CloseQuickView.hover().click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        waitForSpinnerDisappear();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(3000);

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on Wishlist page!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on Wishlist page!");

        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Row on Wishlist page!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on Wishlist page!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1 on Wishlist page!");

        stCategoryPage.productInList.hover();
        takeScreenshot("2250 WishList - HorizontalIcons, LeftRow");
        shiftLanguage("ar");
        Selenide.sleep(3000);
        stCategoryPage.productInList.hover();
        takeScreenshot("2255 WishList(RTL) - HorizontalIcons, LeftRow");
    }
}