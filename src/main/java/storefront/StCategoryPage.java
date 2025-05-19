package storefront;

import adminPanel.Utils;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class StCategoryPage {
    public StCategoryPage(){super();}

    public SelenideElement template_Grid = $(".ut2-icon-products-multicolumns");
    public SelenideElement template_ListWithoutOptions = $(".ty-icon.ut2-icon-products-without-options");
    public SelenideElement template_CompactList = $(".ty-icon.ut2-icon-short-list");
    public SelenideElement productInList = $(".ut2-gl__image");
    SelenideElement button_QuickView = $("a.ut2-quick-view-button");
    public SelenideElement button_CloseQuickView = $(".ui-icon-closethick");
    SelenideElement button_AddToWishList = $(".ut2-add-to-wish");
    SelenideElement button_CloseWishListPopup = $(".cm-notification-close");
    SelenideElement button_WishListOnTop = $("a.ty-wishlist__a");

    public void openQuickViewWindow() {
        button_QuickView.hover().click();
        Utils.waitForSpinnerDisappear();
        $(".ui-dialog-title").shouldBe(Condition.visible).hover();
    }

    public void addProductToWishListAndNavigateToWishListPage() {
        button_AddToWishList.hover().click();
        Utils.waitForSpinnerDisappear();
        button_CloseWishListPopup.shouldBe(Condition.visible).click();
        button_WishListOnTop.click();
        Selenide.sleep(2000);
    }
}