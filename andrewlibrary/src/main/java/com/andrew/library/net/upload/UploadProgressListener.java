package com.andrew.library.net.upload;

public interface UploadProgressListener {
    void onProgress(long total, long progress);
}
