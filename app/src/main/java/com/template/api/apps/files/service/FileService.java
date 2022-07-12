package com.template.api.apps.files.service;

import com.template.api.apps.files.domain.AttachFile;
import com.template.api.apps.files.domain.AttachFileRepository;
import com.template.api.apps.files.dto.AttachFileDto;
import com.template.api.apps.storage.dto.FileSummary;
import com.template.api.jpa.Restrictions;
import com.template.api.apps.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FileService {

  private final StorageService storageService;
  private final AttachFileRepository attachFileRepository;

  @Transactional
  public AttachFileDto.Response storeFile(MultipartFile file, Boolean isSecret) throws Exception {

    LocalDateTime now = LocalDateTime.now();
    AttachFile attachFile = AttachFile.builder()
        .filename(file.getOriginalFilename())
        .contentType(file.getContentType())
        .size(file.getSize())
        .serverPath(now.format(DateTimeFormatter.ofPattern("yyyy/MM")))
        .isSecret(Boolean.TRUE.equals(isSecret))
        .build();

    attachFile = attachFileRepository.save(attachFile);

    Path filePath = Paths.get(attachFile.getServerFilePath());
    storageService.store(file, filePath, attachFile.isSecret());

    return attachFile.toResponse();
  }

  public List<AttachFileDto.Response> getFiles(List<Long> ids) {
    Restrictions r = new Restrictions();
    r.in("id", ids);

    List<AttachFile> files = attachFileRepository.findAll(r.output());
    return files.stream().map(v -> v.toResponse()).collect(Collectors.toList());
  }

  public ResponseEntity getFileSource(Long id) {
    Restrictions r = new Restrictions();
    r.eq("id", id);
    Optional<AttachFile> file = attachFileRepository.findOne(r.output());
    if (file.isEmpty()) {
      return ResponseEntity.status(404).build();
    } else {
      FileSummary fs = new FileSummary();
      fs.setName(file.get().getFilename());
      fs.setSize(file.get().getSize());
      fs.setContentType(file.get().getContentType());
      fs.setPath(Paths.get(file.get().getServerFilePath()));

      return storageService.getResponseEntity(fs, file.get().isSecret());
    }
  }

  public ResponseEntity getFileSource(Long id, Long w, Long h, String t) {
    Restrictions r = new Restrictions();
    r.eq("id", id);
    Optional<AttachFile> file = attachFileRepository.findOne(r.output());
    if (file.isEmpty() || file.get().isSecret()) {
      return ResponseEntity.status(404).build();
    } else {
      FileSummary fs = new FileSummary();
      fs.setName(file.get().getFilename());
      fs.setSize(file.get().getSize());
      fs.setContentType(file.get().getContentType());
      fs.setPath(Paths.get(file.get().getServerFilePath()));

      return storageService.getResponseEntity(fs, w, h, t);
    }
  }
}
