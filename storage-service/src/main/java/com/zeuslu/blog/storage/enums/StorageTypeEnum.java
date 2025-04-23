package com.zeuslu.blog.storage.enums;

public enum StorageTypeEnum {
    COS("腾讯云COS"),
    MINIO("MinIO"),
    OSS("阿里云OSS");
    
    private final String desc;
    
    StorageTypeEnum(String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return desc;
    }
}