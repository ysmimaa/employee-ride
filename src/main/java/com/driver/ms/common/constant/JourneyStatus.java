package com.driver.ms.common.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum JourneyStatus {
    TODO("TODO", 0),
    INPROGRESS("INPROGRESS", 1),
    COMPETED("COMPLETED", 100);

    private String status;
    private int percentage;
}
