package storefront;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class StCategoryPage {
    public StCategoryPage(){super();}

    public SelenideElement breadcrumbs_Phones = $("a:nth-child(3).ty-breadcrumbs__a bdi");
    public SelenideElement template_Grid = $(".ut2-icon-products-multicolumns");
    public SelenideElement template_ListWithoutOptions = $(".ty-icon.ut2-icon-products-without-options");
    public SelenideElement template_CompactList = $(".ty-icon.ut2-icon-short-list");
    public SelenideElement productInList = $(".ut2-gl__image");
    public SelenideElement button_QuickView = $("a.ut2-quick-view-button");
    public SelenideElement button_CloseQuickView = $(".ui-icon-closethick");
    public SelenideElement button_AddToWishList = $(".ut2-add-to-wish");
    public SelenideElement button_CloseWishListPopup = $(".cm-notification-close");
    public SelenideElement button_WishListOnTop = $("a.ty-wishlist__a");
}