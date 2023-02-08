package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DataGenerator {
    private static final Map<String, String> testInfo = new HashMap<String, String>() {{
        put("APPROVED", "4444 4444 4444 4441");
        put("DECLINED", "4444 4444 4444 4442");
        put("RANDOM", generateRandomCard());
        put("SHORT_CARD", "1234 1234 4567 789");
        put("LONG_CARD", "1234 1234 4567 7899 1");
        put("SPECIAL", ";¶æþßڐٍ₷ℓΌῪ↔›ﭞﭘﭾנּﻑﻱæ");
        put("LATIN", "gfdahbv");
        put("KIRI", "куку");
        put("pay", "/api/v1/pay");
        put("credit", "/api/v1/credit");
        put("VALID_SPEC", "Mamed Magomed-Ogly");
        put("NAME", "");
        put("INVALID", "-Mamed Magomed-Ogly-");
        put("INVALID_1", "i");
        put("INVALID_2", "Иван Иванов");
        put("INVALID_3", "Pavlov Pеtr");
        put("INVALID_4", "12345 Petr");
        put("INVALID_5", "INVALID_5");
        put("INVALID_LONG", ";¶æþßڐٍ₷ℓΌῪ↔›ﭞﭘﭾנּﻑﻱæ Petr");
        put("INVALID_130", RandomStringUtils.randomAlphabetic(130));
    }};

    public static String generateOwner(String status) {
        if (Objects.equals(status, "VALID")) {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().lastName() + " " + faker.name().firstName();
        }
        if (Objects.equals(status, "NAME")) {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().firstName();
        }
        return getTestInfo(status);
    }

    public static String getTestInfo(String id) {
        return testInfo.get(id);
    }

    public static LocalDate generateDate() {
        return LocalDate.now();
    }

    public static String getMonth(int shift) {
        String shiftDate = generateDate().plusDays(shift).format(DateTimeFormatter.ofPattern("MM.yyyy"));
        return shiftDate.substring(0, 2);
    }

    public static String getYear(int shift) {
        String shiftDate = generateDate().plusDays(shift).format(DateTimeFormatter.ofPattern("MM.yyyy"));
        return shiftDate.substring(5, 7);
    }

    public static String generateRandomCard() {
        Faker faker = new Faker(new Locale("en"));
        return String.valueOf(faker.business().creditCardNumber());
    }

    public static UserInfo getUserInfo(String card, String owner, int monthShift, int yearShift) {
        return new UserInfo(getTestInfo(card),
                DataGenerator.getMonth(monthShift),
                DataGenerator.getYear(yearShift),
                generateOwner(owner),
                "666");
    }

    @Value
    public static class UserInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }
}