package ru.netology.CardDelivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
//    private LocalDate today = LocalDate.now();
//    private LocalDate addDays = today.plusDays(addDays);
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//    private String formatDate = addDays.format(formatter);

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void deliveryCard() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String date = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Кирсанов Андрей");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification_visible")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно"));
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date));

    }
}
