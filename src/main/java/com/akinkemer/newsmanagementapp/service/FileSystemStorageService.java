package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.utilities.response.FileResponse;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemStorageService {
    void init();
    DataResult<FileResponse> saveFile(MultipartFile file);
    Resource loadFile(String fileName);
}
