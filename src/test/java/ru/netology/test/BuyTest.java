package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BuyTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        Configuration.headless = true;
        open("http://localhost:8080");
        timeout = 12000;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and a card with 'APPROVED' status")
    void buyShouldBeApprovedTest() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.getElementPage("successfully").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and a card with 'DECLINED' status")
    void buyShouldBeRejectedTest() {
        val user = DataGenerator.UserInfo.getUserInfo("DECLINED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.getElementPage("error").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy in credit form' with valid data and a card with 'APPROVED' status")
    void buyOnCreditShouldBeApprovedTest() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.getElementPage("successfully").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and a card with 'DECLINED' status")
    void buyOnCreditShouldBeRejectedTest() {
        val user = DataGenerator.UserInfo.getUserInfo("DECLINED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.getElementPage("error").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting an empty 'buy form'")
    void emptyBuyFormTest() {
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting an empty 'buy on credit form'")
    void emptyBuyOnCreditFormTest() {
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with random card details")
    void buyShouldBeRejectedTest2() {
        val user = DataGenerator.UserInfo.getUserInfo("RANDOM", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.getElementPage("error").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with random card details")
    void buyShouldBeRejectedTest3() {
        val user = DataGenerator.UserInfo.getUserInfo("RANDOM", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.getElementPage("error").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and empty field 'card number'")
    void buyShouldBeRejectedTest4() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("cardNumberField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and empty field 'card number'")
    void buyShouldBeRejectedTest5() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("cardNumberField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and empty field 'month'")
    void buyShouldBeRejectedTest6() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and empty field 'month'")
    void buyShouldBeRejectedTest7() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and empty field 'year'")
    void buyShouldBeRejectedTest8() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("yearField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and empty field 'year'")
    void buyShouldBeRejectedTest9() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("yearField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and empty field 'owner'")
    void buyShouldBeRejectedTest10() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("ownerField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and empty field 'owner'")
    void buyShouldBeRejectedTest11() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("ownerField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and empty field 'cvc'")
    void buyShouldBeRejectedTest12() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("cvcField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and empty field 'cvc'")
    void buyShouldBeRejectedTest13() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("cvcField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and month field '00'")
    void buyShouldBeRejectedTest14() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.getElementPage("monthField").setValue("00");
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверно указан срок действия карты")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and month field '00'")
    void buyShouldBeRejectedTest15() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.getElementPage("monthField").setValue("00");
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверно указан срок действия карты")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and month field '13'")
    void buyShouldBeRejectedTest16() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.getElementPage("monthField").setValue("13");
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверно указан срок действия карты")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and month field '13'")
    void buyShouldBeRejectedTest17() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.getElementPage("monthField").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        buyPage.getElementPage("monthField").setValue("13");
        buyPage.resumeButtonClick();
        $("[class='input__sub']").shouldHave(Condition.text("Неверно указан срок действия карты")).shouldBe(Condition.visible);
    }

//    @Test
//    @DisplayName("Submitting a 'buy form' with invalid data and a card with 'APPROVED' status")
//    void buyShouldBeRejectedTest18() {
//        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", -360);
//        StartPage startPage = new StartPage();
//        val buyPage = startPage.pressTheButtonBuy();
//        buyPage.fillInTheFields(user);
//        buyPage.resumeButtonClick();
//        buyPage.getElementPage("successfully").shouldBe(Condition.visible);
//    }
}
