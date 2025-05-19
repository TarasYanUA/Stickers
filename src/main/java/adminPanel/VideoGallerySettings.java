package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class VideoGallerySettings {
    public VideoGallerySettings(){super();}

    SelenideElement tabSettings = $("#settings");
    SelenideElement settingVerticalView = $("input[id*='addon_option_ab__video_gallery_vertical']");

    public void setVerticalView(boolean shouldBeChecked) {
        tabSettings.click();
        Utils.setCheckbox(settingVerticalView, shouldBeChecked, "settingVerticalView");
        Utils.saveSettings();
    }
}