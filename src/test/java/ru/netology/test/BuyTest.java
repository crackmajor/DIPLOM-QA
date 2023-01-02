package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BuyTest {
    @BeforeEach
    void setup() {
        browser = "chrome";
        open("http://localhost:8080");
        timeout=10000;
    }

    @Test
    @DisplayName("Submitting a 'buy form' with valid data and a card with 'APPROVED' status")
    void shouldBuyTest() {
        val validUser = DataGenerator.UserInfo.getValidUserInfo("APPROVED");
        StartPage startPage = new StartPage();
        val buyPage = startPage.pressTheButtonBuy();
        buyPage.fillInTheFields(validUser);
        $("[class=\"notification__title\"]").shouldBe(Condition.visible);
    }
}
