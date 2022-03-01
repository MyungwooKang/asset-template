package com.example.assettemplate.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CountingFinishedSingleTaskCommand {
    @TargetAggregateIdentifier
    private final String bulkTaskId;
    private final String singleTaskId;
}
