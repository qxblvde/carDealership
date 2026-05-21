package ru.sivak.infrastructure.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import ru.sivak.integration.grpc.AvailableCarGrpcService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GrpcServerLifecycle implements SmartLifecycle {

    private final AvailableCarGrpcService availableCarGrpcService;

    @Value("${grpc.server.port:9091}")
    private int port;

    private Server server;
    private boolean running;

    @Override
    public void start() {
        try {
            server = ServerBuilder.forPort(port)
                    .addService(availableCarGrpcService)
                    .build()
                    .start();
            running = true;
            log.info("gRPC server started on port={}", port);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to start gRPC server", exception);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            server.shutdown();
            log.info("gRPC server stopped");
        }
        running = false;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
