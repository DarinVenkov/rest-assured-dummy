package bg.venkov.darin.client;

import static com.jayway.restassured.RestAssured.given;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ConnectionConfig;
import com.jayway.restassured.specification.RequestSpecification;

public class ConnectionTest {

    private static final String URL = "http://localhost:8080/RestAssuredServiceDummy/rest/dummy/person";

    private RequestSpecification request = null;

    @Before
    public void setUp() throws Exception {
        request = given();
    }

    @Test
    public void OpenedConnectionsTestXML() {
        try {
            request.contentType("application/xml").accept("application/xml").when().get(URL).asString();
        } catch (Exception e) {
            fail("Didn't expect an exception but got: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    @Test
    public void OpenedConnectionsTestJson() {
        try {
            request.contentType("application/json").accept("application/json").when().get(URL).asString();
        } catch (Exception e) {
            fail("Didn't expect an exception but got: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    @Test(expected = org.apache.http.ConnectionClosedException.class)
    public void ClosedConnectionsTestXML() {

        request.config(RestAssured.config().connectionConfig(new ConnectionConfig().closeIdleConnectionsAfterEachResponse()));

        request.contentType("application/xml").accept("application/xml").when().get(URL).asString();

        fail("Expect org.apache.http.ConnectionClosedException exception!");
    }

    @Test(expected = org.apache.http.ConnectionClosedException.class)
    public void ClosedConnectionsTestJson() {

        request.config(RestAssured.config().connectionConfig(new ConnectionConfig().closeIdleConnectionsAfterEachResponse()));

        request.contentType("application/json").accept("application/json").when().get(URL).asString();

        fail("Expect org.apache.http.ConnectionClosedException exception!");
    }

}
