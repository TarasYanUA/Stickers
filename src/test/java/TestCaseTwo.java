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
public class TestCaseTwo extends TestRunner {
    @Test(priority = 1)
    public void TestCaseTwo_ConfigureSettings() {
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
        stickerSettings.tab_Settings.click();
        stickerSettings.setting_OutputPosition.selectOptionByValue("L");
        stickerSettings.setting_OutputType_LeftTop.selectOptionByValue("row");
        stickerSettings.setting_MaxNumber_LeftTop.selectOptionByValue("3");
        stickerSettings.setting_OutputType_LeftBottom.selectOptionByValue("row");
        stickerSettings.setting_MaxNumber_LeftBottom.selectOptionByValue("3");
        stickerSettings.button_SaveSettings.click();

        //Настраиваем Комбинации формаций изображений галереи товара (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_Product.scrollIntoView(false).click();
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("2");
        csCartSettings.saveSettings();

        //Переходим на страницу редактирования товара
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.clickAndType_ProductSearch("Apple iPhone 14");
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        productSettings.productTemplate.selectOptionByValue("default_template");
        csCartSettings.navigateToStProductPage(1);
        csCartSettings.cookieNotice();
    }

    @Test(priority = 2)
    public void TestCaseTwo_ProductPage() {
        StProductPage stProductPage = new StProductPage();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();
        
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
        Selenide.sleep(2000);   //Паузы нужны, чтобы на скриншоте были видны стикеры
        Selenide.screenshot("2100 ProdPage - HorizontalIcons, LeftRow, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        Selenide.sleep(2000);
        Selenide.screenshot("2105 BlockPopular - HorizontalIcons, LeftRow, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Selenide.sleep(2000);
        Selenide.screenshot("2110 BlockHits - HorizontalIcons, LeftRow, AdvancedScroller");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("2115 ProdPage(RTL) - HorizontalIcons, LeftRow, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        $("div.ut2-gl__body.content-on-hover img.img-ab-hover-gallery").shouldBe(Condition.visible);
        Selenide.screenshot("2120 BlockPopular(RTL) - HorizontalIcons, LeftRow, GridWithButtonMore");
        stProductPage.block_Hits.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Selenide.sleep(2000);
        Selenide.screenshot("2125 BlockHits(RTL) - HorizontalIcons, LeftRow, AdvancedScroller");

        //Смотрим другие шаблоны страницы товара
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.shiftBrowserTab(0);
        ProductSettings productSettings = new ProductSettings();
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        csCartSettings.navigateToStProductPage(2);
        Selenide.sleep(2000);
        Selenide.screenshot("2130 ProdPage - HorizontalIcons, LeftRow, BigPictureTemplate");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("2135 ProdPage(RTL) - HorizontalIcons, LeftRow, BigPictureTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        csCartSettings.navigateToStProductPage(3);
        Selenide.sleep(2000);
        Selenide.screenshot("2140 ProdPage - HorizontalIcons, LeftRow, BigPictureFlatTemplate");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("2145 ProdPage(RTL) - HorizontalIcons, LeftRow, BigPictureFlatTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        csCartSettings.navigateToStProductPage(4);
        Selenide.sleep(2000);
        Selenide.screenshot("2150 ProdPage - HorizontalIcons, LeftRow, ThreeColumned");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("2155 ProdPage(RTL) - HorizontalIcons, LeftRow, ThreeColumned");
        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        csCartSettings.navigateToStProductPage(5);
        Selenide.sleep(2000);
        Selenide.screenshot("2160 ProdPage - HorizontalIcons, LeftRow, CascadeGallery f2");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("2165 ProdPage(RTL) - HorizontalIcons, LeftRow, CascadeGallery f2");
    }

    @Test(priority = 3)
    public void TestCaseTwo_CategoryPage(){
        StCategoryPage stCategoryPage = new StCategoryPage();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        shiftLanguage("ru");
        stCategoryPage.breadcrumbs_Phones.click();
        Selenide.sleep(2000);
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
        stCategoryPage.productInList.hover();
        Selenide.screenshot("2200 Category - HorizontalIcons, LeftRow, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        Selenide.sleep(2000);
        $(".ui-dialog-title").hover();
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
        Selenide.screenshot("2205 QuickView - HorizontalIcons, LeftRow");
        stCategoryPage.button_CloseQuickView.hover().click();
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("2210 Category - HorizontalIcons, LeftRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");
        Selenide.sleep(2000);
        Selenide.screenshot("2215 Category - HorizontalIcons, LeftRow, CompactList");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("2220 Category(RTL) - HorizontalIcons, LeftRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("2225 Category(RTL) - HorizontalIcons, LeftRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        Selenide.screenshot("2230 Category(RTL) - HorizontalIcons, LeftRow, Grid");
        stCategoryPage.button_QuickView.click();
        Selenide.sleep(2000);
        $(".ui-dialog-title").hover();
        Selenide.screenshot("2235 QuickView(RTL) - HorizontalIcons, LeftRow");
        stCategoryPage.button_CloseQuickView.hover().click();
    }

    @Test(priority = 4)
    public void TestCaseTwo_WishList(){
        StCategoryPage stCategoryPage = new StCategoryPage();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        shiftLanguage("ru");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(2000);
        ///Проверяем, что присутствуют стикеры слева и вверху
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
        Selenide.screenshot("2300 WishList - HorizontalIcons, LeftRow");
        shiftLanguage("ar");
        stCategoryPage.productInList.hover();
        Selenide.sleep(2000);
        Selenide.screenshot("2305 WishList(RTL) - HorizontalIcons, LeftRow");
    }
}