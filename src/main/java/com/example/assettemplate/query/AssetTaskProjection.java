package com.example.assettemplate.query;

import com.example.assettemplate.command.CountingFinishedSingleTaskCommand;
import com.example.assettemplate.command.DoSingleTaskCommand;
import com.example.assettemplate.event.BulkTaskRequestedEvent;
import com.example.assettemplate.event.SingleTaskDoneEvent;
import com.example.assettemplate.repository.BulkTaskRepository;
import com.example.assettemplate.repository.SingleTaskRepository;
import com.example.assettemplate.repository.entity.BulkTask;
import com.example.assettemplate.repository.entity.SingleTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Profile("query")
@Service
@Slf4j
@RequiredArgsConstructor
@ProcessingGroup("asset-task")
public class AssetTaskProjection {


    /**
     * TODO
     * 1. SAGA EventHandler 로 변경하기.
     * 2. 로직 내 try/catch 삽입 및 보상 트랜잭션 (Command & Event) 추가 정의
     */

    private final BulkTaskRepository bulkTaskRepository;
    private final SingleTaskRepository singleTaskRepository;
    private final CommandGateway commandGateway;

    @EventHandler
    public void on(BulkTaskRequestedEvent event) {
        log.info("BulkTaskRequestedEvent Handler. event : {}", event);

        BulkTask bulkTask = bulkTaskRepository.findById(event.getBulkTaskId()).orElseThrow();
        List<SingleTask> singleTasks = singleTaskRepository.findAllByBulkTaskId(bulkTask.getBulkTaskId());

        for(SingleTask st: singleTasks) {
            commandGateway.send(new DoSingleTaskCommand(st.getSingleTaskId()));
        }
    }

    @EventHandler
    public void on(SingleTaskDoneEvent event) {
        log.info("SingleTaskDoneEvent Handler. event : {}", event);
        commandGateway.send(new CountingFinishedSingleTaskCommand(event.getBulkTaskId(), event.getSingleTaskId()));
    }
}
