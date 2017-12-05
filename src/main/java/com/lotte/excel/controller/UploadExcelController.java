package com.lotte.excel.controller;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.tuple.*;
import org.apache.poi.openxml4j.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.amazonaws.services.s3.model.*;
import com.lotte.excel.domain.*;
import com.lotte.lps.core.excel.*;
import com.lotte.lps.core.file.*;

import lombok.*;
import lombok.extern.slf4j.*;

@Slf4j
@RestController
@RequestMapping("upload")
@RequiredArgsConstructor
public class UploadExcelController {

    private final static String EXCEL_UPLOAD_PATH = "excel_upload";

    @Autowired
    private S3Service s3Service;

    //curl -v -F excel_file=@D:\sample.xlsx http://localhost:8888/upload/excel
    @PostMapping("excel")
    public void uploadAndProcessExcel(@RequestParam("excel_file") MultipartFile multipartFile)
            throws IOException, InvalidFormatException {

        log.info("Step #1. Uploading the multipart excel file...");
        S3Object s3Object = s3Service.uploadAndGetObject(EXCEL_UPLOAD_PATH, multipartFile);

        log.info("Step #2. Converting the uploaded excel file into List<VO>...");
        List<SampleEntry> sampleEntryList = ExcelUploadUtils.readExcelToList(s3Object, SampleEntry::rowOf, Pair.of(5, 10));
        //List<SampleEntry> sampleEntryList = ExcelUploadUtils.readExcelToList(s3Object, SampleEntry::rowOf);
        //List<SampleEntry> sampleEntryList = ExcelUploadUtils.readExcelToList(s3Object, SampleEntry::rowOf, Pair.of(5, 10), 1);

        log.info("Step #3. Processing List<VO> according to business requirements...");
        sampleEntryList.forEach(System.out::println);
    }

}
