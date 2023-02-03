import adminPanel.AdmProductPage;
import adminPanel.CsCartSettings;
import adminPanel.StickerPage;
import adminPanel.VideoGalleryPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.$x;

public class Test_case1 extends TestRunner {
    @Test
    public void VerifyTestCaseOne(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Включаем мини-иконки в виде галереи и окно Быстрого просмотра
        csCartSettings.menuSettings.hover();
        csCartSettings.sectionAppearance.click();
        if (!csCartSettings.settingMiniThumbnailAsGallery.isSelected()){
            csCartSettings.settingMiniThumbnailAsGallery.click();
        }
        if (!csCartSettings.settingQuickView.isSelected()){
            csCartSettings.settingQuickView.click();
        }
        csCartSettings.button_Save.click();
        //Включаем Вертикальное отображение мини-иконок (Модуль "Видео галерея")
        csCartSettings.navigateToAddonsPage();
        VideoGalleryPage videoGalleryPage = csCartSettings.navigateToVideoGalleryPage();
        videoGalleryPage.tabSettings.click();
        if(!videoGalleryPage.settingVerticalView.isSelected()) {
            videoGalleryPage.settingVerticalView.click();
            videoGalleryPage.buttonSaveVideoGallery.click();
        }
        //Настраиваем настройки модуля "Стикеры"
        csCartSettings.navigateToAddonsPage();
        StickerPage stickerPage = csCartSettings.navigateToStickerSettingsPage();
        stickerPage.tabSettings.click();
        stickerPage.selectSettingOutputPosition("L");
        stickerPage.selectSettingOutputTypeTop("column");
        stickerPage.selectSettingMaxNumberTop("3");
        stickerPage.selectSettingOutputTypeBottom("column");
        stickerPage.selectSettingMaxNumberBottom("3");
        stickerPage.buttonSaveSettings.click();
        //Три верхних стикера
        csCartSettings.navigateToAddonsPage();
        csCartSettings.navigateToStickerListPage();
        //Стикер "Акция" (красный цвет)
        stickerPage.sticker_Promotion.click();
        stickerPage.selectSettingPositionsInProductLists("T");
        stickerPage.selectSettingPositionsOnProductPage("T");
        stickerPage.statusActive.click();
        stickerPage.buttonSaveSticker.click();
        //Стикер "Sale > 10% < 30%" (оранжевый цвет)
        stickerPage.dropDownToggle.click();
        stickerPage.toggleStickerList.click();
        stickerPage.sticker_SaleOrange.click();
        stickerPage.selectSettingPositionsInProductLists("T");
        stickerPage.selectSettingPositionsOnProductPage("T");
        stickerPage.statusActive.click();
        stickerPage.buttonSaveSticker.click();
        //Стикер "Популярный" (фиолетовый цвет)
        stickerPage.dropDownToggle.click();
        stickerPage.toggleStickerList.click();
        stickerPage.sticker_PopularProduct.click();
        stickerPage.selectSettingPositionsInProductLists("T");
        stickerPage.selectSettingPositionsOnProductPage("T");
        stickerPage.statusActive.click();
        stickerPage.buttonSaveSticker.click();
        stickerPage.gearWheel.click();
        stickerPage.generateStickerLinks.click();
        //Три нижних стикера
        //Стикер "Высокий рейтинг" (оранжевый цвет)
        Selenide.sleep(2000);
        stickerPage.dropDownToggle.shouldBe(Condition.interactable);
        stickerPage.dropDownToggle.click();
        stickerPage.toggleStickerList.click();
        stickerPage.sticker_TopRated.click();
        stickerPage.selectSettingPositionsInProductLists("B");
        stickerPage.selectSettingPositionsOnProductPage("B");
        stickerPage.statusActive.click();
        stickerPage.buttonSaveSticker.click();
        //Стикер "Бесплатная доставка" (цвет сине-белый)
        stickerPage.dropDownToggle.click();
        stickerPage.toggleStickerList.click();
        stickerPage.sticker_Free_Delivery.click();
        stickerPage.selectSettingPositionsInProductLists("B");
        stickerPage.selectSettingPositionsOnProductPage("B");
        stickerPage.statusActive.click();
        stickerPage.buttonSaveSticker.click();
        stickerPage.gearWheel.click();
        stickerPage.generateStickerLinks.click();
        //Стикер "Вес" (цвет серый)
        Selenide.sleep(2000);
        stickerPage.dropDownToggle.shouldBe(Condition.interactable);
        stickerPage.dropDownToggle.click();
        stickerPage.toggleStickerList.click();
        stickerPage.sticker_Weight.click();
        stickerPage.selectSettingPositionsInProductLists("B");
        stickerPage.selectSettingPositionsOnProductPage("B");
        stickerPage.statusActive.click();
        stickerPage.buttonSaveSticker.click();




        //Удаляем все изображения у товара 1
        AdmProductPage admProductPage = csCartSettings.navigateToEditingProductPage();
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), '18-55mm')]").click();
        admProductPage.productImage.hover();
        admProductPage.button_DeleteProductImage.click();
        csCartSettings.button_Save.click();
    }
}
