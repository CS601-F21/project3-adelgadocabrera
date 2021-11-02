package com.cs601.project3.amazon;

import com.cs601.project3.HtmlValidator;
import com.cs601.project3.clientRequest.ClientRequest;
import com.cs601.project3.clientRequest.ClientResponse;
import com.cs601.project3.server.models.HttpStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

class AmazonSearchTest {
    // constants
    int PORT = 8080;
    String DOMAIN = "http://localhost:" + PORT;
    String SHUTDOWN_PATH = "/shutdown";
    String URL = DOMAIN + SHUTDOWN_PATH;
    public static String[] args;
    Thread serverThread;

    @BeforeAll
    public static void setUp() {
        args = new String[4];
        args[0] = "-reviews";
        args[1] = "mock_reviews.txt";
        args[2] = "-qa";
        args[3] = "mock_qa.txt";
    }

    @BeforeEach
    public void setUpBeforeEach() {
        serverThread = new Thread(() -> AmazonSearch.main(args));
        serverThread.start();
    }

    @AfterEach
    public void cleanUp() throws InterruptedException {
        AmazonSearch.shutdown();
        serverThread.join();
    }

    @Test
    @DisplayName("should shutdown server on Get request to /shutdown endpoint")
    void throwsOnServerShutdown() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        try {
            ClientRequest.get(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertThrows(ConnectException.class, () -> ClientRequest.get(URL));
    }

    @Test
    @DisplayName("should return correct headers/body on shutdown call")
    void headers() throws IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        ClientResponse res = ClientRequest.get(URL);

        Assertions.assertEquals(200, res.statusCode);
        Assertions.assertTrue(HtmlValidator.isValid(res.body));
        Assertions.assertEquals(HttpStatus.VERSION, res.protocol);
    }
}