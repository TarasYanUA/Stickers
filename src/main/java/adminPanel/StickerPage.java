package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Selenide.$;

public class StickerPage {
    public StickerPage(){super();}
    public SelenideElement sticker_Promotion = $("a[href*='sticker_id=11']");
    public SelenideElement sticker_SaleOrange = $("a[href*='sticker_id=14']");
    public SelenideElement sticker_PopularProduct = $("a[href*='sticker_id=18']");
    public SelenideElement sticker_TopRated = $("a[href*='sticker_id=10']");
    public SelenideElement sticker_Free_Delivery = $("a[href*='sticker_id=19']");
    public SelenideElement sticker_Weight = $("a[href*='sticker_id=20']");

    public SelenideElement settingPositionsInProductLists = $("#ab__stickers_output_position_list");
    public SelenideElement settingPositionsOnProductPage = $("#ab__stickers_output_position_detailed_page");
    public SelenideElement statusActive = $("#ab__stickers_status_11_a");
    public SelenideElement buttonSaveSticker = $(".cm-submit.btn-primary");
    public SelenideElement gearWheel = $("div.btn-group.dropleft");
    public SelenideElement generateStickerLinks = $(".cm-post.cm-comet");
    public SelenideElement dropDownToggle = $("#last_edited_items .btn.dropdown-toggle");
    public SelenideElement toggleStickerList = $("#last_edited_items a[href*='ab__stickers.manage']");



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
}
