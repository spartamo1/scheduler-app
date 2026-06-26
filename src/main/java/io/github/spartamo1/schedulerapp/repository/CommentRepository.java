package io.github.spartamo1.schedulerapp.repository;

import io.github.spartamo1.schedulerapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
    List<Comment> findAllByScheduleId(Integer scheduleId);

    Integer countAllByScheduleId(Integer scheduleId);
}