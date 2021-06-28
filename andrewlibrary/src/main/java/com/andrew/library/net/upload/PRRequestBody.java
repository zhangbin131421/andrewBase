package com.andrew.library.net.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * val fileBody = PRRequestBody(file, UploadProgressListener { total, progress ->})
 * val body = MultipartBody.Part.createFormData("file", videoFile?.name, fileBody);
 * partList.add(body)
 */

public class PRRequestBody extends RequestBody {
    private File mFile;

    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private UploadProgressListener uploadCallback;

    public PRRequestBody(final File file, UploadProgressListener uploadCallback) {
        mFile = file;
        this.uploadCallback = uploadCallback;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("video/mp4");
    }

    @Override
    public long contentLength() {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;
        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                uploadCallback.onProgress(fileLength, uploaded);
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }
}