package com.clinic.entity;

import com.clinic.controller.request.dto.VisitRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Visit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Builder.Default
    @Column(name = "date", nullable = false)
    private Date date = Date.valueOf(OffsetDateTime.now(ZoneOffset.UTC).toLocalDate());

    @Column(name = "problem", nullable = false)
    private String problem;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "diagnosis")
    private String diagnosis;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User patient;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Status status;

    public static Visit buildVisitFromDTO(VisitRequestDTO visitDTO) {
        return Visit.builder()
                .date(Date.valueOf(visitDTO.getDate()))
                .problem(visitDTO.getProblem())
                .treatment(visitDTO.getTreatment() == null ? "" : visitDTO.getTreatment())
                .diagnosis(visitDTO.getDiagnosis() == null ? "" : visitDTO.getDiagnosis())
                .build();
    }

    public static void margeVisit(Visit initialVisit, Visit updatedVisit, User patient, User doctor, Status status) {
        initialVisit.setDate(updatedVisit.getDate());
        initialVisit.setProblem(updatedVisit.getProblem());
        initialVisit.setTreatment(updatedVisit.getTreatment() == null ? initialVisit.getTreatment() : updatedVisit.getTreatment());
        initialVisit.setDiagnosis(updatedVisit.getDiagnosis() == null ? initialVisit.getDiagnosis() : updatedVisit.getDiagnosis());
        initialVisit.setPatient(patient);
        initialVisit.setDoctor(doctor);
        initialVisit.setStatus(status);
    }
}
