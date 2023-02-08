package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServiceTest {
    public static final String color = "\u001B[32m";

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        SQLHelper.dropData();
        Configuration.headless = true;
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("#1")
    void test1() {
        System.out.println(color + "Отправка формы 'купить' с данными 1й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("APPROVED", paymentData.getStatus());
        assertEquals(4500000, paymentData.getAmount());
    }

    @Test
    @DisplayName("#2")
    void test2() {
        System.out.println(color + "Отправка формы 'купить' с данными 2й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("DECLINED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("DECLINED", paymentData.getStatus());
        assertEquals(4500000, paymentData.getAmount());
    }

    @Test
    @DisplayName("#3")
    void test3() {
        System.out.println(color + "Отправка формы 'купить в кредит' с данными 1й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
        var creditData = SQLHelper.getCreditData();
        assertEquals("APPROVED", creditData.getStatus());
        assertNotNull(creditData.getBank_id());
    }

    @Test
    @DisplayName("#4")
    void test4() {
        System.out.println(color + "Отправка формы 'купить в кредит' с данными 2й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("DECLINED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpErrorShouldBeVisible();
        var creditData = SQLHelper.getCreditData();
        assertEquals("DECLINED", creditData.getStatus());
    }

    @Test
    @DisplayName("#5")
    void test5() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести имя и фамилию на латинице,содержащую дефис, например 'Mamed Magomed-Ogly'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID_SPEC", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("APPROVED", paymentData.getStatus());
        assertEquals(4500000, paymentData.getAmount());
    }

    @Test
    @DisplayName("#6")
    void test6() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести имя и фамилию на латинице,содержащую дефис, например 'Mamed Magomed-Ogly'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID_SPEC", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
        var creditData = SQLHelper.getCreditData();
        assertEquals("APPROVED", creditData.getStatus());
        assertNotNull(creditData.getBank_id());
    }

    @Test
    @DisplayName("#7")
    void test7() {
        System.out.println(color + "Отправка пустой формы 'купить'." + color);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

//    @Test
//    @DisplayName("#8")
//    void test8() {
//        System.out.println(color + "Отправка пустой формы 'купить', затем заполнить пустые поля валидными данными." + color);
//        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
//        StartPage startPage = new StartPage();
//        val buyPage = startPage.pressTheButtonBuy();
//        buyPage.resumeButtonClick();
//        buyPage.fillInTheFields(user);
//        buyPage.hintShouldBeHidden();
//    }

    @Test
    @DisplayName("#9")
    void test9() {
        System.out.println(color + "Отправка пустой формы 'купить в кредит'." + color);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

//    @Test
//    @DisplayName("#10")
//    void test10() {
//        System.out.println(color + "Отправка пустой формы 'купить в кредит', затем заполнить пустые поля валидными данными." + color);
//        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
//        StartPage startPage = new StartPage();
//        val buyPage = startPage.pressTheButtonBuyOnCredit();
//        buyPage.resumeButtonClick();
//        buyPage.fillInTheFields(user);
//        buyPage.hintShouldBeHidden();
//    }

    @Test
    @DisplayName("#11")
    void test11() {
        System.out.println(color + "Отправка формы 'купить' с данными случайной карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("RANDOM", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpErrorShouldBeVisible();
    }

    @Test
    @DisplayName("#12")
    void test12() {
        System.out.println(color + "Отправка формы 'купить в кредит' с данными случайной карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("RANDOM", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpErrorShouldBeVisible();
    }

    @Test
    @DisplayName("#13")
    void test13() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, поле номер карты пустое." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearCardNumberField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#14")
    void test14() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, поле месяц пустое. Остальные поля валидные тестовые данные. " + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#15")
    void test15() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, поле год пустое. Остальные поля валидные тестовые данные. " + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#16")
    void test16() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, поле владелец пустое. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "empty", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearOwnerField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#17")
    void test17() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, поле cvc/cvv пустое. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#18")
    void test18() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, поле номер карты пустое." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearCardNumberField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#19")
    void test19() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, поле месяц пустое. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#20")
    void test20() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, поле год пустое. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#21")
    void test21() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, поле владелец пустое. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "empty", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearOwnerField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#22")
    void test22() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, поле cvc/cvv пустое. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#23")
    void test23() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле номер карты ввести невалидное значение из 15 цифр." + color);
        val user = DataGenerator.getUserInfo("SHORT_CARD", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#24")
    void test24() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле номер карты ввести спец символы." + color);
        val user = DataGenerator.getUserInfo("SPECIAL", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#25")
    void test25() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле номер карты ввести латинские буквы, например 'gfdahbv'." + color);
        val user = DataGenerator.getUserInfo("LATIN", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#26")
    void test26() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле номер карты ввести невалидное значение из 17 цифр." + color);
        val user = DataGenerator.getUserInfo("LONG_CARD", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#27")
    void test27() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле номер карты ввести невалидное значение из 15 цифр." + color);
        val user = DataGenerator.getUserInfo("SHORT_CARD", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#28")
    void test28() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле номер карты ввести спец символы." + color);
        val user = DataGenerator.getUserInfo("SPECIAL", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#29")
    void test29() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле номер карты ввести латинские буквы, например 'gfdahbv'." + color);
        val user = DataGenerator.getUserInfo("LATIN", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#30")
    void test30() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле номер карты ввести невалидное значение из 17 цифр." + color);
        val user = DataGenerator.getUserInfo("LONG_CARD", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#31")
    void test31() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести невалидное значение из 1 цифры, например '2'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField("2");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#32")
    void test32() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести -1 от текущего." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", -360, -360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Истёк срок действия карты");
    }

    @Test
    @DisplayName("#33")
    void test33() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести +6 от текущего." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 0, 2160);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("#34")
    void test34() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести валидное значение из 2 цифр и попытатья добавить еще 1 цифру, например 230." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField("230");
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#35")
    void test35() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести спец символы" + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField(DataGenerator.getTestInfo("SPECIAL"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#36")
    void test36() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести латинские буквы, например 'rere'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField(DataGenerator.getTestInfo("LATIN"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#37")
    void test37() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле год ввести буквы латиницей, например 'куку'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField(DataGenerator.getTestInfo("KIRI"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#38")
    void test38() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести невалидное значение из 1 цифры, например 2." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField("2");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#39")
    void test39() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести -1 от текущего." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, -360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Истёк срок действия карты");
    }

    @Test
    @DisplayName("#40")
    void test40() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести +6 от текущего." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 0, 2160);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("#41")
    void test41() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести валидное значение из 2 цифр и попытатья добавить еще 1 цифру, например 230." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.fillInTheYearField("230");
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#42")
    void test42() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести спец символы, например '¶¶¶¶'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField(DataGenerator.getTestInfo("SPECIAL"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#43")
    void test43() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести латинские буквы, например 'rere'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField(DataGenerator.getTestInfo("LATIN"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#44")
    void test44() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле год ввести буквы кириллицей, например 'куку'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearYearField();
        buyPage.fillInTheYearField(DataGenerator.getTestInfo("KIRI"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#45")
    void test45() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц  ввести невалидное значение из 1 цифры, например '2'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField("2");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#46")
    void test46() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц ввести валидное значение из 2 цифр и попытатья добавить еще 1 цифру, например 120." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.fillInTheMonthField("230");
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#47")
    void test47() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц ввести '00'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField("00");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("#48")
    void test48() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц ввести '13'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField("13");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("#49")
    void test49() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц ввести спец символы, например '¶¶'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField(DataGenerator.getTestInfo("SPECIAL"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#50")
    void test50() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц ввести латинские буквы, например 're'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField(DataGenerator.getTestInfo("LATIN"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#51")
    void test51() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле месяц ввести буквы латиницей, например 'ку'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField(DataGenerator.getTestInfo("KIRI"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#52")
    void test52() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц  ввести невалидное значение из 1 цифры, например '2'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField("2");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#53")
    void test53() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц ввести валидное значение из 2 цифр и попытатья добавить еще 1 цифру, например 120." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.fillInTheMonthField("230");
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#54")
    void test54() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц ввести '00'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField("00");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("#55")
    void test55() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц ввести '13'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField("13");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("#56")
    void test56() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц ввести спец символы, например '¶¶'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField(DataGenerator.getTestInfo("SPECIAL"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#57")
    void test57() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц ввести латинские буквы, например 're'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField(DataGenerator.getTestInfo("LATIN"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#58")
    void test58() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле месяц ввести буквы латиницей, например 'ку'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearMonthField();
        buyPage.fillInTheMonthField(DataGenerator.getTestInfo("KIRI"));
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#59")
    void test59() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести только имя на латинице, например 'Ivan'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "NAME", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#60")
    void test60() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести имя и фамилию на латинице с дефисом в начале или в конце, например '-Mamed Magomed-Ogly' / 'Mamed Magomed-Ogly-'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#61")
    void test61() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести только 1 букву латиницей, например 'I'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_1", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#62")
    void test62() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести фамилию и имя кириллицей, например 'Иван Иванов'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_2", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#63")
    void test63() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести фамилию и имя латиницей, содержащее букву кириллицей, например 'Pavlov Pеtr'('е'- кириллицей)." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_3", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#64")
    void test64() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести цифры, например '12345 Petr'. " + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_4", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#65")
    void test65() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести имя и фамилию, содержащие спец символы'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_5", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#66")
    void test66() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле владелец ввести имя и фамилию, содержащие более 128 символов." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_130", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#67")
    void test67() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести только имя на латинице, например 'Ivan'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "NAME", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#68")
    void test68() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести имя и фамилию на латинице с дефисом в начале или в конце, например '-Mamed Magomed-Ogly' / 'Mamed Magomed-Ogly-'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#69")
    void test69() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести только 1 букву латиницей, например 'I'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_1", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#70")
    void test70() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести фамилию и имя кириллицей, например 'Иван Иванов'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_2", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#71")
    void test71() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести фамилию и имя латиницей, содержащее букву кириллицей, например 'Pavlov Pеtr'('е'- кириллицей)." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_3", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#72")
    void test72() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести цифры, например '12345 Petr'. " + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_4", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#73")
    void test73() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести имя и фамилию, содержащие спец символы'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_5", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#74")
    void test74() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле владелец ввести имя и фамилию, содержащие более 128 символов." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "INVALID_130", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#75")
    void test75() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле CVC/CVV ввести невалидное значение из 1 цифры, например '6'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("6");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#76")
    void test76() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле CVC/CVV ввести валидное значение из 3 цифр и попытатья добавить еще 1 цифру, например 120." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.fillInTheCVCField("120");
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#77")
    void test77() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле CVC/CVV ввести спец символы, например '¶¶'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("\uFEF1" + "\u00B6" + "\u0556" + "\u00B6" + "\u00B6" + "\u0604");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#78")
    void test78() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле CVC/CVV ввести латинские буквы, например 're'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("re");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#79")
    void test79() {
        System.out.println(color + "Отправка формы 'купить' c валидными тестовыми данными, в поле CVC/CVV ввести буквы латиницей, например 'ку'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("ку");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#80")
    void test80() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле CVC/CVV ввести невалидное значение из 1 цифры, например '2'. " + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("2");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Неверный формат");
    }

    @Test
    @DisplayName("#81")
    void test81() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле CVC/CVV ввести валидное значение из 3 цифр и попытатья добавить еще 1 цифру, например 120." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.fillInTheCVCField("120");
        buyPage.resumeButtonClick();
        buyPage.popUpSuccessfullyShouldBeVisible();
    }

    @Test
    @DisplayName("#82")
    void test82() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле CVC/CVV ввести спец символы, например '¶¶'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("\uFEF1" + "\u00B6" + "\u0556" + "\u00B6" + "\u00B6" + "\u0604");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#83")
    void test83() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле CVC/CVV ввести латинские буквы, например 're'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("re");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("#84")
    void test84() {
        System.out.println(color + "Отправка формы 'купить в кредит' c валидными тестовыми данными, в поле CVC/CVV ввести буквы латиницей, например 'ку'." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuyOnCredit();
        buyPage.fillInTheFields(user);
        buyPage.clearCVCField();
        buyPage.fillInTheCVCField("ку");
        buyPage.resumeButtonClick();
        buyPage.hintVisible("Поле обязательно для заполнения");
    }
}