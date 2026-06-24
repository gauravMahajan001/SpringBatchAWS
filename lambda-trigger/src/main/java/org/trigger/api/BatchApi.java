package org.trigger.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.trigger.dto.StartBatchRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BatchApi {
    private static final String BATCH_API = "https://your-domain.com/batch/api/startBatch";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void invokeBatchApi(String bucketName, String fileName, Context context) throws IOException, InterruptedException {

        String payload = OBJECT_MAPPER.writeValueAsString(new StartBatchRequest(bucketName, fileName));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BATCH_API))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload)).build();

        HttpResponse<String> response =
                HTTP_CLIENT.send( request, HttpResponse.BodyHandlers.ofString());

        context.getLogger().log("Batch API response. status=" + response.statusCode() + ", " +
                "bucket=" + bucketName + ", file=" + fileName);

        if (response.statusCode() >= 400) {
            context.getLogger().log("error response status {}" + response.statusCode());
            throw new IOException("Batch API returned status " + response.statusCode());
        }
    }
}
