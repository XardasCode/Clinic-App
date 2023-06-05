package com.clinic.service.impl;

import com.clinic.controller.request.dto.VisitRequestDTO;
import com.clinic.controller.search.VisitSearchInfo;
import com.clinic.entity.Status;
import com.clinic.entity.User;
import com.clinic.entity.Visit;
import com.clinic.entity.dto.VisitDTO;
import com.clinic.entity.dto.mapper.VisitDTOMapper;
import com.clinic.entity.specification.VisitSpecification;
import com.clinic.exception.ErrorList;
import com.clinic.exception.ServerException;
import com.clinic.repository.UserRepository;
import com.clinic.repository.VisitRepository;
import com.clinic.repository.VisitStatusRepository;
import com.clinic.service.VisitService;
import com.clinic.util.VisitStatusList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final UserRepository userRepository;

    private final VisitStatusRepository visitStatusRepository;

    private final VisitDTOMapper mapper;

    @Override
    @Transactional
    public List<VisitDTO> getVisits(VisitSearchInfo info) {
        log.debug("Get visits: {}", info);
        Specification<Visit> specification = new VisitSpecification(info);
        return visitRepository.findAll(specification, Pageable.ofSize(info.getSize()).withPage(info.getPage() - 1))
                .stream()
                .map(mapper)
                .toList();

    }

    @Override
    @Transactional
    public VisitDTO getVisit(int id) {
        log.debug("Get visit by id: {}", id);
        Visit visit = visitRepository.findById(id).orElseThrow(
                () -> new ServerException("Visit with id " + id + " not found", ErrorList.VISIT_NOT_FOUND));
        return mapper.apply(visit);
    }

    @Override
    @Transactional
    public int saveVisit(VisitRequestDTO visitDTO) {
        log.debug("Save visit: {}", visitDTO);
        Visit visit = Visit.buildVisitFromDTO(visitDTO);
        User patient = userRepository.findById(visitDTO.getPatientId()).orElseThrow(
                () -> new ServerException("User with id " + visitDTO.getPatientId() + " not found", ErrorList.USER_NOT_FOUND));
        User doctor = userRepository.findById(visitDTO.getDoctorId()).orElseThrow(
                () -> new ServerException("User with id " + visitDTO.getDoctorId() + " not found", ErrorList.USER_NOT_FOUND));
        Status status = visitStatusRepository.findById(visitDTO.getStatusId()).orElseThrow(
                () -> new ServerException("Status with id " + visitDTO.getStatusId() + " not found", ErrorList.STATUS_NOT_FOUND));
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setStatus(status);
        log.debug("Save visit: {}", visit);
        return visitRepository.save(visit).getId();
    }

    @Override
    @Transactional
    public void updateVisit(int id, VisitRequestDTO visit) {
        log.debug("Update visit with id: {}, visit: {}", id, visit);
        Visit initialVisit = visitRepository.findById(id).orElseThrow(
                () -> new ServerException("Visit with id " + id + " not found", ErrorList.VISIT_NOT_FOUND));
        Visit updatedVisit = Visit.buildVisitFromDTO(visit);
        User patient = userRepository.findById(visit.getPatientId()).orElseThrow(
                () -> new ServerException("User with id " + visit.getPatientId() + " not found", ErrorList.USER_NOT_FOUND));
        User doctor = userRepository.findById(visit.getDoctorId()).orElseThrow(
                () -> new ServerException("User with id " + visit.getDoctorId() + " not found", ErrorList.USER_NOT_FOUND));
        Status status = visitStatusRepository.findById(visit.getStatusId()).orElseThrow(
                () -> new ServerException("Status with id " + visit.getStatusId() + " not found", ErrorList.STATUS_NOT_FOUND));
        Visit.margeVisit(initialVisit, updatedVisit, patient, doctor, status);
        log.debug("Updated visit: {}", initialVisit);
        visitRepository.save(initialVisit);
    }

    @Override
    public Integer getVisitPageCount(VisitSearchInfo visitSearchInfo) {
        log.debug("Get visit page count: {}", visitSearchInfo);
        Specification<Visit> specification = new VisitSpecification(visitSearchInfo);
        int size = visitRepository.findAll(specification).size();
        return size % visitSearchInfo.getSize() == 0 ? size / visitSearchInfo.getSize() : size / visitSearchInfo.getSize() + 1;
    }

    @Override
    public void cancelVisit(int id) {
        log.debug("Cancel visit with id: {}", id);
        Visit visit = visitRepository.findById(id).orElseThrow(
                () -> new ServerException("Visit with id " + id + " not found", ErrorList.VISIT_NOT_FOUND));
        visit.setStatus(visitStatusRepository.findById(VisitStatusList.CANCELED.getId())
                .orElseThrow(
                        () -> new ServerException("Status with id 3 not found", ErrorList.STATUS_NOT_FOUND)));
        visitRepository.save(visit);
    }

    @Override
    public void inProgressVisit(int id) {
        log.debug("Cancel visit with id: {}", id);
        Visit visit = visitRepository.findById(id).orElseThrow(
                () -> new ServerException("Visit with id " + id + " not found", ErrorList.VISIT_NOT_FOUND));
        visit.setStatus(visitStatusRepository.findById(VisitStatusList.IN_PROGRESS.getId())
                .orElseThrow(
                        () -> new ServerException("Status with id 3 not found", ErrorList.STATUS_NOT_FOUND)));
        visitRepository.save(visit);

    }
}
