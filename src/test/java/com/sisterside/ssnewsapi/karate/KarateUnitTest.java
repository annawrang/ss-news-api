import com.github.tomakehurst.wiremock.WireMockServer;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate")
public class KarateUnitTest {

    private static WireMockServer wireMockServer = new WireMockServer();

    @BeforeClass
    public static void before(){
        wireMockServer.start();
        configureFor("localhost", 8080);
        stubFor(
                get(urlEqualTo("/posts/001"))
                .willReturn(aResponse()
                .withStatus(200)
                .withBody("{ \"postNumber\": \"001\" }"))
        );

        stubFor(
                post(urlEqualTo("/posts"))
                .withRequestBody(containing("postNumber"))
                .willReturn(aResponse()
                .withStatus(200)
                .withBody("{ \"postNumber\": \"001\"}"))
        );
    }

    @AfterClass
    public static void after(){
        wireMockServer.stop();
    }
}
