package com.example.outlookfilter.config;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfig {

    @Value("${azure.client-id}")
    private String clientId;
    @Value("${azure.tenant-id}")
    private String tenantId;
    @Value("${azure.scopes}")
    private String[] scopes;

    @Bean
    public GraphServiceClient getGraphServiceClient(){
        DeviceCodeCredential credential = new DeviceCodeCredentialBuilder()
                .clientId(clientId)
                .tenantId(tenantId)
                .challengeConsumer(challange -> System.out.println(challange.getMessage()))
                .build();
        return new GraphServiceClient(credential,scopes);
    }
}
