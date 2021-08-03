package com.akinkemer.newsmanagementapp.service.Impl;

import com.akinkemer.newsmanagementapp.domain.app.FileUploadProperties;
import com.akinkemer.newsmanagementapp.service.FileSystemStorageService;
import com.akinkemer.newsmanagementapp.utilities.response.FileResponse;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import com.akinkemer.newsmanagementapp.utilities.result.ErrorDataResult;
import com.akinkemer.newsmanagementapp.utilities.result.SuccessDataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Service
public class FileSystemStorageServiceImpl implements FileSystemStorageService {
    private final Path dirLocation;

    @Autowired
    public FileSystemStorageServiceImpl(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.dirLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create upload dir!");
        }
    }

    @Override
    public DataResult<FileResponse> saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path dirFile = this.dirLocation.resolve(fileName);
            Files.copy(file.getInputStream(), dirFile, StandardCopyOption.REPLACE_EXISTING);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/file/download/")
                    .path(fileName)
                    .toUriString();
            log.info("File: {} uploaded successfully",fileName);
            return new SuccessDataResult<FileResponse>(
                    new FileResponse(fileName, fileDownloadUri),
                    "File uploaded successfully"
            );

        } catch (Exception e) {
            log.error("File uploaded failed");
            return new ErrorDataResult<>("File uploaded failed");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info("File loaded successfully");
                return resource;
            } else {
                log.error("Could not find file");
                throw new RuntimeException("Could not find file");
            }
        } catch (MalformedURLException e) {
            log.error("Could not download file");
            throw new RuntimeException("Could not download file");
        }
    }
}
