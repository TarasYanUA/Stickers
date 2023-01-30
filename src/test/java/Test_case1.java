import adminPanel.AdmProductPage;
import adminPanel.CsCartSettings;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.$x;

public class Test_case1 extends TestRunner {
    @Test
    public void VerifyTestCaseOne(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Включаем окно Быстрого просмотра и мини-иконки в виде галереи


        //Подготавливаем Стикеры (6 штук) и пиктограммы


        //Удаляем все изображения у товара 1
        AdmProductPage admProductPage = csCartSettings.navigateToEditingProductPage();
        $x("//td[@class='product-name-column wrap-word']//a[contains(text(), '18-55mm')]").click();
        admProductPage.productImage.hover();
        admProductPage.button_DeleteProductImage.click();
        csCartSettings.button_Save.click();

    }
}
