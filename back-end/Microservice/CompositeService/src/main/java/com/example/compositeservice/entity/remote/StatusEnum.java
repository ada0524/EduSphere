package com.example.compositeservice.entity.remote;

public enum StatusEnum {
    UNPUBLISHED("Unpublished"),
    PUBLISHED("Published"),
    HIDDEN("Hidden"),
    BANNED("Banned"),
    DELETED("Deleted");

    private final String stageName;

    StatusEnum(String stageName) {
        this.stageName = stageName;
    }

    public String getStatusEnum() {
        return stageName;
    }
}
