package com.clinic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitDTO {
    private int id;

    private String problem;

    private String treatment;

    private String doctorName;

    private String patientName;

    private String status;

    private String date;
}
