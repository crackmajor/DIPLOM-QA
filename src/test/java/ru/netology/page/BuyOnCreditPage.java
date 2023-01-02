package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class BuyOnCreditPage {
    private static Map<String, SelenideElement> elementPage = new HashMap<String, SelenideElement>() {{
        put("cardNumberField", $(byText("Номер карты")));
        put("monthField", $(byText("Месяц")));
        put("yearField", $(byText("Год")));
        put("ownerField", $(byText("Владелец")));
        put("cvcField", $(byText("CVC/CVV")));
        put("resumeButton", $(byText("Продолжить")));
    }};

}
