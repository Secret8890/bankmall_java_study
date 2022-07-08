package com.template.api.apps.files.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"[ANY] 파일첨부 API"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v3/files")
public class FileRestController {


}
