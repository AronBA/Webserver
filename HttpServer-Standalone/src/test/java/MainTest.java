import dev.aronba.Main;
import dev.aronba.server.HttpServer;
import dev.aronba.server.HttpServerState;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void shouldNotStartIfParamsIncorrect() {
        String[] args = {"this is a wrong arg"};
        assertThrows(IllegalArgumentException.class, () -> Main.main(args));
    }

    @Test
    public void shouldNotStartIfThereIsNoConfig() {
        String[] args = {"-f", "this/is/a/wrong/path"};
        Exception e = assertThrows(IOException.class, () -> Main.main(args));
        assertEquals("Could not load config", e.getMessage());

    }

    @Test
    public void shouldStartServer() throws IOException {

        File mockedConfig = new File("src/test/java/config.yml");
        mockedConfig.createNewFile();
        String someString = """
                listen: 8080
                root: 
                index: index.html
                error_logs : 
                error_page: error.html
                dev: false
                                """;
        Files.write(mockedConfig.getAbsoluteFile().toPath(), someString.getBytes(StandardCharsets.UTF_8));

        String[] args = {"-f", mockedConfig.getAbsolutePath()};

        Main.main(args);

        assertEquals(HttpServerState.RUNNING, Main.httpServer.getState());
        Main.httpServer.stop();
        assertEquals(HttpServerState.TERMINATED, Main.httpServer.getState());

    }

    @Test
    public void shouldNotStartIfPortIsAlreadyInUse() throws IOException {
        File mockedConfig = new File("src/test/java/config.yml");
        mockedConfig.createNewFile();
        String someString = """
                listen: 8080
                root: 
                index: index.html
                error_logs : 
                error_page: error.html
                dev: false
                                """;
        Files.write(mockedConfig.getAbsoluteFile().toPath(), someString.getBytes(StandardCharsets.UTF_8));

        String[] args = {"-f", mockedConfig.getAbsolutePath()};

        //simulate server already running
        HttpServer httpServer = new HttpServer(new InetSocketAddress(8080));
        httpServer.start();

        Exception e = assertThrows(IOException.class, () -> Main.main(args));
        assertTrue(e.getMessage().contains("Address already in use:"));
        //stop server
        httpServer.stop();
        assertEquals(HttpServerState.TERMINATED, httpServer.getState());
    }

}
