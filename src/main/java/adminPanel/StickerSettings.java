package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class StickerSettings {
    public StickerSettings(){super();}
    public SelenideElement sticker_Promotion = $("a[href*='sticker_id=11']");
    public SelenideElement sticker_SaleOrange = $("a[href*='sticker_id=14']");
    public SelenideElement sticker_PopularProduct = $("a[href*='sticker_id=18']");
    public SelenideElement sticker_TopRated = $("a[href*='sticker_id=10']");
    public SelenideElement sticker_Free_Delivery = $("a[href*='sticker_id=19']");
    public SelenideElement sticker_Weight = $("a[href*='sticker_id=20']");

    public SelenideElement tab_Settings = $("#settings");
    public SelenideElement tab_Pictograms = $("#ab__stickers_pictograms");
    public SelenideElement tab_Conditions = $("#conditions");
    public SelenideElement tab_DisplayOn = $("#display_on");
    public SelenideElement setting_OutputPosition = $("select[id*='addon_option_ab__stickers_output_position']");
    public SelenideElement setting_OutputType_LeftTop = $("#ab__stickers_TL");
    public SelenideElement setting_MaxNumber_LeftTop = $("#ab__stickers_TL_max_count");
    public SelenideElement setting_OutputType_LeftBottom = $("#ab__stickers_BL");
    public SelenideElement setting_MaxNumber_LeftBottom = $("#ab__stickers_BL_max_count");
    public SelenideElement setting_OutputType_RightTop = $("#ab__stickers_TR");
    public SelenideElement setting_MaxNumber_RightTop = $("#ab__stickers_TR_max_count");
    public SelenideElement setting_OutputType_RightBottom = $("#ab__stickers_BR");
    public SelenideElement setting_MaxNumber_RightBottom = $("#ab__stickers_BR_max_count");
    public SelenideElement setting_PositionsInProductLists = $("#ab__stickers_output_position_list");
    public SelenideElement setting_PositionsOnProductPage = $("#ab__stickers_output_position_detailed_page");
    public SelenideElement setting_AppearanceOfPictograms = $("select[id*=addon_option_ab__stickers_p_appearance]");

    public SelenideElement displayOn_ShortList = $("select[id='ab__stickers_display_on_blocks_products_short_list_tpl']");
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

    public void clickAndType_PriceCondition(String value){
        fieldOfPriceCondition.click();
        fieldOfPriceCondition.clear();
        fieldOfPriceCondition.sendKeys(value);
    }
}