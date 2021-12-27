package com.itsol.recruit_managerment.model;

import lombok.Data;

@Data
public class FileInfo {
    private String name;
    private String url;

    public FileInfo(String filename, String url) {
    }
}
