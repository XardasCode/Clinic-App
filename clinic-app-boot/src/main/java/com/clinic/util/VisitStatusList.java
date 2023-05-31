package com.clinic.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VisitStatusList {
    PENDING(1),
    DONE(2),
    CANCELED(3),
    IN_PROGRESS(4);

    private final int id;
}
