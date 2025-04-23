package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class StickerSettings {
    public StickerSettings() {
        super();
    }

    public SelenideElement sticker_Promotion = $("a[href*='sticker_id=22']");       //Стикер "Акция" (красный цвет)
    public SelenideElement sticker_SaleOrange = $("a[href*='sticker_id=7']");       //Стикер "Sale > 10% < 30%" (оранжевый цвет)
    public SelenideElement sticker_PopularProduct = $("a[href*='sticker_id=19']");  //Стикер "Популярный" (фиолетовый цвет)
    public SelenideElement sticker_TopRated = $("a[href*='sticker_id=21']");        //Стикер "Высокий рейтинг" (оранжевый цвет)
    public SelenideElement sticker_Free_Delivery = $("a[href*='sticker_id=26']");   //Стикер "Бесплатная доставка" (цвет сине-белый)
    public SelenideElement sticker_Weight = $("a[href*='sticker_id=29']");          //Стикер "Вес" (цвет серый)

    public SelenideElement tab_Settings = $("#settings");
    public SelenideElement tab_Pictograms = $("#ab__stickers_pictograms");
    public SelenideElement tab_Conditions = $("#conditions");
    SelenideElement tab_Display = $("#display");

    public SelenideElement setting_OutputPosition = $("select[id*='addon_option_ab__stickers_output_position']");
    public SelenideElement setting_OutputType_LeftTop = $("#ab__stickers_TL");
    public SelenideElement setting_MaxNumber_LeftTop = $("#ab__stickers_TL_max_count");
    public SelenideElement setting_OutputType_LeftBottom = $("#ab__stickers_BL");
    public SelenideElement setting_MaxNumber_LeftBottom = $("#ab__stickers_BL_max_count");
    public SelenideElement setting_OutputType_RightTop = $("#ab__stickers_TR");
    public SelenideElement setting_MaxNumber_RightTop = $("#ab__stickers_TR_max_count");
    public SelenideElement setting_OutputType_RightBottom = $("#ab__stickers_BR");
    public SelenideElement setting_MaxNumber_RightBottom = $("#ab__stickers_BR_max_count");
    public SelenideElement setting_AppearanceOfPictograms = $("select[id*=addon_option_ab__stickers_p_appearance]");

    public SelenideElement statusActive = $("input[id^='ab__stickers_status_'][id$='_a']");
    public SelenideElement button_SaveSettings = $(".cm-addons-save-settings");
    public SelenideElement button_SaveSticker = $(".cm-submit.btn-primary");
    public SelenideElement gearWheel = $("div.btn-group.dropleft");
    public SelenideElement generateStickerLinks = $(".cm-post.cm-comet");
    public SelenideElement abMenu_dropDownToggle = $(".ab__am-menu .btn.dropdown-toggle");
    public SelenideElement abMenu_StickerList = $(".ab__am-menu a[href*='ab__stickers.manage']");
    public SelenideElement tableOfConditions = $(".conditions-tree-node.clearfix");
    public SelenideElement button_DeleteCondition = $(".icon-trash");
    public SelenideElement button_AddCondition = $("div[id*='add_condition'] .btn");
    public SelenideElement fieldOfConditions = $("div[class='conditions-tree-node'] select");
    public SelenideElement fieldOfOperator = $("select[name*='sticker_data'][id*='sticker_condition_operator']");
    public SelenideElement fieldOfPriceCondition = $("input[name*='sticker_data'].input-medium");

    SelenideElement productPage_DisplayPlace = $(By.id("ab__stickers_display_place_detailed_page"));
    SelenideElement productPage_Position = $(By.id("ab__stickers_output_position_detailed_page"));
    SelenideElement productPage_Size = $(By.id("ab__stickers_display_on_detailed_page"));
    SelenideElement products_DisplayPlace = $(By.id("ab__stickers_display_place_blocks_products_products_tpl"));        //шаблон страницы категории "Список без опций"
    SelenideElement products_Position = $(By.id("ab__stickers_output_position_blocks_products_products_tpl"));
    SelenideElement products_Size = $(By.id("ab__stickers_display_on_blocks_products_products_tpl"));
    SelenideElement grid_DisplayPlace = $(By.id("ab__stickers_display_place_blocks_products_products_multicolumns_tpl"));
    SelenideElement grid_Position = $(By.id("ab__stickers_output_position_blocks_products_products_multicolumns_tpl"));
    SelenideElement grid_Size = $(By.id("ab__stickers_display_on_blocks_products_products_multicolumns_tpl"));
    SelenideElement shortList_DisplayPlace = $(By.id("ab__stickers_display_place_blocks_products_short_list_tpl"));     //шаблон страницы категории "Компактный список"
    SelenideElement shortList_Position = $(By.id("ab__stickers_output_position_blocks_products_short_list_tpl"));
    SelenideElement shortList_Size = $(By.id("ab__stickers_display_on_blocks_products_short_list_tpl"));
    SelenideElement gridMore_DisplayPlace = $(By.id("ab__stickers_display_place_blocks_products_ab__grid_list_tpl"));
    SelenideElement gridMore_Position = $(By.id("ab__stickers_output_position_blocks_products_ab__grid_list_tpl"));
    SelenideElement gridMore_Size = $(By.id("ab__stickers_display_on_blocks_products_ab__grid_list_tpl"));
    SelenideElement scrollerAdvanced_DisplayPlace = $(By.id("ab__stickers_display_place_blocks_products_products_scroller_advanced_tpl"));
    SelenideElement scrollerAdvanced_Position = $(By.id("ab__stickers_output_position_blocks_products_products_scroller_advanced_tpl"));
    SelenideElement scrollerAdvanced_Size = $(By.id("ab__stickers_display_on_blocks_products_products_scroller_advanced_tpl"));
    SelenideElement extendedPromotions_DisplayPlace = $(By.id("ab__stickers_display_place_addons_ab__deal_of_the_day_blocks_ab__deal_of_the_day_tpl"));
    SelenideElement extendedPromotions_Size = $(By.id("ab__stickers_display_on_addons_ab__deal_of_the_day_blocks_ab__deal_of_the_day_tpl"));


    public void clickAndType_PriceCondition(String value) {
        fieldOfPriceCondition.click();
        fieldOfPriceCondition.clear();
        fieldOfPriceCondition.sendKeys(value);
    }

    public void setSettingsAt_DisplayTab(String placeValue, String positionValue, String sizeValue) {
        executeJavaScript("window.scrollTo(0, 0);");
        tab_Display.click();
        productPage_DisplayPlace.selectOptionByValue(placeValue);
        if (productPage_Position.exists())
            productPage_Position.selectOptionByValue(positionValue);
        productPage_Size.selectOptionByValue(sizeValue);

        products_DisplayPlace.selectOptionByValue(placeValue);
        if (products_Position.exists())
            products_Position.selectOptionByValue(positionValue);
        products_Size.selectOptionByValue(sizeValue);

        grid_DisplayPlace.selectOptionByValue(placeValue);
        if (grid_Position.exists())
            grid_Position.selectOptionByValue(positionValue);
        grid_Size.selectOptionByValue(sizeValue);

        shortList_DisplayPlace.selectOptionByValue(placeValue);
        if (shortList_Position.exists())
            shortList_Position.selectOptionByValue(positionValue);
        shortList_Size.selectOptionByValue(sizeValue);

        gridMore_DisplayPlace.selectOptionByValue(placeValue);
        if (gridMore_Position.exists())
            gridMore_Position.selectOptionByValue(positionValue);
        gridMore_Size.selectOptionByValue(sizeValue);

        scrollerAdvanced_DisplayPlace.selectOptionByValue(placeValue);
        if (scrollerAdvanced_Position.exists())
            scrollerAdvanced_Position.selectOptionByValue(positionValue);
        scrollerAdvanced_Size.selectOptionByValue(sizeValue);

        extendedPromotions_DisplayPlace.selectOptionByValue(placeValue);
        extendedPromotions_Size.selectOptionByValue(sizeValue);
    }
}