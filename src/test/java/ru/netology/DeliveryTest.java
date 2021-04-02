package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

//    private WebDriver driver;

    public String deliveryDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 3);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }
//
//    @AfterEach
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

    @Test
    void shouldAcceptTest() {
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
        $("[placeholder='Город']").setValue("Рим");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
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
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
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
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
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
        $("[placeholder='Дата встречи']").setValue(deliveryDate());
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("77777777777");
        $(".checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}