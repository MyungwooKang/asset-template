package com.example.assettemplate.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class DoSingleTaskCommand {
    @TargetAggregateIdentifier
    private final String singleTaskId;
}
