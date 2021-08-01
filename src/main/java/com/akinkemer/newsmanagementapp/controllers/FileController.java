package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.service.FileSystemStorageService;
import com.akinkemer.newsmanagementapp.utilities.response.FileResponse;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {
    private final FileSystemStorageService fileSystemStorageService;

    @PostMapping("/upload")
    public DataResult<FileResponse> uploadSingleFile(@RequestParam("file") MultipartFile file) {
        return fileSystemStorageService.saveFile(file);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = fileSystemStorageService.loadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}