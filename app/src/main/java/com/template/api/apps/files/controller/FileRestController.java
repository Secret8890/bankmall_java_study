package com.template.api.apps.files.controller;


import com.template.api.apps.files.dto.AttachFileDto;
import com.template.api.apps.files.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"[ANY] 파일첨부 API"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v3/files")
public class FileRestController {

    private final FileService fileService;

    @ApiOperation(value = "파일 업로드", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachFileDto.Response> uploadFile(
            @ApiParam(value = "Select the file to Upload", required = true)
            MultipartFile file,
            @ApiParam(value = "true", example = "true")
            @RequestParam(name = "secret", required = false)
            Boolean secret
    ) throws Exception {
        return ResponseEntity.ok(fileService.storeFile(file, secret));
    }

    @GetMapping("/infos")
    public ResponseEntity<List<AttachFileDto.Response>> infoFiles(
            @RequestParam("ids") List<Long> ids
    ) {
        return ResponseEntity.ok(fileService.getFiles(ids));
    }

    @ApiOperation(value = "파일 다운로드 (undefined")
    @GetMapping("/undefined")
    public ResponseEntity downloadFile() {
        return ResponseEntity.status(404).build();
    }

    @ApiOperation(value = "파일 다운로드")
    @GetMapping("/{id}")
    public ResponseEntity downloadFile(
            @PathVariable("id") Long id
    ) {
        return fileService.getFileSource(id);
    }

    @ApiOperation(value = "썸네일 다운로드")
    @GetMapping("/{id}/thumb")
    public ResponseEntity downloadThumbnail(
            @PathVariable("id") Long id,
            @ApiParam(example = "200") @RequestParam(value = "w", required = false) Long w,
            @ApiParam(example = "200") @RequestParam(value = "h", required = false) Long h,
            @ApiParam(example = "crop") @RequestParam(value = "t", required = false, defaultValue = "crop") String t
    ) {
        return fileService.getFileSource(id, w, h, t);
    }


}
