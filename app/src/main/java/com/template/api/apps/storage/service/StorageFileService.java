package com.template.api.apps.storage.service;

import com.google.common.net.HttpHeaders;
import com.template.api.apps.storage.dto.FileSummary;
import com.template.api.apps.storage.exceptions.StorageException;
import com.template.api.apps.storage.exceptions.StorageFileNotFoundException;
import com.template.api.apps.storage.properies.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.*;

import static java.nio.file.Files.createDirectory;

@Profile({"file"})
@RequiredArgsConstructor
@Service("storageFileService")
@Slf4j
public class StorageFileService implements StorageService {

    private final Path rootLocation;
    private final StorageProperties storageProperties;

    @Autowired
    public StorageFileService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.storageProperties = properties;
    }

    @Override
    public void store(MultipartFile file, Path path, boolean isSecret) throws Exception {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }

        if (filename.contains("..")) {
            throw new StorageException(
                    "Cannot store file with relative path outside current directory " + filename);
        }

        Path target = this.rootLocation.resolve(path);
        if (Files.isDirectory(target)) {
            throw new StorageException("The path is not a file " + target);
        }

        createDirectory(target.getParent());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public ResponseEntity getResponseEntity(FileSummary fs, boolean secret) {
        try {

            Path target = this.rootLocation.resolve(fs.getPath());
            if (Files.isDirectory(target)) {
                throw new StorageException("The path is not a file " + target);
            }

            Resource resource = new UrlResource(target.toUri());
            if (resource.exists() || resource.isReadable()) {
                String encordedFilename = URLEncoder.encode(fs.getName(), "UTF-8").replace("+", "%20");
                String cd = String.format(
                        "inline; filename=\"%s\";filename*=UTF-8''%s",
                        encordedFilename,
                        encordedFilename
                );

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                        .header(HttpHeaders.CONTENT_TYPE, fs.getContentType())
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fs.getSize()))
                        .body(resource);
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + target.toString());
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("UnsupportedEncoding Error");
            return ResponseEntity.status(400).build();
        } catch (IOException e) {
            log.error("File IO Error");
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity getResponseEntity(FileSummary fs, Long w, Long h, String t) {

        Path target = this.rootLocation.resolve(fs.getPath());
        if (Files.isDirectory(target)) {
            throw new StorageException("The path is not a file " + target);
        }

        try {
            String convertedFileName = w + "x" + h + "_" + t + ".jpg";

            Path convertedPath = target.getParent().resolve(convertedFileName);
            if (!convertedPath.toFile().exists()) {


                // FIX ME
                convertedPath = target;

            }

            Resource resource = new UrlResource(convertedPath.toUri());
            if (resource.exists() || resource.isReadable()) {
                String encordedFilename = URLEncoder.encode(convertedFileName, "UTF-8").replace("+", "%20");
                String cd = String.format(
                        "inline; filename=\"%s\";filename*=UTF-8''%s",
                        encordedFilename,
                        encordedFilename
                );

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                        .header(HttpHeaders.CONTENT_TYPE, fs.getContentType())
                        .body(resource);
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + target.toString());
            }

        } catch (UnsupportedEncodingException ex) {
            log.error("UnsupportedEncoding Error");
            return ResponseEntity.status(400).build();
        } catch (IOException e) {
            log.error("File IO Error");
            return ResponseEntity.status(400).build();
        }
        }
    private void createDirectory(Path path) throws IOException {
        try {
            Files.createDirectories(path);
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
