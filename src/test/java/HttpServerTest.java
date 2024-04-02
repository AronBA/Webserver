import dev.aronba.server.HttpServer;
import dev.aronba.server.HttpServerState;
import dev.aronba.server.http.HttpMethod;
import dev.aronba.server.requestHandler.DefaultStaticContentHandler;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class HttpServerTest {


    HttpServer httpServer;



    @Test
    public void shouldSetServerStateOnStoppedBeforeStart() {
        assertEquals(HttpServerState.STOPPED, httpServer.getState());
    }
    @Test
    public void shouldSetServerStateOnRunningAfterStart() {
        httpServer.start();
        assertEquals(HttpServerState.RUNNING, httpServer.getState());
    }
    @Test
    public void shouldSetServerStateOnTerminatedAfterShutdown() {
        httpServer.stop();
        assertEquals(HttpServerState.TERMINATED, httpServer.getState());
    }

    @Test
    public void shouldAddNewRequestMappingToServerEndpoint(){
        httpServer.addRequestMapping(HttpMethod.POST,"", new DefaultStaticContentHandler());
        assertEquals(httpServer.getHttpHandlerMap().size(),1);
    }

    @Test
    public void shouldNotAddNewRequestMappingIfAnotherMappingAlreadyExists(){

        httpServer.addRequestMapping(HttpMethod.POST,"", new DefaultStaticContentHandler());

        assertThrows(IllegalAccessException.class, () -> httpServer.addRequestMapping(HttpMethod.POST,"", new DefaultStaticContentHandler()));


        assertEquals(httpServer.getHttpHandlerMap().size(),1);
    }



}
