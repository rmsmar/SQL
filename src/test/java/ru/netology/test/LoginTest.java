package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.User;
import ru.netology.domain.UserGenerator;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    UserGenerator mySql = new UserGenerator();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clean() throws SQLException {
        UserGenerator.cleanDb();
    }

    @Test
    void shouldLogin() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = User.getValidAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = UserGenerator.getVerificationCode(authInfo.getLogin());
        val verify = verificationPage.verify(verificationCode);
        verify.checkIfVisible();
    }

    @Test
    void shouldBeBlockedAfterThreeWrongPasswords() throws SQLException {
        val loginPage = new LoginPage();
        val authInfo = User.getAuthInfoWithInvalidPassword();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginFields();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginFields();
        loginPage.validLogin(authInfo);
        val statusSQL = mySql.getStatusFromDb(authInfo.getLogin());
        assertEquals("blocked", statusSQL);
    }
}
