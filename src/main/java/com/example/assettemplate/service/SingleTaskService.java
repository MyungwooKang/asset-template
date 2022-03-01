package com.example.assettemplate.service;

import com.example.assettemplate.aggregate.SingleTaskAggregate;
import com.example.assettemplate.exception.DoWithdrawalException;
import com.example.assettemplate.repository.SingleTaskRepository;
import com.example.assettemplate.repository.entity.SingleTask;
import com.example.assettemplate.repository.enums.AcdnHdlStatCd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SingleTaskService {

    private final SingleTaskRepository repository;

    /**
     * php 대량회수 스크립트 내 단건 샘플. 상태값만 변경한다.
     * @param singleTaskId
     * @throws DoWithdrawalException
     */
    @Transactional
    public void doWithdrawal(String singleTaskId) throws DoWithdrawalException {
        SingleTask singleTask = repository.findById(singleTaskId).orElseThrow();

//        if(Integer.parseInt(singleTaskId)/3 == 0) {
//            throw new DoWithdrawalException("3개중 한개만 실패나는 에러");
//        }

        singleTask.setState(AcdnHdlStatCd.COMPLETED);
        repository.save(singleTask);
    }
}
