import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.Assert;
import org.testng.annotations.Test;
import storefront.StCategoryPage;
import storefront.StProductPage;

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
    @Test(priority=1)
    public void TestCaseFour_ConfigureSettings() {
        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToAddonsPage();
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (!videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем настройки модуля "Стикеры"
        csCartSettings.navigateToAddonsPage();
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.tabSettings.click();
        stickerSettings.selectSettingOutputPosition("R");
        stickerSettings.selectSetting_OutputType_RightTop("row");
        stickerSettings.selectSetting_MaxNumber_RightTop("3");
        stickerSettings.selectSetting_OutputType_RightBottom("row");
        stickerSettings.selectSetting_MaxNumber_RightBottom("3");
        stickerSettings.tabPictograms.click();
        stickerSettings.selectSetting_AppearanceOfPictograms("teardrop");
        stickerSettings.buttonSaveSettings.click();

        //Переходим на страницу редактирования товара
        ProductSettings productSettings = csCartSettings.navigateToEditingProductPage();
        productSettings.clickAndType_ProductSearch("Apple iPhone 14");
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        productSettings.selectProductTemplate("default_template");
        csCartSettings.navigateToStProductPage(1);
        csCartSettings.cookieNotice();
    }

    @Test(priority=2)
    public void TestCaseFour_ProductPage() {
        StProductPage stProductPage = new StProductPage();
        //Проверяем, что галерея мини-иконок вертикальная
        Assert.assertTrue($(".ab-vg-vertical-thumbnails").exists(), "Gallery of mini-icons is not Horizontal!");
        //Проверяем, что присутствуют стикеры справа и вверху
        Assert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side!");
        //Проверяем, что присутствуют стикеры справа и внизу
        Assert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side!");
        //Проверяем, что стикеры расположены в строку
        Assert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Column!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the product page!");
        //Проверяем, что пиктограммы расположены в позиции 2
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2!");
        Selenide.sleep(2000);   //Паузы нужны, чтобы на скриншоте были видны стикеры
        Selenide.screenshot("4100 ProdPage - VerticalIcons, RightRow, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        Selenide.sleep(2000);
        Selenide.screenshot("4105 BlockPopular - VerticalIcons, RightRow, LightScroller");
        stProductPage.block_Hits.click();
        Selenide.sleep(2000);
        Selenide.screenshot("4110 BlockHits - VerticalIcons, RightRow, Grid");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("4115 ProdPage(RTL) - VerticalIcons, RightRow, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        $("div.ut2-gl__body.content-on-hover img.img-ab-hover-gallery").shouldBe(Condition.visible);
        Selenide.screenshot("4120 BlockPopular(RTL) - VerticalIcons, RightRow, LightScroller");
        stProductPage.block_Hits.click();
        Selenide.screenshot("4125 BlockHits(RTL) - VerticalIcons, RightRow, Grid");

        //Смотрим другие шаблоны страницы товара
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.shiftBrowserTab(0);
        ProductSettings productSettings = new ProductSettings();
        productSettings.tab_General.hover().click();
        productSettings.selectProductTemplate("bigpicture_template");
        csCartSettings.navigateToStProductPage(2);
        Selenide.sleep(2000);
        Selenide.screenshot("4130 ProdPage - VerticalIcons, RightRow, BigPictureTemplate");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("4135 ProdPage(RTL) - VerticalIcons, RightRow, BigPictureTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.selectProductTemplate("abt__ut2_bigpicture_flat_template");
        csCartSettings.navigateToStProductPage(3);
        Selenide.sleep(2000);
        Selenide.screenshot("4140 ProdPage - VerticalIcons, RightRow, BigPictureFlatTemplate");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("4145 ProdPage(RTL) - VerticalIcons, RightRow, BigPictureFlatTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.selectProductTemplate("abt__ut2_three_columns_template");
        csCartSettings.navigateToStProductPage(4);
        Selenide.sleep(2000);
        Selenide.screenshot("4150 ProdPage - VerticalIcons, RightRow, ThreeColumned");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("4155 ProdPage(RTL) - VerticalIcons, RightRow, ThreeColumned");
    }

    @Test(priority=3)
    public void TestCaseFour_CategoryPage(){
        selectLanguage_RU();
        StCategoryPage stCategoryPage = new StCategoryPage();
        stCategoryPage.breadcrumbs_Phones.click();
        Selenide.sleep(2000);
        //Проверяем, что присутствуют стикеры справа и вверху
        Assert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on category page!");
        //Проверяем, что присутствуют стикеры справа и внизу
        Assert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on category page!");
        //Проверяем, что стикеры расположены в строку
        Assert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Column on category page!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on category page!");
        //Проверяем, что пиктограммы расположены в позиции 2
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on category page!");
        Selenide.screenshot("4200 Category - VerticalIcons, RightRow, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        $(".ui-dialog-title").hover();
        Selenide.sleep(3000);
        //Проверяем, что присутствуют стикеры справа и вверху
        Assert.assertTrue($(".ut2-pb__items .ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on quick view window!");
        //Проверяем, что присутствуют стикеры справа и внизу
        Assert.assertTrue($(".ut2-pb__items .ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on quick view window!");
        //Проверяем, что стикеры расположены в строку
        Assert.assertTrue($(".ut2-pb__items .row-filling").exists(), "Position of stickers is not in Column on quick view window!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ut2-pb__right .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on quick view window!");
        //Проверяем, что пиктограммы расположены в позиции 2
        Assert.assertTrue($(".ut2-pb__right .ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on quick view window!");
        Selenide.screenshot("4205 QuickView - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.click();
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("4210 Category - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        //Проверяем, что стикеры присутствуют
        Assert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");
        Selenide.sleep(2000);
        Selenide.screenshot("4215 Category - VerticalIcons, RightRow, CompactList");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("4220 Category(RTL) - VerticalIcons, RightRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("4225 Category(RTL) - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Selenide.sleep(2000);
        Selenide.screenshot("4230 Category(RTL) - VerticalIcons, RightRow, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        $(".ui-dialog-title").hover();
        Selenide.sleep(3000);
        Selenide.screenshot("4235 QuickView(RTL) - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.click();
    }

    @Test(priority=4)
    public void TestCaseFour_WishList(){
        selectLanguage_RU();
        StCategoryPage stCategoryPage = new StCategoryPage();
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(2000);
        ///Проверяем, что присутствуют стикеры справа и вверху
        Assert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on Wishlist page!");
        //Проверяем, что присутствуют стикеры справа и внизу
        Assert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on Wishlist page!");
        //Проверяем, что стикеры расположены в строку
        Assert.assertTrue($(".row-filling").exists(), "Position of stickers is not in Column on Wishlist page!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on Wishlist page!");
        //Проверяем, что пиктограммы расположены в позиции 2
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on Wishlist page!");
        stCategoryPage.productInList.hover();
        Selenide.screenshot("4300 WishList - VerticalIcons, RightRow");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("4305 WishList(RTL) - VerticalIcons, RightRow");
    }
}