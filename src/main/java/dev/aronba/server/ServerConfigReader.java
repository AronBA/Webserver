package dev.aronba.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class ServerConfigReader {

    public static final String DEFAULT_NOT_FOUND_BODY = "<html>" + "<header>" + "<title>" + "404 Not Found" + "</title>" + "</header>" + "<body>" + "<h1> 404 Here is nothing </h1>" + "</body>" + "</html>";
    private static final Logger LOG = LoggerFactory.getLogger(ServerConfigReader.class);
    public static int PORT = 0;
    public static String ROOT_DIRECTORY = null;
    public static String[] VIABLE_INDEX_FILES;
    public static String ERROR_LOG_PATH;
    public static String DEFAULT_ERROR_PAGE_PATH;
    private final String configPath;


    public ServerConfigReader(String configPath) {
        this.configPath = configPath;
    }

    public static void printConfig() {
        System.out.println("listenPort: " + PORT);
        System.out.println("rootDirectory: " + ROOT_DIRECTORY);
        System.out.print("indexFiles: ");
        for (String file : VIABLE_INDEX_FILES) {
            System.out.print(file + " ");
        }
        System.out.println();
        System.out.println("errorLogPath: " + ERROR_LOG_PATH);
        System.out.println("errorPage: " + DEFAULT_ERROR_PAGE_PATH);
    }

    public void loadConfig() throws IOException {
        try {
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream(this.configPath);
            Map<String, Object> config = yaml.load(fis);

            PORT = (int) config.get("listen");
            ROOT_DIRECTORY = (String) config.get("root");
            VIABLE_INDEX_FILES = ((String) config.get("index")).split(" ");
            ERROR_LOG_PATH = (String) config.get("error_logs");
            DEFAULT_ERROR_PAGE_PATH = (String) config.get("error_page");

            fis.close();
        } catch (FileNotFoundException e) {
            LOG.error("Configuration file not found. Searched path: " + this.configPath);
            throw e;
        } catch (Exception e) {
            LOG.error("Error loading configuration: " + e.getMessage());
            throw e;
        }
    }
}
