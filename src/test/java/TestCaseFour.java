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

public class TestCaseFour extends TestRunner {
    @Test(priority = 1)
    public void TestCaseFour_ConfigureSettings() {
        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        CsCartSettings csCartSettings = new CsCartSettings();
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (!videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.tab_Settings.click();
        stickerSettings.setting_OutputPosition.selectOptionByValue("R");
        stickerSettings.setting_OutputType_RightTop.selectOptionByValue("row");
        stickerSettings.setting_MaxNumber_RightTop.selectOptionByValue("3");
        stickerSettings.setting_OutputType_RightBottom.selectOptionByValue("row");
        stickerSettings.setting_MaxNumber_RightBottom.selectOptionByValue("3");
        stickerSettings.tab_Pictograms.click();
        stickerSettings.setting_AppearanceOfPictograms.selectOptionByValue("teardrop");
        stickerSettings.button_SaveSettings.click();

        //Настраиваем Комбинации формаций изображений галереи товара (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_Product.scrollIntoView(false).click();
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("1");
        csCartSettings.button_Save.click();

        //Переходим на страницу редактирования товара
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.clickAndType_ProductSearch("Apple iPhone 14");
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        productSettings.productTemplate.selectOptionByValue("default_template");
        csCartSettings.navigateToStProductPage(1);
        csCartSettings.cookieNotice();
    }

    @Test(priority = 2)
    public void TestCaseFour_ProductPage() {
        StProductPage stProductPage = new StProductPage();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        //Проверяем, что галерея мини-иконок вертикальная
        softAssert.assertTrue($(".ab-vg-vertical-thumbnails").exists(), "Gallery of mini-icons is not Horizontal!");
        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side!");
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side!");
        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Column!");
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the product page!");
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2!");
        Selenide.sleep(2000);   //Паузы нужны, чтобы на скриншоте были видны стикеры
        Selenide.screenshot("4100 ProdPage - VerticalIcons, RightRow, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        Selenide.sleep(2000);
        Selenide.screenshot("4105 BlockPopular - VerticalIcons, RightRow, LightScroller");
        stProductPage.block_Hits.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Selenide.sleep(2000);
        Selenide.screenshot("4110 BlockHits - VerticalIcons, RightRow, Grid");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("4115 ProdPage(RTL) - VerticalIcons, RightRow, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        $("div.ut2-gl__body.content-on-hover img.img-ab-hover-gallery").shouldBe(Condition.visible);
        Selenide.screenshot("4120 BlockPopular(RTL) - VerticalIcons, RightRow, LightScroller");
        stProductPage.block_Hits.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").click();
        Selenide.screenshot("4125 BlockHits(RTL) - VerticalIcons, RightRow, Grid");

        //Смотрим другие шаблоны страницы товара
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.shiftBrowserTab(0);
        ProductSettings productSettings = new ProductSettings();
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        csCartSettings.navigateToStProductPage(2);
        Selenide.sleep(2000);
        Selenide.screenshot("4130 ProdPage - VerticalIcons, RightRow, BigPictureTemplate");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("4135 ProdPage(RTL) - VerticalIcons, RightRow, BigPictureTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        csCartSettings.navigateToStProductPage(3);
        Selenide.sleep(2000);
        Selenide.screenshot("4140 ProdPage - VerticalIcons, RightRow, BigPictureFlatTemplate");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("4145 ProdPage(RTL) - VerticalIcons, RightRow, BigPictureFlatTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        csCartSettings.navigateToStProductPage(4);
        Selenide.sleep(2000);
        Selenide.screenshot("4150 ProdPage - VerticalIcons, RightRow, ThreeColumned");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("4155 ProdPage(RTL) - VerticalIcons, RightRow, ThreeColumned");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        csCartSettings.navigateToStProductPage(5);
        Selenide.sleep(2000);
        Selenide.screenshot("4160 ProdPage - VerticalIcons, RightRow, CascadeGallery f1");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("4165 ProdPage(RTL) - VerticalIcons, RightRow, CascadeGallery f1");
    }

    @Test(priority = 3)
    public void TestCaseFour_CategoryPage(){
        StCategoryPage stCategoryPage = new StCategoryPage();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        shiftLanguage("ru");
        stCategoryPage.breadcrumbs_Phones.click();
        Selenide.sleep(2000);
        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on category page!");
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on category page!");
        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Column on category page!");
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on category page!");
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on category page!");
        stCategoryPage.productInList.hover();
        Selenide.screenshot("4200 Category - VerticalIcons, RightRow, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        Selenide.sleep(2000);
        $(".ui-dialog-title").hover();
        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on quick view window!");
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on quick view window!");
        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".ut2-pb__items .row-filling").exists(), "Position of stickers is not in Column on quick view window!");
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on quick view window!");
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on quick view window!");
        Selenide.screenshot("4205 QuickView - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.click();
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("4210 Category - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");
        Selenide.sleep(2000);
        Selenide.screenshot("4215 Category - VerticalIcons, RightRow, CompactList");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        Selenide.screenshot("4220 Category(RTL) - VerticalIcons, RightRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("4225 Category(RTL) - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        Selenide.screenshot("4230 Category(RTL) - VerticalIcons, RightRow, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        Selenide.sleep(2000);
        $(".ui-dialog-title").hover();
        Selenide.screenshot("4235 QuickView(RTL) - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.hover().click();
    }

    @Test(priority = 4)
    public void TestCaseFour_WishList(){
        StCategoryPage stCategoryPage = new StCategoryPage();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        shiftLanguage("ru");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(2000);
        ///Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on Wishlist page!");
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on Wishlist page!");
        //Проверяем, что стикеры расположены в строку
        softAssert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Column on Wishlist page!");
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on Wishlist page!");
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on Wishlist page!");
        stCategoryPage.productInList.hover();
        Selenide.screenshot("4300 WishList - VerticalIcons, RightRow");
        shiftLanguage("ar");
        Selenide.sleep(2000);
        stCategoryPage.productInList.hover();
        Selenide.screenshot("4305 WishList(RTL) - VerticalIcons, RightRow");
    }
}