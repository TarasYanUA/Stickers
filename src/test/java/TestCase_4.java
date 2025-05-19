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

public class TestCase_4 extends TestRunner {
    @Test(priority = 10)
    public void TestCase_4_ConfigureSettings() {
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
        stickerSettings.configureStickerSettings("R", "row", "3");
        stickerSettings.configureAppearanceOfPictograms("teardrop");

        //Настраиваем Комбинации формаций изображений галереи товара (тема Uni2)
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.goToProductTab();
        uniThemeSettings.setting_CombinationsOfProductGalleryImageFormations.selectOptionByValue("1");
        Utils.saveSettings();

        //Переходим на страницу редактирования товара
        ProductSettings productSettings = csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        productSettings.productTemplate.selectOptionByValue("default_template");
        Utils.saveSettings();
    }

    @Test(priority = 20, dependsOnMethods = "TestCase_4_ConfigureSettings")
    public void TestCase_4_ProductPage() {
        CsCartSettings csCartSettings = new CsCartSettings();
        ProductSettings productSettings = new ProductSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        csCartSettings.navigateToSection_Products();
        productSettings.selectProductByName("Apple iPhone 14");
        StProductPage stProductPage = productSettings.navigateTo_StProductPage(1);

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
        CsCartSettings csCartSettings = new CsCartSettings();
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        CategorySettings categorySettings = csCartSettings.navigateToSection_Categories();
        $x("//a[text()='AB: Телефоны']").click();
        StCategoryPage stCategoryPage = categorySettings.navigateTo_StCategoryPage(1);
      
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

        takeScreenshot("4200 Category - VerticalIcons, RightRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("4202 Category - Pictograms, Grid");

        //Смотрим окно Быстрого просмотра
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
      
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
      
        takeScreenshot("4205 QuickView - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.click();

        //Смотрим другие шаблона страницы категории
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("4210 Category - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        waitForSpinnerDisappear();

        //Проверяем, что стикеры присутствуют
        softAssert.assertTrue($(".ab-stickers-container").exists(), "There is no stickers on category page as Compact list!");

        //Проверяем, что пиктограммы присутствуют
        softAssert.assertTrue($(".ab-s-pictograms-wrapper").exists(), "There is no pictograms on category page as Compact list!");

        takeScreenshot("4215 Category - VerticalIcons, RightRow, CompactList");
        shiftLanguage("ar");
        takeScreenshot("4220 Category(RTL) - VerticalIcons, RightRow, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        waitForSpinnerDisappear();
        takeScreenshot("4225 Category(RTL) - VerticalIcons, RightRow, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        waitForSpinnerDisappear();
        takeScreenshot("4230 Category(RTL) - VerticalIcons, RightRow, Grid");
        stCategoryPage.productInList.hover();
        takeScreenshot("4232 Category(RTL) - Pictograms, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.hover().click();
        waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
        takeScreenshot("4235 QuickView(RTL) - VerticalIcons, RightRow");
        stCategoryPage.button_CloseQuickView.hover().click();


        //Работаем на странице Избранных товаров
        shiftLanguage("ru");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_AddToWishList.click();
        waitForSpinnerDisappear();
        stCategoryPage.button_CloseWishListPopup.shouldBe(Condition.visible).click();
        stCategoryPage.button_WishListOnTop.click();
        Selenide.sleep(3000);

        //Проверяем, что присутствуют стикеры справа и вверху
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
        takeScreenshot("4250 WishList - VerticalIcons, RightRow");
        shiftLanguage("ar");
        Selenide.sleep(3000);
        stCategoryPage.productInList.hover();
        takeScreenshot("4255 WishList(RTL) - VerticalIcons, RightRow");
    }
}