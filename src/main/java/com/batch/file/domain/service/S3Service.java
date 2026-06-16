
package com.batch.file.domain.service;

import com.batch.file.ports.out.storage.S3Port;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Port s3Port;

	public String presignUploadUrl(String fileName, Duration duration) {
		return s3Port.presignUploadUrl(fileName, duration);
	}

	public String presignDownloadUrl(String fileName, Duration duration) {
		return s3Port.presignDownloadUrl(fileName, duration);
	}
}
