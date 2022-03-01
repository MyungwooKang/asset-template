package com.example.assettemplate.repository.entity;

import com.example.assettemplate.repository.enums.PaywdExectYn;
import com.example.assettemplate.repository.enums.PaywdExectYnConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Slf4j
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BULK_TASK")
public class BulkTask {
    @Id
    @Column(unique = true)
    private String bulkTaskId;

    @Convert(converter = PaywdExectYnConverter.class)
    private PaywdExectYn paywdExectYn;

    private int targetMemberCount;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;
}
