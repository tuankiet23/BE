package com.itsol.recruit_managerment.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface UploadCVService {
    public void init();

    public void save(String username, MultipartFile file, Long jobId);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
