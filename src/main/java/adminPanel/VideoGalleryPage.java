package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class VideoGalleryPage {
    public VideoGalleryPage(){super();}

    public SelenideElement tabSettings = $("#settings");
    public SelenideElement settingVerticalView = $("input[id*='addon_option_ab__video_gallery_vertical']");
    public SelenideElement buttonSaveVideoGallery = $(".cm-addons-save-settings");
}