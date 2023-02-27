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
public class TestCaseThree extends TestRunner {
    @Test(priority=1)
    public void TestCaseThree_ConfigureSettings() {
        //Включаем Горизонтальное отображение мини-иконок (Модуль "Видео галерея")
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToAddonsPage();
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем позицию пиктограмм
        csCartSettings.navigateToAddonsPage();
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_ProductList.click();
        uniThemeSettings.selectPictogramPosition_Grid("position_2");
        uniThemeSettings.selectPictogramPosition_ListWithoutOptions("position_2");
        uniThemeSettings.selectPictogramPosition_CompactList("position_1"); //Позиции 2 у этого списка нет
        uniThemeSettings.tab_Product.hover().click();
        uniThemeSettings.selectPictogramPosition_Product("position_2");
        csCartSettings.button_Save.click();

        //Настраиваем блок с товарами
        csCartSettings.menuDesign.hover();
        csCartSettings.sectionLayouts.click();
        csCartSettings.layout_TabProducts.click();
        csCartSettings.layout_GearwheelOfBlockPopular.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.selectBlockTemplate("blocks/products/products_native_scroller_advanced.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();
        csCartSettings.layout_GearwheelOfBlockHits.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.selectBlockTemplate("blocks/products/products_multicolumns.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();

        //Настраиваем настройки модуля "Стикеры"
        csCartSettings.navigateToAddonsPage();
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.tabSettings.click();
        stickerSettings.selectSettingOutputPosition("R");
        stickerSettings.selectSetting_OutputType_RightTop("column");
        stickerSettings.selectSetting_MaxNumber_RightTop("3");
        stickerSettings.selectSetting_OutputType_RightBottom("column");
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
    public void TestCaseThree_ProductPage() {
        StProductPage stProductPage = new StProductPage();
        //Проверяем, что галерея мини-иконок горизонтальная
        Assert.assertTrue($(".ab-vg-vertical-thumbnails").exists(), "Gallery of mini-icons is not Vertical!");
        //Проверяем, что присутствуют стикеры слева и вверху
        Assert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side!");
        //Проверяем, что присутствуют стикеры слева и внизу
        Assert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side!");
        //Проверяем, что стикеры расположены в колонку
        Assert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page!");
        //Проверяем, что пиктограммы расположены в позиции 1
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 1!");
        Selenide.sleep(2000);   //Паузы нужны, чтобы на скриншоте были видны стикеры
        Selenide.screenshot("1100 ProdPage - VerticalIcons, LeftTopColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        Selenide.sleep(2000);
        Selenide.screenshot("1105 BlockPopular - VerticalIcons, LeftTopColumn, GridWithButtonMore");
        stProductPage.block_Hits.click();
        Selenide.sleep(2000);
        Selenide.screenshot("1110 BlockHits - VerticalIcons, LeftTopColumn, AdvancedScroller");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("1115 ProdPage(RTL) - VerticalIcons, LeftTopColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        $("div.ut2-gl__body.content-on-hover img.img-ab-hover-gallery").shouldBe(Condition.visible);
        Selenide.screenshot("1120 BlockPopular(RTL) - VerticalIcons, LeftTopColumn, GridWithButtonMore");
        stProductPage.block_Hits.click();
        Selenide.screenshot("1125 BlockHits(RTL) - VerticalIcons, LeftTopColumn, AdvancedScroller");

        //Смотрим другие шаблоны страницы товара
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.shiftBrowserTab(0);
        ProductSettings productSettings = new ProductSettings();
        productSettings.tab_General.hover().click();
        productSettings.selectProductTemplate("bigpicture_template");
        csCartSettings.navigateToStProductPage(2);
        Selenide.sleep(2000);
        Selenide.screenshot("1130 ProdPage - VerticalIcons, LeftTopColumn, BigPictureTemplate");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("1135 ProdPage(RTL) - VerticalIcons, LeftTopColumn, BigPictureTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.selectProductTemplate("abt__ut2_bigpicture_flat_template");
        csCartSettings.navigateToStProductPage(3);
        Selenide.sleep(2000);
        Selenide.screenshot("1140 ProdPage - VerticalIcons, LeftTopColumn, BigPictureFlatTemplate");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("1145 ProdPage(RTL) - VerticalIcons, LeftTopColumn, BigPictureFlatTemplate");
        csCartSettings.shiftBrowserTab(0);
        productSettings.selectProductTemplate("abt__ut2_three_columns_template");
        csCartSettings.navigateToStProductPage(4);
        Selenide.sleep(2000);
        Selenide.screenshot("1150 ProdPage - VerticalIcons, LeftTopColumn, ThreeColumned");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("1155 ProdPage(RTL) - VerticalIcons, LeftTopColumn, ThreeColumned");
    }

    @Test(priority=3)
    public void TestCaseOne_CategoryPage(){
        selectLanguage_RU();
        StCategoryPage stCategoryPage = new StCategoryPage();
        stCategoryPage.breadcrumbs_Phones.click();
        Selenide.sleep(2000);
        //Проверяем, что присутствуют стикеры слева и вверху
        Assert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on category page!");
        //Проверяем, что присутствуют стикеры слева и внизу
        Assert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on category page!");
        //Проверяем, что стикеры расположены в колонку
        Assert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column on category page!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on category page!");
        //Проверяем, что пиктограммы расположены в позиции 1
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 1 on category page!");
        Selenide.screenshot("1200 Category - VerticalIcons, LeftTopColumn, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        $(".ui-dialog-title").hover();
        Selenide.sleep(3000);
        //Проверяем, что присутствуют стикеры слева и вверху
        Assert.assertTrue($(".ut2-pb__items .ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on quick view window!");
        //Проверяем, что присутствуют стикеры слева и внизу
        Assert.assertTrue($(".ut2-pb__items .ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on quick view window!");
        //Проверяем, что стикеры расположены в колонку
        Assert.assertTrue($(".ut2-pb__items .column-filling").exists(), "Position of stickers is not in Column on quick view window!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ut2-pb__right .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on quick view window!");
        //Проверяем, что пиктограммы расположены в позиции 1
        Assert.assertTrue($(".ut2-pb__right .ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 1 on quick view window!");
        Selenide.screenshot("1205 QuickView - VerticalIcons, LeftTopColumn");
        stCategoryPage.button_CloseQuickView.click();
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("1210 Category - VerticalIcons, LeftTopColumn, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        //Проверяем, что стикеры присутствуют
        Assert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");
        Selenide.sleep(2000);
        Selenide.screenshot("1215 Category - VerticalIcons, LeftTopColumn, CompactList");
        selectLanguage_RTL();
        Selenide.sleep(2000);
        Selenide.screenshot("1220 Category(RTL) - VerticalIcons, LeftTopColumn, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("1225 Category(RTL) - VerticalIcons, LeftTopColumn, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Selenide.sleep(2000);
        Selenide.screenshot("1230 Category(RTL) - VerticalIcons, LeftTopColumn, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        $(".ui-dialog-title").hover();
        Selenide.sleep(3000);
        Selenide.screenshot("1235 QuickView(RTL) - VerticalIcons, LeftTopColumn");
        stCategoryPage.button_CloseQuickView.click();
    }

    @Test(priority=4)
    public void TestCaseOne_WishListAndComparisonList(){
        selectLanguage_RU();
        StCategoryPage stCategoryPage = new StCategoryPage();
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(2000);
        ///Проверяем, что присутствуют стикеры слева и вверху
        Assert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on Wishlist page!");
        //Проверяем, что присутствуют стикеры слева и внизу
        Assert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on Wishlist page!");
        //Проверяем, что стикеры расположены в колонку
        Assert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column on Wishlist page!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on Wishlist page!");
        //Проверяем, что пиктограммы расположены в позиции 1
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 1 on Wishlist page!");
        stCategoryPage.productInList.hover();
        Selenide.screenshot("1300 WishList - VerticalIcons, LeftTopColumn");
        selectLanguage_RTL();
        Selenide.screenshot("1305 WishList(RTL) - VerticalIcons, LeftTopColumn");
    }
}