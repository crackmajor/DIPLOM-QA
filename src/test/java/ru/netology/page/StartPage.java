package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
    private static Map<String, SelenideElement> elementPage = new HashMap<String, SelenideElement>() {{
        put("buyButton", $(byText("Купить")));
        put("buyOnCreditButton", $(byText("Купить в кредит")));
    }};

    public BuyPage pressTheButtonBuy() {
        elementPage.get("buyButton").click();
        return new BuyPage();
    }

    public BuyOnCreditPage pressTheButtonBuyOnCredit() {
        elementPage.get("buyOnCreditButton").click();
        return new BuyOnCreditPage();
    }
}
