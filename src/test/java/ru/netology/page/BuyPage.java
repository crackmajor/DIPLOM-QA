package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyPage {
    private static Map<String, SelenideElement> elementPage = new HashMap<String, SelenideElement>() {{
        put("cardNumberField", $("[placeholder='0000 0000 0000 0000']"));
        put("monthField", $("input[placeholder='08']"));
        put("yearField", $("input[placeholder='22']"));
        put("ownerField", $("div.form-field:nth-child(3) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)"));
        put("cvcField", $("[placeholder='999']"));
        put("resumeButton", $(byText("Продолжить")));
    }};

    public void fillInTheFields(DataGenerator.UserInfo user) {
        elementPage.get("cardNumberField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("cardNumberField").setValue(user.getCardNumber());
        elementPage.get("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("monthField").setValue(user.getMonth());
        elementPage.get("yearField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("yearField").setValue(user.getYear());
        elementPage.get("ownerField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("ownerField").setValue(user.getOwner());
        elementPage.get("cvcField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        elementPage.get("cvcField").setValue(user.getCvc());
        elementPage.get("resumeButton").click();
    }
}
