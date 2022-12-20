package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderDeliveryChangeDateTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:7777/");
    }

    @Test
    void positiveRegistrationTest() {

        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("ru");
        String planningDate = DataGenerator.generateDate(3);
        String newPlanningDate = DataGenerator.generateDate(10);

        $("[data-test-id='city'] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(ClearField.deleteString);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $(".checkbox__box").click();
        $x("//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate));

        $("[data-test-id='city'] input").setValue(ClearField.deleteString);
        $("[data-test-id='city'] input").setValue(user.getCity());
        $("[data-test-id='date'] input").sendKeys(ClearField.deleteString);
        $("[data-test-id='date'] input").setValue(newPlanningDate);
        $("[data-test-id='name'] input").setValue(ClearField.deleteString);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(ClearField.deleteString);
        $("[data-test-id='phone'] input").setValue(user.getPhone());
        $x("//span[contains(text(),'Запланировать')]").click();
        $x(".//div [contains(text(), 'У вас уже запланирована встреча на другую дату. Перепланировать?')]")
                .shouldBe(visible, Duration.ofSeconds(15));
        $x(".//span[contains(text(), 'Перепланировать')]").click();
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + newPlanningDate));


    }
}




