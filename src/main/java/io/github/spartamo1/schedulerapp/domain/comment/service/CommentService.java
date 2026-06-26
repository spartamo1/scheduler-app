package io.github.spartamo1.schedulerapp.domain.comment.service;

import io.github.spartamo1.schedulerapp.domain.comment.dto.CommentDto;
import io.github.spartamo1.schedulerapp.domain.comment.dto.CreateCommentDto;
import io.github.spartamo1.schedulerapp.domain.comment.mapper.CommentMapper;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.ScheduleDto;
import io.github.spartamo1.schedulerapp.entity.Comment;
import io.github.spartamo1.schedulerapp.entity.Schedule;
import io.github.spartamo1.schedulerapp.repository.CommentRepository;
import io.github.spartamo1.schedulerapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentDto create(Integer scheduleId, CreateCommentDto createCommentDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + scheduleId));

        // comment 가 10개 이상이면 throw err
        Integer commentCnt = commentRepository.countAllByScheduleId(schedule.getId());
        if (commentCnt > 10)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글을 더 이상 달 수 없습니다");

        Comment comment = commentMapper.toEntity(createCommentDto);
        comment.setSchedule(schedule);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentDto(savedComment);
    }

}
