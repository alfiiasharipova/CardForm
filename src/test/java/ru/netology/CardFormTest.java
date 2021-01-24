package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardFormTest {

    @BeforeEach
    void serUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        $("[data-test-id=city] input").setValue("Казань");
        SelenideElement date = $("[data-test-id=date] input");
        date.sendKeys(Keys.CONTROL, "A");
        date.sendKeys(Keys.DELETE);
        String newDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        date.setValue(newDate);
        $("[name=name]").setValue("Кашин Василий");
        $("[name=phone]").setValue("+79270000000");
        $(".checkbox__box").click();
        $(".button").click();
        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
    }
}
