package com.example.fileservice.exception;

import com.azure.core.exception.AzureException;

public class FileNotFoundException extends AzureException {

        public FileNotFoundException(String message) {
            super(message);
        }
}
