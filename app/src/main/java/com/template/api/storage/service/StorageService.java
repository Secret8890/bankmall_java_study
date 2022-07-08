package com.template.api.storage.service;

import com.template.api.storage.dto.FileSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void store(MultipartFile file, Path path, boolean isSecret) throws Exception;

    ResponseEntity getResponseEntity(FileSummary fs, boolean secret);

    ResponseEntity getResponseEntity(FileSummary fs, Long w, Long h, String t);
}
