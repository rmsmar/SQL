package ru.netology.domain;

import com.github.javafaker.Faker;
import lombok.Value;

public class User {
    private User() {
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoWithInvalidPassword() {
        Faker faker = new Faker();
        return new AuthInfo("vasya", faker.internet().password());
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }
}