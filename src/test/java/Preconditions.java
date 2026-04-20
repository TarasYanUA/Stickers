import adminPanel.BasicPage;
import adminPanel.StickerSettings;
import org.testng.annotations.Test;

public class Preconditions extends TestRunner {

    @Test
    public void PreconditionConfigurations() {
        BasicPage basicPage = new BasicPage();
        StickerSettings stickerSettings = new StickerSettings();

        //Три верхних стикера
        basicPage.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerSettings.sticker_Promotion.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        //Стикер "Sale > 10% < 30%" (оранжевый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_SaleOrange.click();
        stickerSettings.statusActive.click();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        //Стикер "Популярный" (фиолетовый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_PopularProduct.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "T", "full_size");
        stickerSettings.generateStickerLinks();

        //Три нижних стикера
        //Стикер "Высокий рейтинг" (оранжевый цвет)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_TopRated.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        //Стикер "Бесплатная доставка" (цвет сине-белый)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_Free_Delivery.click();
        stickerSettings.statusActive.click();
        stickerSettings.addConditionOfPrice();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
        stickerSettings.generateStickerLinks();
        //Стикер "Вес" (цвет серый)
        stickerSettings.goToAbMenu_StickerListPage();
        stickerSettings.sticker_Weight.click();
        stickerSettings.statusActive.click();
        stickerSettings.setSettingsAt_DisplayTab("product_labels", "B", "full_size");
    }
}
