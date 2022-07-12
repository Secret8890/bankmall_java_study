package com.template.api.apps.files.service;

import com.template.api.apps.files.domain.AttachFile;
import com.template.api.apps.files.domain.AttachFileRepository;
import com.template.api.apps.files.dto.AttachFileDto;
import com.template.api.apps.storage.service.StorageService;
import com.template.api.jpa.Restrictions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Qualifier("FileServiceImpl")
@Primary
public class FileServiceImpl implements FileService{

    private final StorageService storageService;
    private final AttachFileRepository attachFileRepository;

    @Override
    public ResponseEntity<AttachFileDto.Response> uploadFile(MultipartFile file, Boolean isSecret) throws Exception {

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

    @Override
    public ResponseEntity<List<AttachFileDto.Response>> infoFiles(List<Long> ids) {
        Restrictions r = new Restrictions();
        r.in("id", ids);

        List<AttachFile> files = attachFileRepository.findAll(r.output());
        return files.stream().map(v -> v.toResponse()).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity downloadFile() {
        return null;
    }

    @Override
    public ResponseEntity downloadFile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity downloadThumbnail(Long id, Long w, Long h, String t) {
        return null;
    }
}
