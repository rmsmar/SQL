package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.UserGenerator;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @AfterAll
    static void tearDown() {
        UserGenerator.cleanData();
    }

    @Test
    void shouldSendRequestHappyPath() {
        var loginPage = new LoginPage();
        var authInfo = UserGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = UserGenerator.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldSendWrongCode() {
        var loginPage = new LoginPage();
        var authInfo = UserGenerator.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify(UserGenerator.getInvalidCode().getCode());
    }

    @Test
    void shouldSendWrongLogin() {
        var loginPage = new LoginPage();
        var authInfo = UserGenerator.getAuthInfo();
        var invalidLogin = UserGenerator.getInvalidLogin();
        loginPage.invalidLogin(UserGenerator.getInvalidLogin());
    }

    @Test
    void shouldSendWrongPassword3Times() {
        var loginPage = new LoginPage();
        var authInfo = UserGenerator.getAuthInfo();
        var invalidPassword = UserGenerator.getInvalidPassword();
        loginPage.invalidPassword3Times(UserGenerator.getInvalidPassword());
    }
}
