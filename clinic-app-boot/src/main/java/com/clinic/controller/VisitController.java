package com.clinic.controller;

import com.clinic.controller.request.dto.VisitRequestDTO;
import com.clinic.controller.search.VisitSearchInfo;
import com.clinic.controller.status.JSONStatus;
import com.clinic.entity.dto.VisitDTO;
import com.clinic.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/visits")
@Slf4j
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<VisitDTO>> getVisits(
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "10") String size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false) List<String> filter) {
        log.info("get visits point called");
        VisitSearchInfo visitSearchInfo = VisitSearchInfo.builder()
                .page(Integer.parseInt(page))
                .size(Integer.parseInt(size))
                .sortField(sortField)
                .direction(direction)
                .filter(filter)
                .build();
        return ResponseEntity.ok(visitService.getVisits(visitSearchInfo));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VisitDTO> getVisit(@PathVariable int id) {
        log.info("Getting visit with id {}", id);
        return ResponseEntity.ok(visitService.getVisit(id));
    }

    @PostMapping
    public ResponseEntity<JSONStatus> saveVisit(@RequestBody @Valid VisitRequestDTO visit) {
        log.info("Saving visit");
        int id = visitService.saveVisit(visit);
        log.info("Visit saved with id {}", id);
        return ResponseEntity.ok(JSONStatus.builder().message(String.valueOf(id)).build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<JSONStatus> updateVisit(@PathVariable int id, @RequestBody @Valid VisitRequestDTO visit) {
        log.info("Updating visit with id {}", id);
        visitService.updateVisit(id, visit);
        log.info("Visit updated with id {}", id);
        return ResponseEntity.ok(JSONStatus.builder().message(String.valueOf(id)).build());
    }

    @GetMapping(value = "/page-count")
    public ResponseEntity<Integer> getVisitPageCount(
            @RequestParam(required = false, defaultValue = "10") String size,
            @RequestParam(required = false) List<String> filter) {
        log.info("get visit page count point called");
        VisitSearchInfo visitSearchInfo = VisitSearchInfo.builder()
                .size(Integer.parseInt(size))
                .filter(filter)
                .build();
        return ResponseEntity.ok(visitService.getVisitPageCount(visitSearchInfo));
    }

    @PatchMapping(value = "/{id}/cancel")
    public ResponseEntity<JSONStatus> cancelVisit(@PathVariable int id) {
        log.info("Canceling visit with id {}", id);
        visitService.cancelVisit(id);
        log.info("Visit canceled with id {}", id);
        return ResponseEntity.ok(JSONStatus.builder().message(String.valueOf(id)).build());
    }

    @PatchMapping(value = "/{id}/in-progress")
    public ResponseEntity<JSONStatus> inProgressVisit(@PathVariable int id) {
        log.info("In progress visit with id {}", id);
        visitService.inProgressVisit(id);
        log.info("Visit in progress with id {}", id);
        return ResponseEntity.ok(JSONStatus.builder().message(String.valueOf(id)).build());
    }
}
