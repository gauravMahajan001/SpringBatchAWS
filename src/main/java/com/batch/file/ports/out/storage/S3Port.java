package com.batch.file.ports.out.storage;

import java.time.Duration;

public interface S3Port {
    String presignUploadUrl(String fileName, Duration duration);
    String presignDownloadUrl(String fileName, Duration duration);
}

