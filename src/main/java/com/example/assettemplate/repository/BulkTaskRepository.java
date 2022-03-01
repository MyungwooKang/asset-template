package com.example.assettemplate.repository;

import com.example.assettemplate.repository.entity.BulkTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkTaskRepository extends JpaRepository<BulkTask, String> {
}
