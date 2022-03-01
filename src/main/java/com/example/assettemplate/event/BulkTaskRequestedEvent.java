package com.example.assettemplate.event;

import lombok.Value;

@Value
public class BulkTaskRequestedEvent {
    private final String bulkTaskId;
    private final int totalCount;
}
