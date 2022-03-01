package com.example.assettemplate.controller;

import com.example.assettemplate.command.RequestBulkTaskCommand;
import com.example.assettemplate.controller.dto.BulkTaskRequest;
import com.example.assettemplate.repository.BulkTaskRepository;
import com.example.assettemplate.repository.entity.BulkTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bulk")
@Slf4j
@RequiredArgsConstructor
public class AssetController {

    private final CommandGateway commandGateway;
    private final BulkTaskRepository repository;

    @PostMapping("/recall2")
    public void recall(@RequestBody BulkTaskRequest request) {
        log.info("Controller!!");
        commandGateway.send(new RequestBulkTaskCommand(request.getSeq()));
    }

    @GetMapping("/test")
    public void test() {
        log.info("test");
        BulkTask bulkTask = repository.findById("2").orElseThrow();
        log.info("bulkTask : {}", bulkTask);
    }

}
