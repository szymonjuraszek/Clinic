package com.szymon.demo.service.file;

@FunctionalInterface
public interface ImageFileHandler {
    void save(String title, byte[] image, String extension, Long timestamp);
}
