package com.example.assettemplate.aggregate;

import com.example.assettemplate.command.DoSingleTaskCommand;
import com.example.assettemplate.event.SingleTaskDoneEvent;
import com.example.assettemplate.exception.DoWithdrawalException;
import com.example.assettemplate.repository.SingleTaskRepository;
import com.example.assettemplate.service.SingleTaskService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Profile("command")
@Aggregate
@Slf4j
public class SingleTaskAggregate {

    @AggregateIdentifier
    private String singleTaskId;
    private String bulkTaskId;

    private SingleTaskRepository repository;
    private SingleTaskService service;

    @CommandHandler
    public SingleTaskAggregate(DoSingleTaskCommand command,
                               @Autowired SingleTaskRepository repository,
                               @Autowired SingleTaskService service) {
        log.info("DoSingleTaskCommand CommandHandler. command : {}", command);
        this.repository = repository;
        this.service = service;

        try {
            this.service.doWithdrawal(command.getSingleTaskId());
        } catch(DoWithdrawalException e) {
            //TODO Fail Event 발행
        }

        String bulkTaskId = this.repository.findById(command.getSingleTaskId()).orElseThrow().getBulkTaskId();

        apply(new SingleTaskDoneEvent(command.getSingleTaskId(), bulkTaskId));
    }

    @EventSourcingHandler
    public void on(SingleTaskDoneEvent event) {
        log.info("SingleTaskDoneEvent EventSourcingHandler. event : {}", event);
        this.singleTaskId = event.getSingleTaskId();
        this.bulkTaskId = event.getBulkTaskId();
    }

    public SingleTaskAggregate() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }
}

