package bg.venkov.darin.client;

import static com.jayway.restassured.RestAssured.given;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ConnectionConfig;

public class ConnectionTest {

    private static final String URL = "http://localhost:8080/rest/dummy/person";

    @Before
    public void setUp() throws Exception {
        // This is required since we make a lot of consecutive requests with small response bodies.
        // If we omit this option, suddenly hundreds of inet sockets are being opened in a small window of time.
        // These sockets then are never closed (they stay in CLOSE_WAIT state)
        // See: http://blogs.technet.com/b/janelewis/archive/2010/03/09/explaining-close-wait.aspx for more info
        RestAssured.config = RestAssured.config().connectionConfig(new ConnectionConfig().closeIdleConnectionsAfterEachResponse());
    }

    @After
    public void tearDown() throws Exception {
        RestAssured.reset();
    }

    // Fails with "org.apache.http.MalformedChunkCodingException: CRLF expected at end of chunk"
    // Instead, this MUST complete successfully, without opening 1000 sockets.
    @Test
    public void loop1000RequestsOpenSocketsJson() throws InterruptedException
    {
        for (int i = 0; i < 1000; i++)
        {
            given().contentType("application/json").accept("application/json").when().get(URL).asString();
        }
        Thread.sleep(10000);
    }

    // This works as expected. If the content type is application/xml, the entity is consumed correctly.
    @Test
    public void loop1000RequestsOpenSocketsXml() throws InterruptedException
    {
        for (int i = 0; i < 1000; i++)
        {
            given().contentType("application/xml").accept("application/xml").when().get(URL).asString();
        }
        Thread.sleep(10000);
    }
}