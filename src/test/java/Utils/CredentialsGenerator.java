package Utils;

import UI.Models.TestUser;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class CredentialsGenerator {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final Random random = new Random();

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generatePassword() {
        int length = random.nextInt(18) + 8;
        StringBuilder password = new StringBuilder();
        password.append((char) ('A' + random.nextInt(26)));
        password.append((char) ('0' + random.nextInt(10)));
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String allChars = letters + digits;
        for (int i = 2; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return password.toString();
    }

    private static String generateUSZipCode() {
        int zip = 10000 + random.nextInt(89999);
        return String.valueOf(zip);
    }

    public static TestUser generateTestUser() {
        return TestUser.builder()
                .email(generateEmail())
                .password(generatePassword())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .zipCode(generateUSZipCode())
                .build();
    }
}
