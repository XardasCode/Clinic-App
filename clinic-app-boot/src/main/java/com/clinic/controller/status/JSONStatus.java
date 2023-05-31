package com.clinic.controller.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JSONStatus {

    @Builder.Default
    private String status = "success";

    private String message;

}
