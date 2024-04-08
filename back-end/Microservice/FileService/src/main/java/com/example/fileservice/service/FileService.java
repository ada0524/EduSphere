package com.example.fileservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.fileservice.azure.AzureBlobStorageProvider;
import com.example.fileservice.entity.Attachment;
import com.example.fileservice.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private AzureBlobStorageProvider azureBlobStorageProvider;

    public String uploadUserProfile(byte[] image) throws IOException {
        BlobContainerClient blobContainerClient = azureBlobStorageProvider.getBlobContainerClient();
        String objectKey = UUID.randomUUID().toString(); // Generate a unique key for the object
        BlobClient blobClient = blobContainerClient.getBlobClient(objectKey);
        blobClient.upload(new ByteArrayInputStream(image), image.length);
        return objectKey;
    }

    public String getProfileImage(String imageKey) throws IOException {
        BlobContainerClient blobContainerClient = azureBlobStorageProvider.getBlobContainerClient();
        BlobClient blobClient = blobContainerClient.getBlobClient(imageKey);
        if (!blobClient.exists()) {
            throw new FileNotFoundException("No such file");
        }
        return blobClient.getBlobUrl();
    }

    public List<String> uploadPostFiles(List<byte[]> files) throws IOException {
        BlobContainerClient blobContainerClient = azureBlobStorageProvider.getBlobContainerClient();
        List<String> fileKeys = new ArrayList<>();
        for (byte[] file : files) {
            String imageKey = UUID.randomUUID().toString(); // Generate a unique key for the object
            BlobClient blobClient = blobContainerClient.getBlobClient(imageKey);
            blobClient.upload(new ByteArrayInputStream(file), file.length);
            fileKeys.add(imageKey);
        }
        return fileKeys;
    }

    public List<String> getPostImages(List<String> imageKeys) throws IOException {
        BlobContainerClient blobContainerClient = azureBlobStorageProvider.getBlobContainerClient();
        List<String> imageURLs = new ArrayList<>();
        for (String imageKey : imageKeys) {
            BlobClient blobClient = blobContainerClient.getBlobClient(imageKey);
            if (!blobClient.exists()) {
                throw new FileNotFoundException("No such file");
            }
            imageURLs.add(blobClient.getBlobUrl());
        }
        return imageURLs;
    }

    public List<Attachment> getPostAttachments(List<String> attachmentKeys) throws IOException {
        BlobContainerClient blobContainerClient = azureBlobStorageProvider.getBlobContainerClient();
        List<Attachment> attachments = new ArrayList<>();
        for (String attachmentKey : attachmentKeys) {
            BlobClient blobClient = blobContainerClient.getBlobClient(attachmentKey);
            if (!blobClient.exists()) {
                throw new FileNotFoundException("No such file");
            }
            attachments.add(Attachment.builder()
                    .url(blobClient.getBlobUrl())
                    .type(blobClient.getProperties().getContentType())
                    .build());
        }
        return attachments;
    }
}
