package storefront;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$;

public class StProductPage {
    public StProductPage(){super();}

    public SelenideElement block_Popular = $(".ty-mainbox-title");
    public SelenideElement block_Hits = $x("(//li[contains(@id, 'abt__ut2_grid_tab')])[2]");

    public void shiftLanguage(int index) {
        $("a[id*='_wrap_language_']").hover().click();
        $$("div[id*='_wrap_language_'] li.ty-select-block__list-item").get(index).click();
    }
}
