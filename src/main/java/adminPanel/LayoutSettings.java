package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LayoutSettings extends CsCartSettings {

    //Меню "Веб-сайт -- Темы -- Макеты"
    public LayoutSettings(){super();}

    public SelenideElement layout_TabProducts = $x("//a[contains(@href, 'selected_location')][text()='Товары']");
    public SelenideElement layout_BlockTemplate = $("select[id*='products_template']");
    SelenideElement layout_ButtonSaveBlock = $("input[name='dispatch[block_manager.update_block]']");
    SelenideElement layoutBlock_TabContent = $("li[id*='block_contents'] a");
    SelenideElement layout_FieldFilling = $("select[id*='content_items_filling']");
    SelenideElement layout_FieldMaxLimit = $("input[id*='content_items_properties_items_limit']");
    SelenideElement layout_ButtonSettings = $("a[id*='sw_case_settings_']");
    SelenideElement layoutSettings_ShowPrice = $("input[id*='products_properties_show_price']");
    SelenideElement layoutSetting_OutsideNavigation = $("input[id*='products_properties_outside_navigation']");


    public void openBlockProperties(String blockName) {
        $x(String.format("//div[@title='%s']/..//div[contains(@class, 'bm-action-properties')]", blockName)).click();
        Utils.waitForPopupWindow(true);
    }

    public void setFillingForBlockContent(String filling) {
        layoutBlock_TabContent.click();
        layout_FieldFilling.selectOptionByValue(filling);
        layout_FieldMaxLimit.setValue("4");
    }

    public void setBlockSettings() {
        sleep(1500);
        layout_ButtonSettings.click();
        Utils.setCheckbox(layoutSettings_ShowPrice, true, "layoutSettings_ShowPrice");
        Utils.setCheckbox(layoutSetting_OutsideNavigation, false, "layoutSetting_OutsideNavigation");
    }

    public void saveBlockProperties() {
        layout_ButtonSaveBlock.click();
        Utils.waitForPopupWindow(false);
    }
}