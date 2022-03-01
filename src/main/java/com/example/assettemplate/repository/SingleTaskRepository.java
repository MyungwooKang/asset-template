package com.example.assettemplate.repository;

import com.example.assettemplate.repository.entity.SingleTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SingleTaskRepository extends JpaRepository<SingleTask, String> {
    List<SingleTask> findAllByBulkTaskId(String bulkTaskId);
}
