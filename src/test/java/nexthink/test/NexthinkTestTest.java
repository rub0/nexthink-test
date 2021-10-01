package nexthink.test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import nexthink.test.Services.SwapiService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import reactor.test.StepVerifier;

@MicronautTest
class NexthinkTestTest {
    private EmbeddedServer server;

    @Inject
    SwapiService service;

    @Before
    public void setup()
    {
        server = ApplicationContext.run(EmbeddedServer.class);
    }

    @After
    public void cleanup()
    {
        server.stop();
    }


    @Test
    void t(){
        Assertions.assertTrue(true);
    }


    @Test
    void testOKName() {
        StepVerifier.create(service.getStarships("luke"))
        .expectNextCount(2)
        .verifyComplete();
    }

    @Test
    void testNomatches() {
        StepVerifier.create(service.getStarships("dsffdg"))
                .verifyError();
    }

}
