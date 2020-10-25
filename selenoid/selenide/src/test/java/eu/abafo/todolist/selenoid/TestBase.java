package eu.abafo.todolist.selenoid;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;

import java.util.UUID;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase {
    protected final String BASE_URL = System.getenv("BASEURL");

    public void setUpBase() {
        Configuration.remote = "http://127.0.0.1:4444/wd/hub";
        Configuration.browserCapabilities.setCapability("enableVNC", true);
        Configuration.browserCapabilities.setCapability("enableVideo", true);
    }

    @AfterEach
    public void tearDown() {
        getWebDriver().quit();
    }

    public void addNewItem() {
        open(BASE_URL);
        $("#new-todo-item-text").waitUntil(Condition.visible, 5000l);
        final String newItemValue = UUID.randomUUID().toString();

        $("#new-todo-item-text").setValue(newItemValue);
        $("#add-todo-item").click();

        $$(".list-item span").shouldHave(itemWithText(newItemValue));
    }

}
