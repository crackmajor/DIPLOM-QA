package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.APIHelper;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;

import static org.junit.jupiter.api.Assertions.*;

public class APITest {
    public static final String color = "\u001B[32m";

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        SQLHelper.dropData();
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
        val status = APIHelper.sendForm(user, DataGenerator.getTestInfo("pay"), 200);
        assertTrue(status.contains("APPROVED"));
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("APPROVED", paymentData.getStatus());
        assertEquals(4500000, paymentData.getAmount());
    }

    @Test
    @DisplayName("#2")
    void test2() {
        System.out.println(color + "Отправка формы 'купить' с данными 2й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("DECLINED", "VALID", 360, 360);
        val status = APIHelper.sendForm(user, DataGenerator.getTestInfo("pay"), 200);
        assertTrue(status.contains("DECLINED"));
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("DECLINED", paymentData.getStatus());
        assertEquals(4500000, paymentData.getAmount());
    }

    @Test
    @DisplayName("#3")
    void test3() {
        System.out.println(color + "Отправка формы 'купить в кредит' с данными 1й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("APPROVED", "VALID", 360, 360);
        val status = APIHelper.sendForm(user, DataGenerator.getTestInfo("credit"), 200);
        assertTrue(status.contains("APPROVED"));
        var creditData = SQLHelper.getCreditData();
        assertEquals("APPROVED", creditData.getStatus());
        assertNotNull(creditData.getBank_id());
    }

    @Test
    @DisplayName("#4")
    void test4() {
        System.out.println(color + "Отправка формы 'купить в кредит' с данными 2й карты. Остальные поля валидные тестовые данные." + color);
        val user = DataGenerator.getUserInfo("DECLINED", "VALID", 360, 360);
        val status = APIHelper.sendForm(user, DataGenerator.getTestInfo("credit"), 200);
        assertTrue(status.contains("DECLINED"));
        var creditData = SQLHelper.getCreditData();
        assertEquals("DECLINED", creditData.getStatus());
        assertNotNull(creditData.getBank_id());
    }
}