package ru.netology.domain;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class UserGenerator {
    private UserGenerator() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidLogin() {
        Faker faker = new Faker();
        return new AuthInfo(faker.name().username(), "qwerty123");
    }

    public static AuthInfo getInvalidPassword() {
        Faker faker = new Faker();
        return new AuthInfo("vasya", faker.internet().password());
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCode() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC;";
        String verificationCode;
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            verificationCode = runner.query(connection, codeSQL, new ScalarHandler<>());
        }
        return new VerificationCode(verificationCode);
    }

    public static VerificationCode getInvalidCode() {
        return new VerificationCode("12345");
    }

    @SneakyThrows
    public static void cleanData() {
        var runner = new QueryRunner();
        var cleanAuth_codes = "DELETE FROM auth_codes";
        var cleanCardTransactions = "DELETE FROM card_transactions";
        var cleanCardSQL = "DELETE FROM cards";
        var cleanUsers = "DELETE FROM users";

        try (
                var connection = DriverManager.
                        getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            runner.execute(connection, cleanAuth_codes);
            runner.execute(connection, cleanCardTransactions);
            runner.execute(connection, cleanCardSQL);
            runner.execute(connection, cleanUsers);
        }
    }
}
