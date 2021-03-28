package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    public String deliveryDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 3);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    @Test
    void shouldAcceptTest() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    // negative tests

    @Test
    void shouldNotAcceptForeignCityTest() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Рим");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("div:nth-child(1) > div > span > span > span.input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotAcceptCityInEnglishTest() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Moscow");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("div:nth-child(1) span.input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotAcceptWrongDateTest() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue("20.03.2020");
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("div:nth-child(2) > span > span > span > span > span.input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotAcceptNameInEnglishTest() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
        $("[name='name']").setValue("Ivanov Иван");
        $("[name='phone']").setValue("+77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("div:nth-child(3) > span > span > span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotAcceptWrongPhoneTest() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("div:nth-child(4) > span > span > span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}