package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement buyOnCreditButton = $(byText("Купить в кредит"));


    public BuyPage pressTheButtonBuy() {
        buyButton.click();
        return new BuyPage();
    }

    public BuyOnCreditPage pressTheButtonBuyOnCredit() {
        buyOnCreditButton.click();
        return new BuyOnCreditPage();
    }
}
