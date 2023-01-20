package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class BuyOnCreditPage {
    private static Map<String, SelenideElement> elementPage = new HashMap<String, SelenideElement>() {{
        put("cardNumberField", $("[placeholder='0000 0000 0000 0000']"));
        put("monthField", $("input[placeholder='08']"));
        put("yearField", $("input[placeholder='22']"));
        put("ownerField", $("div.form-field:nth-child(3) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)"));
        put("cvcField", $("[placeholder='999']"));
        put("resumeButton", $(byText("Продолжить")));
        put("successfully", $("[class='notification notification_visible notification_status_ok notification_has-closer notification_stick-to_right notification_theme_alfa-on-white']"));
        put("error", $("[class='notification notification_visible notification_status_error notification_has-closer notification_stick-to_right notification_theme_alfa-on-white']"));
    }};

    public void fillInTheFields(DataGenerator.UserInfo user) {
        elementPage.get("cardNumberField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("cardNumberField").setValue(user.getNumber());
        elementPage.get("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("monthField").setValue(user.getMonth());
        elementPage.get("yearField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("yearField").setValue(user.getYear());
        elementPage.get("ownerField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("ownerField").setValue(user.getHolder());
        elementPage.get("cvcField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("cvcField").setValue(user.getCvc());
    }

    public void resumeButtonClick() {
        elementPage.get("resumeButton").click();
    }

    public SelenideElement getElementPage(String text) {
        return elementPage.get(text);
    }
}