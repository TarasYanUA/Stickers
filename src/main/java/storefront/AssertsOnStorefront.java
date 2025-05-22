package storefront;

import org.testng.asserts.SoftAssert;
import java.util.Map;
import static com.codeborne.selenide.Selenide.$;

public class AssertsOnStorefront {
    public AssertsOnStorefront() {
        super();
    }

    public String quickViewWindow = ".ut2-pb__items ";

    public String stickersExist = ".ab-stickers-container";
    public String verticalThumbnails = ".ab-vg-vertical-thumbnails";
    public String horizontalThumbnails = ".ab-vg-horizontal-thumbnails";
    public String stickersContainer_TopLeft = ".ab-stickers-container__TL";
    public String stickersContainer_TopRight = ".ab-stickers-container__TR";
    public String stickersContainer_BottomLeft = ".ab-stickers-container__BL";
    public String stickersContainer_BottomRight = ".ab-stickers-container__BR";
    public String columnFilling = ".column-filling";
    public String rowFilling = ".row-filling";
    public String locatedBeforePrice = ".ab-stickers-container__price_before";
    public String locatedAfterPrice = ".ab-stickers-container__price_after";

    public String pictogramsExist = ".ab-s-pictograms-wrapper";
    public String pictogramsPosition_1 = ".ab-s-pictograms-wrapper-position_1";
    public String pictogramsPosition_2 = ".ab-s-pictograms-wrapper-position_2";


    public void assertElementExists(String selector, String page, String prefix) {
        SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

        Map<String, String> messages = Map.ofEntries(
                Map.entry(stickersExist, "There are no stickers "),
                Map.entry(verticalThumbnails, "Gallery of mini-icons is not Vertical "),
                Map.entry(horizontalThumbnails, "Gallery of mini-icons is not Horizontal "),
                Map.entry(stickersContainer_TopLeft, "Stickers are not on the Top-Left side "),
                Map.entry(stickersContainer_TopRight, "Stickers are not on the Top-Right side "),
                Map.entry(stickersContainer_BottomLeft, "Stickers are not on the Bottom-Left side "),
                Map.entry(stickersContainer_BottomRight, "Stickers are not on the Bottom-Right side "),
                Map.entry(columnFilling, "Position of stickers is not in Column "),
                Map.entry(rowFilling, "Position of stickers is not in Row "),
                Map.entry(locatedBeforePrice, "Sticker is not located BEFORE price "),
                Map.entry(locatedAfterPrice, "Sticker is not located AFTER price "),

                Map.entry(pictogramsExist, "There are no pictograms "),
                Map.entry(pictogramsPosition_1, "Pictograms are not in Position 1 "),
                Map.entry(pictogramsPosition_2, "Pictograms are not in Position 2 ")
        );

        String message = messages.get(selector);
        if (message == null)
            throw new IllegalArgumentException("No message found for selector: " + selector);

        softAssert.assertTrue($(prefix + selector).exists(), message + page);
    }
}