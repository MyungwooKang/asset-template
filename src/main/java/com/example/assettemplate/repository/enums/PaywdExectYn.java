package com.example.assettemplate.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum PaywdExectYn {
    OPENED("N", "대기"),
    INPROGRESS("I", "진행중"),
    DONE("Y", "완료"),
    UNKNOWN("00", "알수없는 코드");

    private String legacyCode;
    private String description;

    public static PaywdExectYn ofLegacyCode(String legacyCode) {
        return Arrays.stream(PaywdExectYn.values())
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElse(UNKNOWN);
    }
}
