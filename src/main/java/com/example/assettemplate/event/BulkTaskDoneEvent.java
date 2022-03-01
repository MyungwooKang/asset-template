package com.example.assettemplate.event;

import lombok.Value;

@Value
public class BulkTaskDoneEvent {
    private final String bulkTaskId;
}
