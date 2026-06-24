package org.trigger.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.trigger.api.BatchApi;

public class S3EventHandler implements RequestHandler<S3Event, String> {
    private final BatchApi batchApi = new BatchApi();

    @Override
    public String handleRequest(S3Event s3Event, Context context) {

        s3Event.getRecords().forEach(s3Record -> {
            String bucketName = s3Record.getS3().getBucket().getName();
            String fileName = s3Record.getS3().getObject().getKey();

            try {
                batchApi.invokeBatchApi(bucketName, fileName, context);
            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to invoke batch API. bucket="
                                + bucketName
                                + ", file="
                                + fileName, e); }
        });
        return "SUCCESS";
    }
}
