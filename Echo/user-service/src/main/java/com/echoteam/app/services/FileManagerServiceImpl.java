package com.echoteam.app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.echoteam.app.utils.PathUtilities.getClasspath;

@Service
public class FileManagerServiceImpl implements FileManagerService {

    @Override
    public byte[] download(String filename) throws IOException {
        Path absolute = getClasspath().resolve(filename);

        if (Files.exists(absolute)) {
            return Files.readAllBytes(absolute);
        } else {
            throw new EntityNotFoundException("File with name: %s not found.".formatted(filename));
        }
    }

    @Override
    public void upload(String filepath, MultipartFile fileToSave) throws IOException {
        Path pathToFile = getClasspath().resolve(Paths.get(filepath));
        File directory = pathToFile.getParent().toFile();

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("Directory wasn't created.");
            }
        }

        fileToSave.transferTo(pathToFile);
    }

    @Override
    public void delete(String filepath) throws IOException {
        Path absolute = getClasspath().resolve(filepath);
        Files.delete(absolute);
    }

}
