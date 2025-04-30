package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum StorageErrorCode implements IErrorCode {
    FILE_SIZE_EXCEEDED(80000, "文件过大"),
    FILE_GET_BYTES_FAILED(80001, "获取文件字节失败"),;


    private final int code;
    private final String message;

    StorageErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
