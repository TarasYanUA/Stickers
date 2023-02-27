package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.Select;
import static com.codeborne.selenide.Selenide.$;

public class StickerSettings {
    public StickerSettings(){super();}
    public SelenideElement sticker_Promotion = $("a[href*='sticker_id=11']");
    public SelenideElement sticker_SaleOrange = $("a[href*='sticker_id=14']");
    public SelenideElement sticker_PopularProduct = $("a[href*='sticker_id=18']");
    public SelenideElement sticker_TopRated = $("a[href*='sticker_id=10']");
    public SelenideElement sticker_Free_Delivery = $("a[href*='sticker_id=19']");
    public SelenideElement sticker_Weight = $("a[href*='sticker_id=20']");

    public SelenideElement tabSettings = $("#settings");
    public SelenideElement tabPictograms = $("#ab__stickers_pictograms");
    public SelenideElement tab_Conditions = $("#conditions");
    public SelenideElement tab_DisplayOn = $("#display_on");
    public SelenideElement settingOutputPosition = $("select[id*='addon_option_ab__stickers_output_position']");
    public SelenideElement setting_OutputType_LeftTop = $("#ab__stickers_TL");
    public SelenideElement setting_MaxNumber_LeftTop = $("#ab__stickers_TL_max_count");
    public SelenideElement setting_OutputType_LeftBottom = $("#ab__stickers_BL");
    public SelenideElement setting_MaxNumber_LeftBottom = $("#ab__stickers_BL_max_count");
    public SelenideElement setting_OutputType_RightTop = $("#ab__stickers_TR");
    public SelenideElement setting_MaxNumber_RightTop = $("#ab__stickers_TR_max_count");
    public SelenideElement setting_OutputType_RightBottom = $("#ab__stickers_BR");
    public SelenideElement setting_MaxNumber_RightBottom = $("#ab__stickers_BR_max_count");
    public SelenideElement settingPositionsInProductLists = $("#ab__stickers_output_position_list");
    public SelenideElement settingPositionsOnProductPage = $("#ab__stickers_output_position_detailed_page");
    public SelenideElement setting_AppearanceOfPictograms = $("select[id*=addon_option_ab__stickers_p_appearance]");

    public SelenideElement displayOn_ShortList = $("select[id='ab__stickers_display_on_blocks_products_short_list_tpl']");
    public SelenideElement statusActive = $("input[id^='ab__stickers_status_'][id$='_a']");
    public SelenideElement buttonSaveSettings = $(".cm-addons-save-settings");
    public SelenideElement buttonSaveSticker = $(".cm-submit.btn-primary");
    public SelenideElement gearWheel = $("div.btn-group.dropleft");
    public SelenideElement generateStickerLinks = $(".cm-post.cm-comet");
    public SelenideElement dropDownToggle = $("#last_edited_items .btn.dropdown-toggle");
    public SelenideElement toggleStickerList = $("#last_edited_items a[href*='ab__stickers.manage']");
    public SelenideElement tableOfConditions = $(".conditions-tree-node.clearfix");
    public SelenideElement button_DeleteCondition = $(".icon-trash");
    public SelenideElement button_AddCondition = $("div[id*='add_condition'] .btn");
    public SelenideElement fieldOfConditions = $("div[class='conditions-tree-node'] select");
    public SelenideElement fieldOfOperator = $("select[name*='sticker_data'][id*='sticker_condition_operator']");
    public SelenideElement fieldOfPriceCondition = $("input[name*='sticker_data'].input-medium");



    public Select getSettingOutputPosition(){return new Select(settingOutputPosition);}
    public void selectSettingOutputPosition(String value){
        getSettingOutputPosition().selectByValue(value);
    }
    public Select getSetting_OutputType_RightTop(){return new Select(setting_OutputType_RightTop);}
    public void selectSetting_OutputType_RightTop(String value){
        getSetting_OutputType_RightTop().selectByValue(value);
    }
    public Select getSetting_MaxNumber_RightTop(){return new Select(setting_MaxNumber_RightTop);}
    public void selectSetting_MaxNumber_RightTop(String value){

        getSetting_MaxNumber_RightTop().selectByValue(value);
    }
    public Select getSetting_MaxNumber_RightBottom(){return new Select(setting_MaxNumber_RightBottom);}
    public void selectSetting_MaxNumber_RightBottom(String value){
        getSetting_MaxNumber_RightBottom().selectByValue(value);
    }
    public Select getSetting_OutputType_RightBottom(){return new Select(setting_OutputType_RightBottom);}
    public void selectSetting_OutputType_RightBottom(String value){
        getSetting_OutputType_RightBottom().selectByValue(value);
    }
    public Select getSetting_OutputType_LeftTop(){return new Select(setting_OutputType_LeftTop);}
    public void selectSetting_OutputType_LeftTop(String value){
        getSetting_OutputType_LeftTop().selectByValue(value);
    }
    public Select getSetting_MaxNumber_LeftTop(){return new Select(setting_MaxNumber_LeftTop);}
    public void selectSetting_MaxNumber_LeftTop(String value){
        getSetting_MaxNumber_LeftTop().selectByValue(value);
    }

    public Select getSetting_MaxNumber_LeftBottom(){return new Select(setting_MaxNumber_LeftBottom);}
    public void selectSetting_MaxNumber_LeftBottom(String value){
        getSetting_MaxNumber_LeftBottom().selectByValue(value);
    }
    public Select getSetting_OutputType_LeftBottom(){return new Select(setting_OutputType_LeftBottom);}
    public void selectSetting_OutputType_LeftBottom(String value){
        getSetting_OutputType_LeftBottom().selectByValue(value);
    }
    public Select getSettingPositionsInProductLists(){
        return new Select(settingPositionsInProductLists);
    }
    public void selectSettingPositionsInProductLists(String value){
        getSettingPositionsInProductLists().selectByValue(value);
    }
    public Select getSettingPositionsOnProductPage(){
        return new Select(settingPositionsOnProductPage);
    }
    public void selectSettingPositionsOnProductPage(String value){
        getSettingPositionsOnProductPage().selectByValue(value);
    }
    public Select getStickerCondition(){return new Select(fieldOfConditions);}
    public void selectStickerCondition(String value){
        getStickerCondition().selectByValue(value);
    }
    public Select getStickerOperator(){return new Select(fieldOfOperator);}
    public void selectStickerOperator(String value){
        getStickerOperator().selectByValue(value);
    }
    public void clickAndType_PriceCondition(String value){
        fieldOfPriceCondition.click();
        fieldOfPriceCondition.clear();
        fieldOfPriceCondition.sendKeys(value);
    }
    public Select getDisplayOn_ShortList(){return new Select(displayOn_ShortList);}
    public void selectDisplayOn_ShortList(String value){
        getDisplayOn_ShortList().selectByValue(value);}
    public Select getSetting_AppearanceOfPictograms(){return new Select(setting_AppearanceOfPictograms);}
    public void selectSetting_AppearanceOfPictograms(String value){getSetting_AppearanceOfPictograms().selectByValue(value);}
}