package com.clinic.service;

import com.clinic.controller.request.dto.VisitRequestDTO;
import com.clinic.controller.search.VisitSearchInfo;
import com.clinic.entity.dto.VisitDTO;

import java.util.List;

public interface VisitService {
    List<VisitDTO> getVisits(VisitSearchInfo visitSearchInfo);

    VisitDTO getVisit(int id);

    int saveVisit(VisitRequestDTO visit);

    void updateVisit(int id, VisitRequestDTO visit);

    Integer getVisitPageCount(VisitSearchInfo visitSearchInfo);
}
