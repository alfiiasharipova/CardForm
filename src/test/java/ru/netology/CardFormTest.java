package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFormTest {

    @BeforeEach
    void serUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() throws ParseException {
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
        Pattern pattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4}");
        Matcher matcher = pattern.matcher($(".notification__content").getText());
        String actual = "";
        if(matcher.find())
            actual = matcher.group();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date actualDate= format.parse(actual);
        Date expectedDate = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertEquals(expectedDate, actualDate);
    }
}
