package com.example.assettemplate.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum AcdnHdlStatCd {
    OPENED("01", "대기"),
    INPROGRESS("02", "진행중"),
    DELETED("03", "삭제"),
    COMPLETED("99", "완료"),
    FAILED("97", "실패"),
    UNKNOWN("00", "알수없는 코드");

    private String legacyCode;
    private String description;

    public static AcdnHdlStatCd ofLegacyCode(String legacyCode) {
        return Arrays.stream(AcdnHdlStatCd.values())
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElse(UNKNOWN);
    }
}
