package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.UserGenerator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }
    public DashboardPage validVerify(UserGenerator.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        $(withText("Ошибка")).shouldBe(visible);
    }
}
