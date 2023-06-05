package com.clinic.entity.specification;

import com.clinic.controller.search.VisitSearchInfo;
import com.clinic.entity.Visit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class VisitSpecification implements Specification<Visit> {

    private final VisitSearchInfo info;

    public static final String SPLITERATOR = ":";

    public static final String ID = "id";
    public static final String PATIENT = "patient";

    public static final String PATIENT_ID = "patientId";

    public static final String DOCTOR_ID = "doctorId";

    public static final String DOCTOR = "doctor";

    public static final String DATE_FROM = "date_from";

    public static final String DATE_TO = "date_to";

    public static final String STATUS = "status";

    public static final String PROBLEM = "problem";

    public static final String DESC = "desc";

    public static final String NAME = "name";

    public static final String DATE = "date";

    public static final String PERCENT = "%";

    @Override
    public Predicate toPredicate(@NonNull Root<Visit> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (info.getFilter() != null && !info.getFilter().isEmpty()) {
            for (String filter : info.getFilter()) {
                String[] filterParts = filter.split(SPLITERATOR);
                String filterField = filterParts[0];
                String filterValue = filterParts[1];

                if (filterField.equalsIgnoreCase(PATIENT) || filterField.equalsIgnoreCase(DOCTOR)) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(filterField).get(NAME)), PERCENT + filterValue.toLowerCase() + PERCENT));
                }
                if (filterField.equalsIgnoreCase(PATIENT_ID)) {
                    predicates.add(criteriaBuilder.equal(root.get(PATIENT).get(ID), Integer.parseInt(filterValue)));
                }
                if (filterField.equalsIgnoreCase(DOCTOR_ID)) {
                    predicates.add(criteriaBuilder.equal(root.get(DOCTOR).get(ID), Integer.parseInt(filterValue)));
                }
                if (filterField.equalsIgnoreCase(DATE_FROM)) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(DATE), Date.valueOf(filterValue)));
                }
                if (filterField.equalsIgnoreCase(DATE_TO)) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(DATE), Date.valueOf(filterValue)));
                }
                if (filterField.equalsIgnoreCase(STATUS)) {
                    predicates.add(criteriaBuilder.equal(root.get(filterField).get(NAME), filterValue));
                }
                if (filterField.equalsIgnoreCase(PROBLEM)) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(filterField)), PERCENT + filterValue.toLowerCase() + PERCENT));
                }
            }
        }
        setSortOrder(query, criteriaBuilder, root);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setSortOrder(CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Root<Visit> root) {
        query.orderBy(info.getDirection().equalsIgnoreCase(DESC) ?
                criteriaBuilder.desc(root.get(info.getSortField())) :
                criteriaBuilder.asc(root.get(info.getSortField())));
    }
}
