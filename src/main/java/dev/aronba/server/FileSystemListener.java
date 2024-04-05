package dev.aronba.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.Arrays;

public class FileSystemListener implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemListener.class);
    private final HttpServer httpServer;

    FileSystemListener(HttpServer httpServer) {
        this.httpServer = httpServer;
    }
    @Override
    public void run() {
        try {
            LOG.info("started listing for file changes");
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(HttpServerConfigReader.ROOT_DIRECTORY);

            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            WatchKey key;
            while ((key = watchService.take()) != null) {

                Object lastEvent = null;
                for (WatchEvent<?> event : key.pollEvents()) {
                    LOG.info("Change detected on: " + event.context());
                    if (lastEvent == null || !event.context().equals(lastEvent) ){
                        this.httpServer.scheduleHotReload();
                    }
                    lastEvent = event.context();

                }
                key.reset();
            }

        } catch (Exception e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
