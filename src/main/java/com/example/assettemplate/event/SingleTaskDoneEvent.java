package com.example.assettemplate.event;

import lombok.Value;

@Value
public class SingleTaskDoneEvent {
    private final String singleTaskId;
    private final String bulkTaskId;
}
