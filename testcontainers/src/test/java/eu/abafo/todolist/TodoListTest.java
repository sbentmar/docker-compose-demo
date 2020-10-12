package eu.abafo.todolist;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@Log
@Testcontainers
public class TodoListTest {
    @Container
    private static DockerComposeContainer environment =
            new DockerComposeContainer(new File("../docker-compose.yml"))
                    .withExposedService(
                            "backend_1",
                            5000,
                            Wait.forHttp("/todo").forStatusCode(200));

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }

    private String getBaseUrl() {
        final String hostname = environment.getServiceHost("backend_1", 5000);
        final int port = environment.getServicePort("backend_1", 5000);
        String uri = String.format("http://%s:%d/", hostname, port);
        log.info("uri: " + uri);
        return uri;
    }

    @Test
    void testShouldReturn200OnRootEndpoint() {
        final String uri = getBaseUrl();
        RestAssured.get(uri).then().statusCode(equalTo(200));

    }

    @Test
    void testShouldReturnItemsOnTodoEndpoint() {
        final String uri = getBaseUrl();
        RestAssured.get(uri + "todo").then().
                statusCode(equalTo(200)).
                body("items[0].key", equalTo("1")).
                and().
                body("items[0].value", equalTo("walk the dog"));
    }

    @Test
    void testShouldReturnNewItemWhenAdded() {
        final String uri = getBaseUrl();
        final String body = UUID.randomUUID().toString();
        given().contentType(ContentType.TEXT).body(body).post(uri + "todo").
                then().
                statusCode(200).
                and().
                body("value", equalTo(body));
    }
}
