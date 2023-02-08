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
    public SelenideElement settingOutputPosition = $("select[id*='addon_option_ab__stickers_output_position']");
    public SelenideElement settingOutputTypeTop = $("#ab__stickers_TL");
    public SelenideElement settingMaxNumberTop = $("#ab__stickers_TL_max_count");
    public SelenideElement settingOutputTypeBottom = $("#ab__stickers_BL");
    public SelenideElement settingMaxNumberBottom = $("#ab__stickers_BL_max_count");
    public SelenideElement settingPositionsInProductLists = $("#ab__stickers_output_position_list");
    public SelenideElement settingPositionsOnProductPage = $("#ab__stickers_output_position_detailed_page");
    public SelenideElement statusActive = $("input[id^='ab__stickers_status_'][id$='_a']");
    public SelenideElement buttonSaveSettings = $(".cm-addons-save-settings");
    public SelenideElement buttonSaveSticker = $(".cm-submit.btn-primary");
    public SelenideElement gearWheel = $("div.btn-group.dropleft");
    public SelenideElement generateStickerLinks = $(".cm-post.cm-comet");
    public SelenideElement dropDownToggle = $("#last_edited_items .btn.dropdown-toggle");
    public SelenideElement toggleStickerList = $("#last_edited_items a[href*='ab__stickers.manage']");
    public SelenideElement tab_Conditions = $("#conditions");
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

    public Select getSettingOutputTypeTop(){return new Select(settingOutputTypeTop);}
    public void selectSettingOutputTypeTop(String value){
        getSettingOutputTypeTop().selectByValue(value);
    }

    public Select getSettingMaxNumberTop(){return new Select(settingMaxNumberTop);}
    public void selectSettingMaxNumberTop(String value){
        getSettingMaxNumberTop().selectByValue(value);
    }

    public Select getSettingMaxNumberBottom(){return new Select(settingMaxNumberBottom);}
    public void selectSettingMaxNumberBottom(String value){
        getSettingMaxNumberBottom().selectByValue(value);
    }

    public Select getSettingOutputTypeBottom(){return new Select(settingOutputTypeBottom);}
    public void selectSettingOutputTypeBottom(String value){
        getSettingOutputTypeBottom().selectByValue(value);
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
}