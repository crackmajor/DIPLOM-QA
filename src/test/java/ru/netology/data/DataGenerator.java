package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataGenerator {
    private static final Map<String, String> cardsNumbers = new HashMap<String, String>() {{
        put("APPROVED", "4444 4444 4444 4441");
        put("DECLINED", "4444 4444 4444 4442");
        put("RANDOM", generateRandomCard());
    }};

    public static String getCardNumbers(String status) {
        return cardsNumbers.get(status);
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("MM.yyyy"));
    }

    public static String generateOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateRandomCard() {
        Faker faker = new Faker(new Locale("en"));
        return String.valueOf(faker.business().creditCardNumber());
    }

    @Value
    public static class UserInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;

        public static UserInfo getUserInfo(String status, int shift) {
            String date = generateDate(shift);
            return new UserInfo(getCardNumbers(status),
                    date.substring(0, 2),
                    date.substring(5, 7),
                    generateOwner(),
                    "666");
        }
    }
}

