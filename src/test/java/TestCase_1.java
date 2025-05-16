import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import storefront.StCategoryPage;
import storefront.StProductPage;

import static com.codeborne.selenide.Selenide.*;
import org.testng.asserts.SoftAssert;

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
    @Test(priority = 10)
    public void TestCase_1_ConfigureSettings() {

        CsCartSettings csCartSettings = new CsCartSettings();

        //Включаем мини-иконки в виде галереи и окно Быстрого просмотра
        csCartSettings.navigateTo_AppearanceSettings();
        if (!csCartSettings.settingMiniThumbnailAsGallery.isSelected()) {
            csCartSettings.settingMiniThumbnailAsGallery.click();
        }
        if (!csCartSettings.settingQuickView.isSelected()) {
            csCartSettings.settingQuickView.click();
        }
        Utils.saveSettings();

        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (!videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем позицию пиктограмм (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_ProductList.click();
        uniThemeSettings.fieldOfPictogramPosition_Grid.selectOptionByValue("position_1");
        uniThemeSettings.fieldOfPictogramPosition_ListWithoutOptions.selectOptionByValue("position_1");
        uniThemeSettings.fieldOfPictogramPosition_CompactList.selectOptionByValue("position_1");
        uniThemeSettings.tab_Product.hover().click();
        uniThemeSettings.fieldOfPictogramPosition_Product.selectOptionByValue("position_1");
        Utils.saveSettings();

        //Настраиваем блок с товарами
        LayoutSettings layoutSettings = csCartSettings.navigateToSection_Layouts();
        layoutSettings.layout_TabProducts.click();
        layoutSettings.openBlockProperties("Самые популярные");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/ab__grid_list.tpl");
        layoutSettings.setFillingForBlockContent("newest");
        layoutSettings.saveBlockProperties();
        layoutSettings.openBlockProperties("Распродажа");
        layoutSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_scroller_advanced.tpl");
        layoutSettings.saveBlockProperties();

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.tab_Settings.click();
        stickerSettings.setting_OutputPosition.selectOptionByValue("L");
        stickerSettings.setting_OutputType_LeftTop.selectOptionByValue("column");
        stickerSettings.setting_MaxNumber_LeftTop.selectOptionByValue("3");
        stickerSettings.setting_OutputType_LeftBottom.selectOptionByValue("column");
        stickerSettings.setting_MaxNumber_LeftBottom.selectOptionByValue("3");
        stickerSettings.button_SaveSettings.click();

        //Три верхних стикера
        csCartSettings.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerSettings.sticker_Promotion.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        stickerSettings.button_SaveSticker.click();
        //Стикер "Sale > 10% < 30%" (оранжевый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_SaleOrange.click();
        stickerSettings.statusActive.click();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        stickerSettings.button_SaveSticker.click();
        //Стикер "Популярный" (фиолетовый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_PopularProduct.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        stickerSettings.button_SaveSticker.click();
        stickerSettings.generateStickerLinks();

        //Три нижних стикера
        //Стикер "Высокий рейтинг" (оранжевый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_TopRated.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        stickerSettings.button_SaveSticker.click();
        //Стикер "Бесплатная доставка" (цвет сине-белый)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_Free_Delivery.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        stickerSettings.button_SaveSticker.click();
        stickerSettings.generateStickerLinks();
        //Стикер "Вес" (цвет серый)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_Weight.click();
        stickerSettings.statusActive.click();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        stickerSettings.button_SaveSticker.click();

        //Настраиваем страницу товара
        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        categorySettings.openCategoryPage("AB: Телефоны");
        categorySettings.activateCategoryAndSave();
        categorySettings.viewCategoryProducts();
        ProductSettings productSettings = new ProductSettings();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.statusActive_Product.click();
        productSettings.field_ListPrice.setValue("2000");
        productSettings.productTemplate.selectOptionByValue("default_template");
        productSettings.setValueTo_ProductWeight("9");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_1_ConfigureSettings")
    public void TestCase_1_ProductPage() {
        CsCartSettings csCartSettings = new CsCartSettings();
        ProductSettings productSettings = new ProductSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

        //Проверяем, что галерея мини-иконок вертикальная
        softAssert.assertTrue($(".ab-vg-vertical-thumbnails").exists(), "Gallery of mini-icons is not Vertical!");

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side!");

        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1!");

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
        CsCartSettings csCartSettings = new CsCartSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on category page!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on category page!");

        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column on category page!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on category page!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1 on category page!");

        takeScreenshot("1200 Category - VerticalIcons, LeftColumn, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("1202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();

        //Проверяем, что присутствуют стикеры слева и вверху
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side on quick view window!");

        //Проверяем, что присутствуют стикеры слева и внизу
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side on quick view window!");

        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".ut2-pb__items .column-filling").exists(), "Position of stickers is not in Column on quick view window!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on quick view window!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1 on quick view window!");

        takeScreenshot("1205 QuickView - VerticalIcons, LeftColumn");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();

        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as List without options!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as List without options!");

        waitForSpinnerDisappear();
        takeScreenshot("1210 Category - VerticalIcons, LeftColumn, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");

        takeScreenshot("1215 Category - VerticalIcons, LeftColumn, CompactList");
        shiftLanguage("ar");
        takeScreenshot("1220 Category(RTL) - VerticalIcons, LeftColumn, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("1225 Category(RTL) - VerticalIcons, LeftColumn, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        waitForSpinnerDisappear();
        takeScreenshot("1230 Category(RTL) - VerticalIcons, LeftColumn, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("1232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
        takeScreenshot("1235 QuickView(RTL) - VerticalIcons, LeftColumn");
        stCategoryPage.button_CloseQuickView.click();


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

        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column on Wishlist page!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on Wishlist page!");

        //Проверяем, что пиктограммы расположены в позиции 1
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1 on Wishlist page!");

        stCategoryPage.productInList.hover();
        takeScreenshot("1250 WishList - VerticalIcons, LeftColumn");
        shiftLanguage("ar");
        Selenide.sleep(3000);
        stCategoryPage.productInList.hover();
        takeScreenshot("1255 WishList(RTL) - VerticalIcons, LeftColumn");
    }

}