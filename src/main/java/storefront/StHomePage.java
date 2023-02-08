package storefront;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StHomePage {
    public StHomePage(){super();}

    public void shiftLanguage(int index) {
        $("a[id*='_wrap_language_']").click();
        $$("div[id*='_wrap_language_'] li.ty-select-block__list-item").get(index).click();
    }
}
