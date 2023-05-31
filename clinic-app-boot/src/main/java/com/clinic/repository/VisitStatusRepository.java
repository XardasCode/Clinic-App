package com.clinic.repository;

import com.clinic.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitStatusRepository extends JpaRepository<Status, Integer> {
}
