package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardNumber;
import static ru.netology.data.DataHelper.getSecondCardNumber;
import static ru.netology.page.DashboardPage.firstCardPushButton;
import static ru.netology.page.DashboardPage.secondCardPushButton;

public class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void transferFrom1to2() {
        int amount = 7000;
        var dashboardPage = new DashboardPage();
        var firstCardInitialAmount = dashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = dashboardPage.getSecondCardBalance();
        var transactionPage = secondCardPushButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        var firstCardEndBalance = firstCardInitialAmount - amount;
        var secondCardEndBalance = secondCardInitialAmount + amount;
        assertEquals(firstCardEndBalance, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardEndBalance, dashboardPage.getSecondCardBalance());

    }

    @Test
    void transferFrom2to1() {
        int amount = 7000;
        var dashboardPage = new DashboardPage();
        var firstCardInitialAmount = dashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = dashboardPage.getSecondCardBalance();
        var transactionPage = firstCardPushButton();
        transactionPage.transferMoney(amount, getSecondCardNumber());
        var secondCardEndBalance = secondCardInitialAmount - amount;
        var firstCardEndBalance = firstCardInitialAmount + amount;
        assertEquals(firstCardEndBalance, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardEndBalance, dashboardPage.getSecondCardBalance());

    }
    @Test
    void transferFrom1To1() {
        int amount = 8000;
        var dashboardPage = new DashboardPage();
        var firstCardInitialAmount = dashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = dashboardPage.getSecondCardBalance();
        var transactionPage = firstCardPushButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        transactionPage.invalidCard();
    }

    @Test
    void transferMoreBalance() {
        int amount = 15000;
        var dashboardPage = new DashboardPage();
        var firstCardInitialAmount = dashboardPage.getFirstCardBalance();
        var secondCardInitialAmount = dashboardPage.getSecondCardBalance();
        var transactionPage = secondCardPushButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        transactionPage.errorLimit();

    }


}
