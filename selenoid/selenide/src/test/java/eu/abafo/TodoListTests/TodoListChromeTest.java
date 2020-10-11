package eu.abafo.TodoListTests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TodoListChromeTest extends TestBase {

    @BeforeEach
    public void setUp() {
        setUpBase();
        Configuration.browserCapabilities.setCapability("browserName", "chrome");
        Configuration.browser = "chrome";
        Configuration.browserCapabilities.setCapability("name", Configuration.browser+UUID.randomUUID().toString());
    }

    @Test
    public void testAddNewItemInChrome() {
        addNewItem();
    }

}
