import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import storefront.StCategoryPage;
import storefront.StProductPage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import org.testng.asserts.SoftAssert;

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
    @Test(priority = 10)
    public void TestCase_3_ConfigureSettings() {
        //Включаем Горизонтальное отображение мини-иконок (Модуль "Видео галерея")
        CsCartSettings csCartSettings = new CsCartSettings();
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем позицию пиктограмм (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_ProductList.click();
        uniThemeSettings.fieldOfPictogramPosition_Grid.selectOptionByValue("position_2");
        uniThemeSettings.fieldOfPictogramPosition_ListWithoutOptions.selectOptionByValue("position_2");
        uniThemeSettings.fieldOfPictogramPosition_CompactList.selectOptionByValue("position_1"); //Позиции 2 у этого списка нет
        uniThemeSettings.tab_Product.hover().click();
        uniThemeSettings.fieldOfPictogramPosition_Product.selectOptionByValue("position_2");
        //Настраиваем Комбинации формаций изображений галереи товара
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("3");
        csCartSettings.button_Save.click();

        //Настраиваем блок с товарами
        csCartSettings.navigateToSectionLayouts();
        csCartSettings.layout_TabProducts.click();
        csCartSettings.layout_GearwheelOfBlockPopular.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_native_scroller_advanced.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();
        csCartSettings.layout_GearwheelOfBlockHits.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.layout_BlockTemplate.selectOptionByValue("blocks/products/products_multicolumns.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();

        //Настраиваем настройки модуля "Стикеры"
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.tab_Settings.click();
        stickerSettings.setting_OutputPosition.selectOptionByValue("R");
        stickerSettings.setting_OutputType_RightTop.selectOptionByValue("column");
        stickerSettings.setting_MaxNumber_RightTop.selectOptionByValue("3");
        stickerSettings.setting_OutputType_RightBottom.selectOptionByValue("column");
        stickerSettings.setting_MaxNumber_RightBottom.selectOptionByValue("3");
        stickerSettings.tab_Pictograms.click();
        stickerSettings.setting_AppearanceOfPictograms.selectOptionByValue("teardrop");
        stickerSettings.button_SaveSettings.click();

        //Настраиваем страницу товара
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.clickAndType_ProductSearch("Apple iPhone 14");
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        productSettings.productTemplate.selectOptionByValue("default_template");
        csCartSettings.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_3_ConfigureSettings")
    public void TestCase_3_ProductPage() {
        CsCartSettings csCartSettings = new CsCartSettings();
        ProductSettings productSettings = new ProductSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Products();
        productSettings.clickAndType_ProductSearch("Apple iPhone 14");
        csCartSettings.chooseAnyProduct();
        StProductPage stProductPage = csCartSettings.navigateToStProductPage(1);

        //Проверяем, что галерея мини-иконок горизонтальная
        softAssert.assertFalse($(".ab-vg-vertical-thumbnails").exists(), "Gallery of mini-icons is not Horizontal!");
        
        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side!");
        
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side!");
        
        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column!");
        
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the product page!");
        
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2!");
        
        takeScreenshot("3100 ProdPage - HorizontalIcons, RightColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        takeScreenshot("3105 BlockPopular - HorizontalIcons, RightColumn, LightScroller");
        stProductPage.block_Hits.scrollIntoCenter().click();
        takeScreenshot("3110 BlockHits - HorizontalIcons, RightColumn, Grid");
        shiftLanguage("ar");
        takeScreenshot("3115 ProdPage(RTL) - HorizontalIcons, RightColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollIntoCenter();
        $(".ab-stickers-container__product_labels").shouldBe(Condition.visible);
        takeScreenshot("3120 BlockPopular(RTL) - HorizontalIcons, RightColumn, LightScroller");
        stProductPage.block_Hits.scrollIntoCenter().click();
        $(".ab-stickers-container__product_labels").shouldBe(Condition.visible);
        takeScreenshot("3125 BlockHits(RTL) - HorizontalIcons, RightColumn, Grid");

        //Смотрим другие шаблоны страницы товара
        csCartSettings.shiftBrowserTab(0);
        productSettings.tab_General.hover().click();
        productSettings.productTemplate.selectOptionByValue("bigpicture_template");
        csCartSettings.navigateToStProductPage(2);
        takeScreenshot("3130 ProdPage - HorizontalIcons, RightColumn, BigPictureTemplate");
        shiftLanguage("ar");
        takeScreenshot("3135 ProdPage(RTL) - HorizontalIcons, RightColumn, BigPictureTemplate");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_flat_template");
        csCartSettings.navigateToStProductPage(3);
        takeScreenshot("3140 ProdPage - HorizontalIcons, RightColumn, BigPictureFlatTemplate");
        shiftLanguage("ar");
        takeScreenshot("3145 ProdPage(RTL) - HorizontalIcons, RightColumn, BigPictureFlatTemplate");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_three_columns_template");
        csCartSettings.navigateToStProductPage(4);
        takeScreenshot("3150 ProdPage - HorizontalIcons, RightColumn, ThreeColumned");
        shiftLanguage("ar");
        takeScreenshot("3155 ProdPage(RTL) - HorizontalIcons, RightColumn, ThreeColumned");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_cascade_gallery_template");
        csCartSettings.navigateToStProductPage(5);
        takeScreenshot("3160 ProdPage - HorizontalIcons, RightColumn, CascadeGallery f3");
        shiftLanguage("ar");
        takeScreenshot("3165 ProdPage(RTL) - HorizontalIcons, RightColumn, CascadeGallery f3");

        csCartSettings.shiftBrowserTab(0);
        productSettings.productTemplate.selectOptionByValue("abt__ut2_bigpicture_gallery_template");
        csCartSettings.saveSettings();
        csCartSettings.navigateToStProductPage(6);
        takeScreenshot("3170 ProdPage - HorizontalIcons, RightColumn, Gallery");
        shiftLanguage("ar");
        takeScreenshot("3175 ProdPage(RTL) - HorizontalIcons, RightColumn, Gallery");
    }

    @Test(priority = 30, dependsOnMethods = "TestCase_3_ConfigureSettings")
    public void TestCase_3_CategoryPage(){
        CsCartSettings csCartSettings = new CsCartSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = csCartSettings.navigateToStCategoryPage(1);
        
        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on category page!");
        
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on category page!");
        
        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column on category page!");
        
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on category page!");
        
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on category page!");
   
        stCategoryPage.productInList.hover();
        takeScreenshot("3200 Category - HorizontalIcons, RightColumn, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();

        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on quick view window!");
      
        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ut2-pb__items .ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on quick view window!");
      
        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".ut2-pb__items .column-filling").exists(), "Position of stickers is not in Column on quick view window!");
      
        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on quick view window!");
      
        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ut2-pb__items .ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on quick view window!");
      
        takeScreenshot("3205 QuickView - HorizontalIcons, RightColumn");
        stCategoryPage.button_CloseQuickView.click();
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("3210 Category - HorizontalIcons, RightColumn, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");

        takeScreenshot("3215 Category - HorizontalIcons, RightColumn, CompactList");
        shiftLanguage("ar");
        takeScreenshot("3220 Category(RTL) - HorizontalIcons, RightColumn, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("3225 Category(RTL) - HorizontalIcons, RightColumn, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        waitForSpinnerDisappear();
        stCategoryPage.productInList.hover();
        takeScreenshot("3230 Category(RTL) - HorizontalIcons, RightColumn, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
        takeScreenshot("3235 QuickView(RTL) - HorizontalIcons, RightColumn");
        stCategoryPage.button_CloseQuickView.hover().click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(3000);

        //Проверяем, что присутствуют стикеры справа и вверху
        softAssert.assertTrue($(".ab-stickers-container__TR").exists(), "There are no stickers on the Top-Right side on Wishlist page!");

        //Проверяем, что присутствуют стикеры справа и внизу
        softAssert.assertTrue($(".ab-stickers-container__BR").exists(), "There are no stickers on the Bottom-Right side on Wishlist page!");

        //Проверяем, что стикеры расположены в колонку
        softAssert.assertTrue($(".column-filling").exists(), "Position of stickers is not in Column on Wishlist page!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page on Wishlist page!");

        //Проверяем, что пиктограммы расположены в позиции 2
        softAssert.assertTrue($(".ab-s-pictograms-wrapper-position_2").exists(), "Pictograms are not in Position 2 on Wishlist page!");

        stCategoryPage.productInList.hover();
        takeScreenshot("3250 WishList - HorizontalIcons, RightColumn");
        shiftLanguage("ar");
        Selenide.sleep(3000);
        stCategoryPage.productInList.hover();
        takeScreenshot("3255 WishList(RTL) - HorizontalIcons, RightColumn");
    }
}