package com.clinic.entity.dto.mapper;

import com.clinic.entity.Visit;
import com.clinic.entity.dto.VisitDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VisitDTOMapper implements Function<Visit, VisitDTO> {
    @Override
    public VisitDTO apply(Visit visit) {
        return VisitDTO.builder()
                .id(visit.getId())
                .patientName(visit.getPatient().getName())
                .doctorName(visit.getDoctor().getName())
                .doctorSpecialization(visit.getDoctor().getSpecialization().getName())
                .problem(visit.getProblem())
                .treatment(visit.getTreatment())
                .status(visit.getStatus().getName())
                .date(visit.getDate().toString())
                .build();
    }
}