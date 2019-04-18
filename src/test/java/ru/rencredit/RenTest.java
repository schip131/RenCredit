package ru.rencredit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RenTest {
    private ChromeDriver driver;
    Conn db = new Conn();
    String step;

    @BeforeSuite
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        db.connectDB("localhost", "DB_rencredit", "rencredit", "rencredit");
    }

    @Test
    public void depositTest() {
        // 1) Открыть сайт rencredit.ru
        step = "Открываем сайт https://rencredit.ru";
        Timestamp dateStart = Timestamp.valueOf(LocalDateTime.now());
        driver.get("https://rencredit.ru");
        Timestamp dateEnd = Timestamp.valueOf(LocalDateTime.now());
        db.addStepDB(step, dateStart, dateEnd);
        LogUtil.log(step);

        // 2) Перейти на страницу "Вклад"
        step = "Переходим на страницу \"Вклад\"";
        dateStart = Timestamp.valueOf(LocalDateTime.now());
        WebElement title = driver.findElement(By.className("services_main"));
        title.findElement(By.cssSelector("[href=\"/contributions/\"]")).click();
        dateEnd = Timestamp.valueOf(LocalDateTime.now());
        db.addStepDB(step, dateStart, dateEnd);
        LogUtil.log(step);

        // 3) Выбрать чекбокс "В отделении банка"
        step = "Выбираем чекбокс \"В отделении банка\"";
        dateStart = Timestamp.valueOf(LocalDateTime.now());
        driver.findElement(By.cssSelector(".calculator__check-block")).click();
        dateEnd = Timestamp.valueOf(LocalDateTime.now());
        db.addStepDB(step, dateStart, dateEnd);
        LogUtil.log(step);

        // 4) Ввести сумму вклада
        step = "Вводим сумму вклада";
        dateStart = Timestamp.valueOf(LocalDateTime.now());
        driver.findElement(By.name("amount")).sendKeys("500000");
        dateEnd = Timestamp.valueOf(LocalDateTime.now());
        db.addStepDB(step, dateStart, dateEnd);
        LogUtil.log(step);

        // 5) Передвинуть ползунок "На срок"
        step = "Передвигаем ползунок \"На срок\"";
        dateStart = Timestamp.valueOf(LocalDateTime.now());
        WebElement blockPeriod = driver.findElement(By.cssSelector("div[data-property=period]"));
        WebElement slider = blockPeriod.findElement(By.cssSelector(".ui-slider-handle"));
        Actions actions = new Actions(driver);
        WebElement target = blockPeriod.findElement(By.cssSelector(".range-scale__item_3"));
        actions.dragAndDrop(slider, target).perform();
        dateEnd = Timestamp.valueOf(LocalDateTime.now());
        db.addStepDB(step, dateStart, dateEnd);
        LogUtil.log(step);

        // 6) Выгрузить Печатную Форму "Общие условия по вкладам"
        step = "Выгружаем Печатную Форму \"Общие условия по вкладам\"";
        dateStart = Timestamp.valueOf(LocalDateTime.now());
        WebElement depositFile = driver.findElement(By.className("document__title"));
        depositFile.findElement(By.cssSelector("[href=\"/upload/iblock/0eb/obshchie-usloviya-po-vkladam-i-schetam_02.04.2018.pdf\"]")).click();
        dateEnd = Timestamp.valueOf(LocalDateTime.now());
        db.addStepDB(step, dateStart, dateEnd);
        LogUtil.log(step);
    }

    @Test
    public void cardTest() {
        // 1) Открыть сайт rencredit.ru
        driver.get("https://rencredit.ru");
        LogUtil.log("Открываем сайт https://rencredit.ru");

        // 2) Перейти на страницу "Карта"
        WebElement title = driver.findElement(By.cssSelector(".service:nth-child(3)"));
        title.findElement(By.cssSelector("[href=\"/app/card/cc_full\"]")).click();
        LogUtil.log("Переходим на страницу \"Карта\"");

        // 3) Ввести в поля "Фамилия", "Имя", "Мобильный телефон", "e-mail" значения
        driver.findElement(By.id("t1")).sendKeys("Я есть Грут");
        driver.findElement(By.id("t2")).sendKeys("Я есть Грут");
        driver.findElement(By.id("t4")).sendKeys("9998881111");
        driver.findElement(By.id("t38")).sendKeys("IAmGroot@GuardiansOfTheGalaxy.com");
        LogUtil.log("Вводим в поля \"Фамилия\", \"Имя\", \"Мобильный телефон\", \"e-mail\" значения");

        // 4) Выбрать чекбокс "Нет отчества"
        driver.findElement(By.className("js-validation-ignore-checkbox")).click();
        LogUtil.log("Выбираем чекбокс \"Нет отчества\"");

        // 5) Выбрать значение из выпадающего списка "Где вы желаете получить карту"
        WebElement selectRegion = driver.findElement(By.id("s2"));
        Select region = new Select(selectRegion);
        region.selectByValue("358");
        WebElement selectCity = driver.findElement(By.id("s3"));
        Select city = new Select(selectCity);
        city.selectByValue("12225");
        LogUtil.log("Выбираем значение из выпадающего списка \"Где вы желаете получить карту\"");
    }

    @AfterSuite
    public void close() {
        driver.quit();
        db.closeDB();
    }
}
