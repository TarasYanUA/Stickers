package storefront;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class StCategoryPage {
    public StCategoryPage(){super();}

    public SelenideElement breadcrumbs_Phones = $("a:nth-child(3).ty-breadcrumbs__a bdi");
    public SelenideElement template_Grid = $(".ty-icon-products-multicolumns");
    public SelenideElement template_ListWithoutOptions = $(".ty-icon-products-without-options");
    public SelenideElement template_CompactList = $(".cm-ajax .ty-icon-short-list");
    public SelenideElement productInList = $(".ut2-gl__image");
    public SelenideElement button_QuickView = $(".ut2-icon-baseline-visibility");
    public SelenideElement button_CloseQuickView = $(".ui-icon-closethick");
}
