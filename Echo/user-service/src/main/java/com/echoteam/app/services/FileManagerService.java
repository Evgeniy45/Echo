package com.echoteam.app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManagerService {

    byte[] download(String fileName) throws IOException;
    void upload(String filepath, MultipartFile fileToSave) throws IOException;
    void delete(String filepath) throws IOException;
}
