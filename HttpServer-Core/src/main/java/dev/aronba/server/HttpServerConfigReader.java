package dev.aronba.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class HttpServerConfigReader {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerConfigReader.class);
    public static int PORT = 0;
    public static String ROOT_DIRECTORY = null;
    public static boolean DEVELOPER_MODE;
    public static String INDEX_FILE;
    public static String ERROR_LOG_PATH;
    public static String DEFAULT_ERROR_PAGE_PATH;
    private final String configPath;


    public HttpServerConfigReader(String configPath) {
        this.configPath = configPath;
    }


    public static String getCurrentConfigAsString() {
        return "\n|+++++| SERVER CONFIG |+++++|\n" +
                "port: " + PORT + "\n" +
                "rootDirectory: " + ROOT_DIRECTORY + "\n" +
                "viable indexFiles: " + INDEX_FILE + "\n" +
                "errorLogPath: " + ERROR_LOG_PATH + "\n" +
                "errorPage: " + DEFAULT_ERROR_PAGE_PATH + "\n" +
                "developer mode: " + DEVELOPER_MODE + "\n" +
                "|+++++++++++++++++++++++++++|";
    }


    public void loadConfig() throws IOException {
        try {
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream(this.configPath);
            Map<String, Object> config = yaml.load(fis);

            PORT = (int) config.get("listen");
            ROOT_DIRECTORY = (String) config.get("root");
            INDEX_FILE = (String) config.get("index");
            ERROR_LOG_PATH = (String) config.get("error_logs");
            DEFAULT_ERROR_PAGE_PATH = (String) config.get("error_page");
            DEVELOPER_MODE = (boolean) config.get("dev");
            fis.close();
            LOG.info(HttpServerConfigReader.getCurrentConfigAsString());
        } catch (FileNotFoundException e) {
            LOG.error("Configuration file not found. Searched path: " + this.configPath);
            throw e;
        } catch (Exception e) {
            LOG.error("Error loading configuration: " + e.getMessage());
            throw e;
        }
    }
}
