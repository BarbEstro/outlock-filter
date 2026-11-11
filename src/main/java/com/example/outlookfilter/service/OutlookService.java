package com.example.outlookfilter.service;

import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class OutlookService {

    private static final Logger log = LoggerFactory.getLogger(OutlookService.class);
    private final GraphServiceClient graphServiceClient;

    public OutlookService(GraphServiceClient graphServiceClient){
        this.graphServiceClient = graphServiceClient;
    }

    public void testConnection() {
        try {
            User user = graphServiceClient.me().get();
            log.info("Accesso riuscito! Benvenuto {} ({})",
                    user.getDisplayName(),
                    user.getUserPrincipalName());

        } catch (Exception e) {
            log.error("Accesso fallito o rifiutato: {}", e.getMessage());
        }
    }

    public void downloadAttachment() {
        Path rootFolder = Path.of(
                System.getProperty("user.home"),
                "outlookfilter"
        );

        MessageCollectionResponse messageCollectionResponse = graphServiceClient.me().messages().get();
        if (messageCollectionResponse == null || messageCollectionResponse.getValue() == null) {
            return;
        }

        messageCollectionResponse.getValue().stream()
                .filter(message -> Boolean.FALSE.equals(message.getIsRead()))
                .filter(message -> Boolean.TRUE.equals(message.getHasAttachments()))
                .forEach(message -> processMessageAttachments(message, rootFolder));
    }

    private void processMessageAttachments(Message message, Path rootFolder) {
        if (message.getId() == null) {
            return;
        }

        AttachmentCollectionResponse attachmentResponse = graphServiceClient.me()
                .messages()
                .byMessageId(message.getId())
                .attachments()
                .get();

        if (attachmentResponse == null || attachmentResponse.getValue() == null) {
            return;
        }

        String senderName = extractSenderName(message);
        Path senderFolder = rootFolder.resolve(sanitizeName(senderName)).resolve(LocalDate.now().toString());

        for (Attachment attachment : attachmentResponse.getValue()) {
            if (!Boolean.TRUE.equals(attachment.getIsInline()) && attachment instanceof FileAttachment fileAttachment) {
                saveFileAttachment(fileAttachment, senderFolder);
            }
        }
    }

    private void saveFileAttachment(FileAttachment fileAttachment, Path targetFolder) {
        String fileName = fileAttachment.getName();
        byte[] content = fileAttachment.getContentBytes();

        if (fileName == null || content == null) {
            return;
        }

        try {
            Files.createDirectories(targetFolder);
            Path destinationFile = targetFolder.resolve(sanitizeName(fileName)).normalize();
            if (!destinationFile.startsWith(targetFolder.normalize())) {
                log.warn("Percorso allegato non sicuro ignorato: {}", fileName);
                return;
            }
            Files.write(destinationFile, content);
            log.info("Allegato salvato: {}", destinationFile);
        } catch (IOException e) {
            log.error("Errore durante il salvataggio di {}: {}", fileName, e.getMessage());
        }
    }

    private String extractSenderName(Message message) {
        return Optional.ofNullable(message.getFrom())
                .map(Recipient::getEmailAddress)
                .map(EmailAddress::getName)
                .orElse("sconosciuto");
    }

    private String sanitizeName(String rawName) {
        String sanitized = rawName.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
        if (sanitized.isEmpty() || ".".equals(sanitized) || "..".equals(sanitized)) {
            return "sconosciuto";
        }
        return sanitized;
    }
}