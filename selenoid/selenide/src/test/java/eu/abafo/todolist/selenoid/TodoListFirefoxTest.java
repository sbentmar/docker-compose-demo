package eu.abafo.todolist.selenoid;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TodoListFirefoxTest extends TestBase {

    @BeforeEach
    public void setUp() {
        setUpBase();
        Configuration.browserCapabilities.setCapability("browserName", "firefox");
        Configuration.browser = "firefox";
        Configuration.browserCapabilities.setCapability("name", Configuration.browser+UUID.randomUUID().toString());
    }

    @Test
    public void testAddNewItemInFirefox() {
        addNewItem();
    }
}
