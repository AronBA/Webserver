package dev.aronba.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

public class ChangeDetectionService implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ChangeDetectionService.class);
    private final HttpServer httpServer;

    ChangeDetectionService(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void run() {
        try {
            LOG.info("started listing for file changes");
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(HttpServerConfigReader.ROOT_DIRECTORY);

            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);


            while (httpServer.getState().equals(HttpServerState.RUNNING)) {

                WatchKey key;

                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    LOG.error("Watch service was interrupted");
                    continue;
                }

                if (key == null) {
                    LOG.warn("Key should not be null");
                    continue;
                }

                if (!key.isValid()) {
                    LOG.error("Invalid watch key");
                    continue;
                }
                Object lastEvent = null;
                for (WatchEvent<?> event : key.pollEvents()) {
                    LOG.info("Change detected on: " + event.context());
                    if (lastEvent == null || !event.context().equals(lastEvent)) {
                        this.httpServer.scheduleHotReload();
                    }
                    lastEvent = event.context();

                }
                key.reset();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
