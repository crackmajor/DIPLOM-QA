package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyOnCreditPage {
    SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    SelenideElement monthField = $("input[placeholder='08']");
    SelenideElement yearField = $("input[placeholder='22']");
    SelenideElement ownerField = $$("[class='input__control']").get(3);
    SelenideElement cvcField = $("[placeholder='999']");
    SelenideElement resumeButton = $(byText("Продолжить"));
    SelenideElement successfully = $(byText("Операция одобрена Банком."));
    SelenideElement error = $(byText("Ошибка! Банк отказал в проведении операции."));

    public void fillInTheFields(DataGenerator.UserInfo user) {
        clearCardNumberField();
        cardNumberField.setValue(user.getNumber());
        clearMonthField();
        monthField.setValue(user.getMonth());
        clearYearField();
        yearField.setValue(user.getYear());
        clearOwnerField();
        ownerField.setValue(user.getHolder());
        clearCVCField();
        cvcField.setValue(user.getCvc());
    }

    public void fillInTheField(SelenideElement field, String text) {
        field.sendKeys(text);
    }

    public void fillInTheCardNumberField(String text) {
        fillInTheField(cardNumberField, text);
    }

    public void fillInTheMonthField(String text) {
        fillInTheField(monthField, text);
    }

    public void fillInTheYearField(String text) {
        fillInTheField(yearField, text);
    }

    public void fillInTheOwnerField(String text) {
        fillInTheField(ownerField, text);
    }

    public void fillInTheCVCField(String text) {
        fillInTheField(cvcField, text);
    }

    public void fieldCleaner(SelenideElement field) {
        field.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    public void clearCardNumberField() {
        fieldCleaner(cardNumberField);
    }

    public void clearMonthField() {
        fieldCleaner(monthField);
    }

    public void clearYearField() {
        fieldCleaner(yearField);
    }

    public void clearOwnerField() {
        fieldCleaner(ownerField);
    }

    public void clearCVCField() {
        fieldCleaner(cvcField);
    }

    public void resumeButtonClick() {
        resumeButton.click();
    }

    public void popUpSuccessfullyShouldBeVisible() {
        successfully.shouldBe(Condition.visible);
    }

    public void popUpErrorShouldBeVisible() {
        error.shouldBe(Condition.visible);
    }

    public void hintShouldBeVisible() {
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    public void hintShouldBeHidden() {
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.hidden);
    }

    public void hintCardExpiredShouldBeVisible() {
        $("[class='input__sub']").shouldHave(Condition.text("Истёк срок действия карты")).shouldBe(Condition.visible);
    }

    public void hintInvalidExpirationDateShouldBeVisible() {
        $("[class='input__sub']").shouldHave(Condition.text("Неверно указан срок действия карты")).shouldBe(Condition.visible);
    }

    public void hintRequiredFieldShouldBeVisible() {
        $("[class='input__sub']").shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }
}