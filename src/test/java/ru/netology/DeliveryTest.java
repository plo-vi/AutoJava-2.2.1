package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    LocalDate date = LocalDate.now().plusDays(3);

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldShowSuccessTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldShowMeetingInfoTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Встреча успешно забронирована на ")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldShowMeetingDateInfoTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("div.notification__content").equals(date);
    }

    // negative tests

    @Test
    void shouldNotAcceptForeignCityTest() {
        $("[placeholder='Город']").setValue("Рим");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldNotAcceptCityInEnglishTest() {
        $("[placeholder='Город']").setValue("Moscow");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldNotAcceptWrongDateTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("20.03.2020");
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldNotAcceptNameInEnglishTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Ivanov Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldNotAcceptWrongPhoneTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldNotAcceptWithNoAgreementTest() {
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}