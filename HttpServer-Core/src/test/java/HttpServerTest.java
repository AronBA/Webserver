import dev.aronba.server.HttpServer;
import dev.aronba.server.HttpServerConfigReader;
import dev.aronba.server.HttpServerState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class HttpServerTest {
    HttpServer httpServer;
    InetSocketAddress inetSocketAddress = new InetSocketAddress(8080);

    HttpServerConfigReader httpServerConfigReader = mock(HttpServerConfigReader.class);


    //make sure no HttpServer is running after the tests
    @AfterEach
    public void teardown() {
        if (httpServer == null) return;
        if (!httpServer.getState().equals(HttpServerState.TERMINATED)) {
            httpServer.stop();
        }
        this.httpServer = null;
    }

    @Test
    public void shouldSetServerStateOnStoppedBeforeStart() throws IOException {
        this.httpServer = new HttpServer(inetSocketAddress);
        assertEquals(HttpServerState.STOPPED, httpServer.getState());
    }

    @Test
    public void shouldSetServerStateRunningAfterStart() throws IOException {
        this.httpServer = new HttpServer(inetSocketAddress);
        httpServer.start();
        assertEquals(HttpServerState.RUNNING, httpServer.getState());
    }

    @Test
    public void shouldSetServerStateTerminatedAfterStop() throws IOException {
        this.httpServer = new HttpServer(inetSocketAddress);
        httpServer.start();
        httpServer.stop();
        assertEquals(HttpServerState.TERMINATED, httpServer.getState());
    }

    @Test
    public void shouldNotCreateIfInetSocketAddressIsNull() {
        assertThrows(IllegalArgumentException.class, () -> this.httpServer = new HttpServer(null));
    }

    @Test
    public void shouldNotCreateIfPortIsAlreadyInUse() throws IOException {
        HttpServer alreadyRunningServer = new HttpServer(inetSocketAddress);
        assertThrows(BindException.class, () -> this.httpServer = new HttpServer(inetSocketAddress));
    }

    @Test
    public void shouldNotCreateNewChangeDetectionServiceIfDevMode() throws IOException {
        this.httpServer = new HttpServer(inetSocketAddress,false);
        assertNull(httpServer.getChangeDetectionService());
    }
    @Test
    public void shouldCreateNewChangeDetectionServiceIfDevMode() throws IOException {
        this.httpServer = new HttpServer(inetSocketAddress,true);
        assertNotNull(httpServer.getChangeDetectionService());
    }

    @Test
    public void shouldCreateNewThreadPoolExecutorIfThereIsNone() throws IOException {
        this.httpServer = new HttpServer(inetSocketAddress,true);
        httpServer.start();
        assertNotNull(httpServer.getThreadPoolExecutor());
    }


    @Test
    public void shouldCreateNewFileSystemScannerThreadIfDevMode() throws IOException {

        HttpServerConfigReader.DEVELOPER_MODE = true;
        HttpServerConfigReader.ROOT_DIRECTORY = "";
        this.httpServer = new HttpServer(inetSocketAddress,true);

        httpServer.start();

        assertNotNull(httpServer.getFileSystemListenerThread());
        assertTrue(httpServer.getFileSystemListenerThread().isAlive());
    }



    @Test
    public void shouldStoopFileSystemListenerThreadIfDevModeIsActive() throws IOException {

        HttpServerConfigReader.DEVELOPER_MODE = true;
        HttpServerConfigReader.ROOT_DIRECTORY = "";
        httpServer = new HttpServer(inetSocketAddress,true);
        httpServer.start();
        httpServer.stop();

        assertFalse(httpServer.getFileSystemListenerThread().isAlive());
    }

    @Test
    public void shouldShutDownCorrectly() throws IOException {

        HttpServerConfigReader.DEVELOPER_MODE = true;
        HttpServerConfigReader.ROOT_DIRECTORY = "";
        httpServer = new HttpServer(inetSocketAddress,true);
        httpServer.start();
        httpServer.stop();

        assertTrue(httpServer.getDispatcherThread().isInterrupted());
        assertTrue(httpServer.getAllServerConnections().isEmpty());
        assertTrue(httpServer.getServerSocket().isClosed());
    }
}
