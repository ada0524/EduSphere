package com.example.fileservice.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AzureBlobStorageProvider {

    @Value("${azure.storage.connectionString}")
    private String connectionString;

    @Value("${azure.storage.containerName}")
    private String containerName;

    private BlobContainerClient blobContainerClient;

    public BlobContainerClient getBlobContainerClient() {
        if (blobContainerClient == null) {
            blobContainerClient = new BlobServiceClientBuilder().connectionString(connectionString)
                    .buildClient().getBlobContainerClient(containerName);
        }
        return blobContainerClient;
    }
}