package io.github.spartamo1.schedulerapp.repository;

import io.github.spartamo1.schedulerapp.entity.Schedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {
    List<Schedule> findAllByCreatedBy(String createdBy, Sort sort);
}