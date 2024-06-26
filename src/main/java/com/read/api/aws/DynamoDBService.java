package com.read.api.aws;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class DynamoDBService {

    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .build();

    public Map<String, AttributeValue> getItem(String shortUrl, String shard) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("short_url", AttributeValue.builder().s(shortUrl).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(shard)
                .key(key)
                .build();

        GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
        return getItemResponse.item();
    }

    public void cleanUp() {
        dynamoDbClient.close();
    }

}
