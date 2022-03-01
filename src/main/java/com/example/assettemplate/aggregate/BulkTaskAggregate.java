package com.example.assettemplate.aggregate;

import com.example.assettemplate.command.CountingFinishedSingleTaskCommand;
import com.example.assettemplate.command.RequestBulkTaskCommand;
import com.example.assettemplate.event.BulkTaskDoneEvent;
import com.example.assettemplate.event.BulkTaskRequestedEvent;
import com.example.assettemplate.event.SingleTaskCountedEvent;
import com.example.assettemplate.repository.BulkTaskRepository;
import com.example.assettemplate.repository.entity.BulkTask;
import com.example.assettemplate.repository.enums.PaywdExectYn;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Profile("command")
@Aggregate
@Slf4j
public class BulkTaskAggregate {

    @AggregateIdentifier
    private String bulkTaskId;
    private PaywdExectYn state;
    private int totalCount;
    private int doneCount;

    private BulkTaskRepository repository;

    @CommandHandler
    public BulkTaskAggregate(RequestBulkTaskCommand command, @Autowired BulkTaskRepository repository) {
        log.info("RequestBulkTaskCommand CommandHandler. command : {}", command);
        this.repository = repository;

        BulkTask bulkTask = this.repository.findById(command.getBulkTaskId()).orElseThrow();

        apply(new BulkTaskRequestedEvent(command.getBulkTaskId(), bulkTask.getTargetMemberCount()));
    }

    @EventSourcingHandler
    public void on(BulkTaskRequestedEvent event) {
        log.info("BulkTaskRequestedEvent EventSourcingHandler. event : {}", event);
        this.bulkTaskId = event.getBulkTaskId();
        this.state = PaywdExectYn.INPROGRESS;
        this.totalCount = event.getTotalCount();
        this.doneCount = 0;
    }

    @CommandHandler
    public void handler(CountingFinishedSingleTaskCommand command) {
        log.info("CountingFinishedSingleTaskCommand CommandHandler. command : {}", command);

        int doneCount = this.doneCount + 1;
        apply(new SingleTaskCountedEvent());
        if(this.totalCount == doneCount) {
            BulkTask bulkTask = this.repository.findById(this.bulkTaskId).orElseThrow();
            bulkTask.setPaywdExectYn(PaywdExectYn.DONE);
            this.repository.save(bulkTask);

            apply(new BulkTaskDoneEvent(this.bulkTaskId));
        }
    }

    @EventSourcingHandler
    public void on(SingleTaskCountedEvent event) {
        log.info("SingleTaskCountedEvent EventSourcingHandler. event : {}", event);
        this.doneCount++;
//        apply(/*newCommand*/);
    }

//    @CommandHandler
//    public void handler(/*newCommand*/ ) {
//        if(this.totalCount == doneCount) {
//            BulkTask bulkTask = this.repository.findById(this.bulkTaskId).orElseThrow();
//            bulkTask.setPaywdExectYn(PaywdExectYn.DONE);
//            this.repository.save(bulkTask);
//
//            apply(new BulkTaskDoneEvent(this.bulkTaskId));
//        }
//    }


    @EventSourcingHandler
    public void on(BulkTaskDoneEvent event) {
        log.info("BulkTaskDoneEvent EventSourcingHandler. event : {}", event);
        this.state = PaywdExectYn.DONE;
    }

    public BulkTaskAggregate() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }
}

