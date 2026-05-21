package ru.sivak.infrastructure.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sivak.contract.availablecar.AvailableCarServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Bean(destroyMethod = "shutdownNow")
    public ManagedChannel availableCarChannel(
            @Value("${storage.grpc.host:localhost}") String host,
            @Value("${storage.grpc.port:9091}") int port
    ) {
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public AvailableCarServiceGrpc.AvailableCarServiceBlockingStub availableCarServiceBlockingStub(
            ManagedChannel availableCarChannel
    ) {
        return AvailableCarServiceGrpc.newBlockingStub(availableCarChannel);
    }
}
