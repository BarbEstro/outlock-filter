package com.example.outlookfilter.service;

import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class OutlookService {

    private static final Logger log = LoggerFactory.getLogger(OutlookService.class);
    private final GraphServiceClient graphServiceClient;

    public OutlookService(GraphServiceClient graphServiceClient){
        this.graphServiceClient = graphServiceClient;
    }

    public void testConnection() {
        try {
            var user = graphServiceClient.me().get();
            log.info("Accesso riuscito! Benvenuto {} ({})",
                    user.getDisplayName(),
                    user.getUserPrincipalName());

        } catch (Exception e) {
            log.error("Accesso fallito o rifiutato: {}", e.getMessage());
        }
    }



}