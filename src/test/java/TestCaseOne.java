import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.Assert;
import org.testng.annotations.Test;
import storefront.StHomePage;

import static com.codeborne.selenide.Selenide.*;

/*
ссылка на тест-кейс 1: https://docs.google.com/spreadsheets/d/1UdXKRCHxD7XP7W3UzDN28ff10LyiJPbZKrdvZllpUCU/edit#gid=1582514111

*/
public class TestCaseOne extends TestRunner {
    /*@Test
    public void TestCaseOne_ConfigureSettings() {
        //Включаем мини-иконки в виде галереи и окно Быстрого просмотра
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.menuSettings.hover();
        csCartSettings.sectionAppearance.click();
        if (!csCartSettings.settingMiniThumbnailAsGallery.isSelected()) {
            csCartSettings.settingMiniThumbnailAsGallery.click();
        }
        if (!csCartSettings.settingQuickView.isSelected()) {
            csCartSettings.settingQuickView.click();
        }
        csCartSettings.button_Save.click();

        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        csCartSettings.navigateToAddonsPage();
        VideoGallerySettings videoGallerySettings = csCartSettings.navigateToVideoGalleryPage();
        videoGallerySettings.tabSettings.click();
        if (!videoGallerySettings.settingVerticalView.isSelected()) {
            videoGallerySettings.settingVerticalView.click();
            videoGallerySettings.buttonSaveVideoGallery.click();
        }

        //Настраиваем позицию пиктограмм
        csCartSettings.navigateToAddonsPage();
        UniThemeSettings uniThemeSettings = csCartSettings.navigateToUniThemeSettings();
        uniThemeSettings.tab_ProductList.click();
        uniThemeSettings.selectPictogramPosition_Grid("position_1");
        uniThemeSettings.selectPictogramPosition_ListWithoutOptions("position_1");
        uniThemeSettings.selectPictogramPosition_CompactList("position_1");
        uniThemeSettings.tab_Product.hover().click();
        uniThemeSettings.selectPictogramPosition_Product("position_1");
        csCartSettings.button_Save.click();

        //Настраиваем настройки модуля "Стикеры"
        csCartSettings.navigateToAddonsPage();
        StickerSettings stickerSettings = csCartSettings.navigateToStickerSettingsPage();
        stickerSettings.tabSettings.click();
        stickerSettings.selectSettingOutputPosition("L");
        stickerSettings.selectSettingOutputTypeTop("column");
        stickerSettings.selectSettingMaxNumberTop("3");
        stickerSettings.selectSettingOutputTypeBottom("column");
        stickerSettings.selectSettingMaxNumberBottom("3");
        stickerSettings.buttonSaveSettings.click();
        //Три верхних стикера
        csCartSettings.navigateToAddonsPage();
        csCartSettings.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerSettings.sticker_Promotion.click();
        stickerSettings.selectSettingPositionsInProductLists("T");
        stickerSettings.selectSettingPositionsOnProductPage("T");
        stickerSettings.statusActive.click();
        addConditionOfPrice(stickerSettings);
        stickerSettings.buttonSaveSticker.click();
        //Стикер "Sale > 10% < 30%" (оранжевый цвет)
        stickerSettings.dropDownToggle.click();
        stickerSettings.toggleStickerList.click();
        stickerSettings.sticker_SaleOrange.click();
        stickerSettings.selectSettingPositionsInProductLists("T");
        stickerSettings.selectSettingPositionsOnProductPage("T");
        stickerSettings.statusActive.click();
        stickerSettings.buttonSaveSticker.click();
        //Стикер "Популярный" (фиолетовый цвет)
        stickerSettings.dropDownToggle.click();
        stickerSettings.toggleStickerList.click();
        stickerSettings.sticker_PopularProduct.click();
        stickerSettings.selectSettingPositionsInProductLists("T");
        stickerSettings.selectSettingPositionsOnProductPage("T");
        stickerSettings.statusActive.click();
        addConditionOfPrice(stickerSettings);
        stickerSettings.buttonSaveSticker.click();
        stickerSettings.gearWheel.click();
        stickerSettings.generateStickerLinks.click();
        Selenide.sleep(2000);
        //Три нижних стикера
        //Стикер "Высокий рейтинг" (оранжевый цвет)
        stickerSettings.dropDownToggle.shouldBe(Condition.interactable).click();
        stickerSettings.toggleStickerList.click();
        stickerSettings.sticker_TopRated.click();
        stickerSettings.selectSettingPositionsInProductLists("B");
        stickerSettings.selectSettingPositionsOnProductPage("B");
        stickerSettings.statusActive.click();
        addConditionOfPrice(stickerSettings);
        stickerSettings.buttonSaveSticker.click();
        //Стикер "Бесплатная доставка" (цвет сине-белый)
        stickerSettings.dropDownToggle.click();
        stickerSettings.toggleStickerList.click();
        stickerSettings.sticker_Free_Delivery.click();
        stickerSettings.selectSettingPositionsInProductLists("B");
        stickerSettings.selectSettingPositionsOnProductPage("B");
        stickerSettings.statusActive.click();
        addConditionOfPrice(stickerSettings);
        stickerSettings.buttonSaveSticker.click();
        stickerSettings.gearWheel.click();
        stickerSettings.generateStickerLinks.click();
        Selenide.sleep(2000);
        //Стикер "Вес" (цвет серый)
        stickerSettings.dropDownToggle.shouldBe(Condition.interactable).click();
        stickerSettings.toggleStickerList.click();
        stickerSettings.sticker_Weight.click();
        stickerSettings.selectSettingPositionsInProductLists("B");
        stickerSettings.selectSettingPositionsOnProductPage("B");
        stickerSettings.statusActive.click();
        stickerSettings.buttonSaveSticker.click();

        //Настраиваем блок с товарами
        csCartSettings.menuDesign.hover();
        csCartSettings.sectionLayouts.click();
        csCartSettings.layout_TabProducts.click();
        csCartSettings.layout_GearwheelOfBlockPopular.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.selectBlockTemplate("blocks/products/ab__grid_list.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();
        csCartSettings.layout_GearwheelOfBlockHits.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.selectBlockTemplate("blocks/products/products_scroller_advanced.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();

        //Настраиваем страницу товара
        csCartSettings.navigateToEditingCategoryPage();
        $x("//a[text()='AB: Телефоны']").click();
        csCartSettings.statusActive_Category.click();
        csCartSettings.button_Save.click();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_ViewProducts.click();
        ProductSettings productSettings = new ProductSettings();
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        productSettings.statusActive_Product.click();
        productSettings.field_ListPrice.click();
        productSettings.field_ListPrice.clear();
        productSettings.field_ListPrice.sendKeys("2000");
        productSettings.selectProductTemplate("default_template");
        productSettings.tab_Shippings.hover().click();
        productSettings.clickAndType_ProductWeight("9");
        csCartSettings.navigateToStorefront(1);
    }*/
    @Test
    public void TestCaseOne_Storefront(){
        //Это удалить после разработки
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToEditingCategoryPage();
        $x("//a[text()='AB: Телефоны']").click();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_ViewProducts.click();
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        csCartSettings.navigateToStorefront(1);


        //Работаем с витриной
        //Проверяем, что галерея мини-иконок вертикальная
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
        Assert.assertTrue($(".ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1!");
        Selenide.sleep(3000);   //Пауза нужна, чтобы на скриншоте были видны стикеры
        Selenide.screenshot("100 ProdPage - VerticalIcons, LeftTopColumn, DefaultTemplate");
        StHomePage stHomePage = new StHomePage();
        stHomePage.shiftLanguage(1);
        Selenide.sleep(2000);
        Selenide.screenshot("101 ProdPage(RTL) - VerticalIcons, LeftTopColumn, DefaultTemplate");
    }

    private static void addConditionOfPrice(StickerSettings stickerSettings) {
        stickerSettings.tab_Conditions.hover().click();
        if(stickerSettings.tableOfConditions.exists()){
            stickerSettings.tableOfConditions.hover();
            stickerSettings.button_DeleteCondition.click();
        }
        stickerSettings.button_AddCondition.shouldBe(Condition.interactable).click();
        stickerSettings.selectStickerCondition("price");
        stickerSettings.selectStickerOperator("gte");
        stickerSettings.clickAndType_PriceCondition("1400");
    }
}