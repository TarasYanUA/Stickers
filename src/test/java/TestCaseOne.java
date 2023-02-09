import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.Assert;
import org.testng.annotations.Test;
import storefront.StCategoryPage;
import storefront.StProductPage;
import static com.codeborne.selenide.Selenide.*;

/*
ссылка на тест-кейс 1: https://docs.google.com/spreadsheets/d/1UdXKRCHxD7XP7W3UzDN28ff10LyiJPbZKrdvZllpUCU/edit#gid=1582514111

*/
public class TestCaseOne extends TestRunner {
    @Test(priority=1)
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

        //Настраиваем блок с товарами
        csCartSettings.menuDesign.hover();
        csCartSettings.sectionLayouts.click();
        csCartSettings.layout_TabProducts.click();
        csCartSettings.layout_GearwheelOfBlockPopular.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.selectBlockTemplate("blocks/products/ab__grid_list.tpl");
        csCartSettings.layoutBlock_TabContent.click();
        csCartSettings.selectLayout_FieldFilling();
        csCartSettings.clickAndType_Layout_FieldMaxLimit();
        csCartSettings.layout_ButtonSaveBlock.click();
        csCartSettings.layout_GearwheelOfBlockHits.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        csCartSettings.selectBlockTemplate("blocks/products/products_scroller_advanced.tpl");
        csCartSettings.layout_ButtonSaveBlock.click();

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
        stickerSettings.tab_DisplayOn.hover().click();
        stickerSettings.selectDisplayOn_ShortList("small_size");
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
        csCartSettings.navigateToStProductPage(1);
    }
    @Test(priority=2)
    public void TestCaseOne_Storefront_ProductPage() {
/*      //Это удалить после разработки
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToEditingCategoryPage();
        $x("//a[text()='AB: Телефоны']").click();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_ViewProducts.click();
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), 'Apple iPhone 14')]").click();
        csCartSettings.navigateToStProductPage(1);*/


        StProductPage stProductPage = new StProductPage();
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
        Selenide.sleep(2000);   //Паузы нужны, чтобы на скриншоте были видны стикеры
        Selenide.screenshot("100 ProdPage - VerticalIcons, LeftTopColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        $("div.ut2-gl__body.content-on-hover img.img-ab-hover-gallery").shouldBe(Condition.visible);
        Selenide.screenshot("110 BlockPopular - VerticalIcons, LeftTopColumn, GridWithButtonMore");
        stProductPage.block_Hits.click();
        Selenide.sleep(2000);
        Selenide.screenshot("120 BlockHits - VerticalIcons, LeftTopColumn, AdvancedScroller");
        shiftLanguage(1);
        Selenide.sleep(2000);
        Selenide.screenshot("130 ProdPage(RTL) - VerticalIcons, LeftTopColumn, DefaultTemplate");
        stProductPage.block_Popular.scrollTo();
        $("div.ut2-gl__body.content-on-hover img.img-ab-hover-gallery").shouldBe(Condition.visible);
        Selenide.screenshot("140 BlockPopular(RTL) - VerticalIcons, LeftTopColumn, GridWithButtonMore");
        stProductPage.block_Hits.click();
        Selenide.screenshot("150 BlockHits(RTL) - VerticalIcons, LeftTopColumn, AdvancedScroller");
        shiftLanguage(2);
        stProductPage.breadcrumbs_Phones.click();
        Selenide.sleep(2000);
    }
    @Test(priority=3)
    public void TestCaseOne_Storefront_CategoryPage(){
        //Работаем со страницей категории
        Selenide.screenshot("160 Category - VerticalIcons, LeftTopColumn, Grid");
        StCategoryPage stCategoryPage = new StCategoryPage();
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        Selenide.sleep(3000);
        $(".ui-dialog-title").hover();
        //Проверяем, что присутствуют стикеры слева и вверху
        Assert.assertTrue($(".ut2-pb__items .ab-stickers-container__TL").exists(), "There are no stickers on the Top-Left side!");
        //Проверяем, что присутствуют стикеры слева и внизу
        Assert.assertTrue($(".ut2-pb__items .ab-stickers-container__BL").exists(), "There are no stickers on the Bottom-Left side!");
        //Проверяем, что стикеры расположены в колонку
        Assert.assertTrue($(".ut2-pb__items .column-filling").exists(), "Position of stickers is not in Column!");
        //Проверяем, что пиктограммы присутствуют
        Assert.assertTrue($(".ut2-pb__right .ab-s-pictograms-wrapper").exists(), "There are no pictograms on the page!");
        //Проверяем, что пиктограммы расположены в позиции 1
        Assert.assertTrue($(".ut2-pb__right .ab-s-pictograms-wrapper-position_1").exists(), "Pictograms are not in Position 1!");
        Selenide.screenshot("170 QuickView - VerticalIcons, LeftTopColumn");
        stCategoryPage.button_CloseQuickView.click();
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("180 Category - VerticalIcons, LeftTopColumn, ListWithoutOptions");
        stCategoryPage.template_CompactList.click();
        Selenide.sleep(2000);
        Selenide.screenshot("190 Category - VerticalIcons, LeftTopColumn, CompactList");
        shiftLanguage(1);
        Selenide.sleep(2000);
        Selenide.screenshot("1100 Category(RTL) - VerticalIcons, LeftTopColumn, CompactList");
        stCategoryPage.template_ListWithoutOptions.click();
        Selenide.sleep(2000);
        Selenide.screenshot("1110 Category(RTL) - VerticalIcons, LeftTopColumn, ListWithoutOptions");
        stCategoryPage.template_Grid.click();
        Selenide.sleep(2000);
        Selenide.screenshot("1120 Category(RTL) - VerticalIcons, LeftTopColumn, Grid");
        stCategoryPage.productInList.hover();
        stCategoryPage.button_QuickView.click();
        Selenide.sleep(3000);
        $(".ui-dialog-title").hover();
        Selenide.screenshot("1130 QuickView(RTL) - VerticalIcons, LeftTopColumn");
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