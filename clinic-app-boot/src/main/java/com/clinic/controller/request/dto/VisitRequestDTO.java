package com.clinic.controller.request.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitRequestDTO {
    @NotBlank(message = "Date is mandatory field")
    private String problem;

    @NotBlank(message = "Date is mandatory field")
    public String date;

    private String treatment;

    private String diagnosis;

    @Min(value = 1, message = "Doctor id must be greater than 0")
    private int doctorId;

    @Min(value = 1, message = "Patient id must be greater than 0")
    private int patientId;

    @Min(value = 1, message = "Status id must be greater than 0")
    private int statusId;
}
