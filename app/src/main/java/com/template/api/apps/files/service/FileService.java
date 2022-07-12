package com.template.api.apps.files.service;


import com.template.api.apps.files.dto.AttachFileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    ResponseEntity<AttachFileDto.Response> uploadFile(MultipartFile file,Boolean secret)throws Exception;
    ResponseEntity<List<AttachFileDto.Response>> infoFiles(List<Long> ids);

    ResponseEntity downloadFile();
    ResponseEntity downloadFile(@PathVariable("id") Long id);

    ResponseEntity downloadThumbnail(Long id, Long w, Long h, String t);
}
