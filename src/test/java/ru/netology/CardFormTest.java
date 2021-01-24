package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardFormTest {

    @BeforeEach
    void serUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        $("[data-test-id=city] input").setValue("Ка");
        $(byText("Казань")).click();
        $("[data-test-id=date] input").click();
        SelenideElement frame = $(".calendar-input__calendar-wrapper ~ iframe");
        LocalDate deliveryDate = LocalDate.now().plusDays(3);
        LocalDate newDate = deliveryDate.plusDays(4);
        if (deliveryDate.getMonth() != newDate.getMonth())
            $("[data-step='1']").click();
        int day = newDate.getDayOfMonth();
        $$(".calendar__day").get(day - 1).click();
        $("[name=name]").setValue("Кашин Василий");
        $("[name=phone]").setValue("+79270000000");
        $(".checkbox__box").click();
        $(".button").click();
        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
    }
}
