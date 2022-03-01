package com.example.assettemplate.repository.entity;

import com.example.assettemplate.repository.enums.AcdnHdlStatCd;
import com.example.assettemplate.repository.enums.AcdnHdlStatCdConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SINGLE_TASK")
public class SingleTask {
    @Id
    @Column(unique = true)
    private String singleTaskId;

    @Convert(converter = AcdnHdlStatCdConverter.class)
    private AcdnHdlStatCd state;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    private String bulkTaskId;
}
