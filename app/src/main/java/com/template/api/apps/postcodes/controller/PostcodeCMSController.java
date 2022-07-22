package com.template.api.apps.postcodes.controller;


import com.google.common.collect.Lists;
import com.template.api.apps.logs.dto.LogFileUploadDto;
import com.template.api.apps.logs.service.LogFileUploadService;
import com.template.api.apps.postcodes.dto.PostcodeDto;
import com.template.api.apps.postcodes.service.PostCodeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postcodes/cms")
@Secured(("ROLE_ADMIN"))
public class PostcodeCMSController {

    //private final AptSalesRepository aptSalesRepository;
    private final PostCodeService postCodeService;
    private final LogFileUploadService logFileUploadService;


    @ApiOperation(value = "[CMS] KB_뱅크몰.zip 업로드", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity zipFileImport(
            @ApiParam(value = "Select the file to Upload", required = true) MultipartFile file
    ) throws IOException {
        List<LogFileUploadDto.CmsResponse> logFileUploadList = logFileUploadService.getLogFileUploadCheckList(
                "KbUpload");
        if (logFileUploadList.size() > 0) {
            throw new IOException("이미 업로드가 진행중인 항목이 존재합니다.");
        }

        //todo: 파일명
        String fileName = file.getOriginalFilename();

        //todo:확장명
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if(extension != null) {
            extension = extension.toLowerCase();
        } else {
            throw new IOException("File is not extension");
        }
        //확장명체크
        if (extension.equals("zip")) {
            postCodeService.zipFileUploadStart(fileName, file);
            List<PostcodeDto.UploadDto> uploadDtoList = postCodeService.zipFileImport(fileName, file);
            if (uploadDtoList.size() > 0) {
                PostcodeDto.UploadDto aptComplexModel = uploadDtoList.stream()
                        .filter(x -> x.getIndex() == 0).findAny().orElse(null);
                PostcodeDto.UploadDto aptPriceModel = uploadDtoList.stream().filter(x -> x.getIndex() == 1)
                        .findAny().orElse(null);
                PostcodeDto.UploadDto aptSizeTypeModel = uploadDtoList.stream()
                        .filter(x -> x.getIndex() == 2).findAny().orElse(null);

                if (aptComplexModel != null) {
                    postCodeService.applyToAptComplex(aptComplexModel.getUploadZipName(),
                            aptComplexModel.getUploadFileName(), aptComplexModel.getRows());
                }
                if (aptPriceModel != null) {
                    postCodeService.applyToAptPrice(aptPriceModel.getUploadZipName(),
                            aptPriceModel.getUploadFileName(), aptPriceModel.getRows());
                }
                if (aptSizeTypeModel != null) {
                    postCodeService.applyToAptSizeType(aptSizeTypeModel.getUploadZipName(),
                            aptSizeTypeModel.getUploadFileName(), aptSizeTypeModel.getRows());
                }

                postCodeService.updateKbIds();
            }
        } else if (extension.equals("txt")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
            String myString = IOUtils.toString(stream, "EUC-KR");
            List<String> rows = Lists.newArrayList(myString.split("\\n"));

            if (rows.size() > 0) {
                if (fileName.contains("정보")) {
                    postCodeService.applyToAptComplex(null, fileName, rows);
                    postCodeService.updateKbIds();

                } else if (fileName.contains("시세")) {
                    postCodeService.applyToAptPrice(null, fileName, rows);

                } else if (fileName.contains("평형")) {
                    postCodeService.applyToAptSizeType(null, fileName, rows);
                }
            }
        } else {
            throw new IOException("File is not zip or txt");
        }

        return ResponseEntity.ok().build();
    } }