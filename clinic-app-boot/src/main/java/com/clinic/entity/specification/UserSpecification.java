package com.clinic.entity.specification;

import com.clinic.controller.search.UserSearchInfo;
import com.clinic.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {

    private final UserSearchInfo userSearchInfo;

    private static final String NAME = "name";

    public static final String ROLE = "role";

    public static final String SPECIALIZATION = "specialization";

    public static final String DESC = "DESC";

    public static final String SPLITERATOR = ":";

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (userSearchInfo.getFilter() != null && !userSearchInfo.getFilter().isEmpty()) {
            for (String filter : userSearchInfo.getFilter()) {
                String[] filterParts = filter.split(SPLITERATOR);
                String filterField = filterParts[0];
                String filterValue = filterParts[1];
                if (filterField.equalsIgnoreCase(NAME)) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(filterField)), "%" + filterValue.toLowerCase() + "%"));
                }
                if (filterField.equalsIgnoreCase(ROLE)) {
                    predicates.add(criteriaBuilder.equal(root.get(filterField).get(NAME), filterValue.toUpperCase()));
                }
                if (filterField.equalsIgnoreCase(SPECIALIZATION)) {
                    predicates.add(criteriaBuilder.equal(root.get(filterField).get(NAME), filterValue));
                }
            }
        }
        setSortOrder(query, criteriaBuilder, root);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setSortOrder(CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Root<User> root) {
        query.orderBy(userSearchInfo.getDirection().equalsIgnoreCase(DESC) ?
                criteriaBuilder.desc(root.get(userSearchInfo.getSortField())) :
                criteriaBuilder.asc(root.get(userSearchInfo.getSortField())));
    }
}
