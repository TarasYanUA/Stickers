package storefront;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StHomePage {
    public StHomePage(){super();}

    public void shiftLanguage(int index) {
        $("a[id*='_wrap_language_']").click();
        $$("div[id*='_wrap_language_'] .ty-select-block__list li").get(index);
    }
}
