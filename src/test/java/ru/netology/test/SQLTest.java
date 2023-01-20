package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.APIHelper;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SQLTest {
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
    @DisplayName("Submitting a 'buy form' with valid data and a card with 'APPROVED' status")
    void test1() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        val status = APIHelper.sendBuyForm(user);
        assertTrue(status.contains("APPROVED"));
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("APPROVED", paymentData.getStatus());
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and a card with 'DECLINED' status")
    void test2() {
        val user = DataGenerator.UserInfo.getUserInfo("DECLINED", 360);
        val status = APIHelper.sendBuyForm(user);
        assertTrue(status.contains("DECLINED"));
        var paymentData = SQLHelper.getPaymentData();
        assertEquals("DECLINED", paymentData.getStatus());
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and a card with 'APPROVED' status")
    void test3() {
        val user = DataGenerator.UserInfo.getUserInfo("APPROVED", 360);
        val status = APIHelper.sendBuyOnCreditForm(user);
        assertTrue(status.contains("APPROVED"));
        var creditData = SQLHelper.getCreditData();
        assertEquals("APPROVED", creditData.getStatus());
    }

    @Test
    @DisplayName("Submitting a 'buy on credit form' with valid data and a card with 'DECLINED' status")
    void test4() {
        val user = DataGenerator.UserInfo.getUserInfo("DECLINED", 360);
        val status = APIHelper.sendBuyOnCreditForm(user);
        assertTrue(status.contains("DECLINED"));
        var creditData = SQLHelper.getCreditData();
        assertEquals("DECLINED", creditData.getStatus());
    }
}
