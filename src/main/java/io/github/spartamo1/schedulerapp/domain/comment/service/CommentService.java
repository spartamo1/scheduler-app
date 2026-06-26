package io.github.spartamo1.schedulerapp.domain.comment.service;

import io.github.spartamo1.schedulerapp.domain.comment.dto.CommentDto;
import io.github.spartamo1.schedulerapp.domain.comment.dto.CreateCommentDto;
import io.github.spartamo1.schedulerapp.domain.comment.mapper.CommentMapper;
import io.github.spartamo1.schedulerapp.entity.Comment;
import io.github.spartamo1.schedulerapp.entity.Schedule;
import io.github.spartamo1.schedulerapp.repository.CommentRepository;
import io.github.spartamo1.schedulerapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentDto create(Integer scheduleId, CreateCommentDto createCommentDto) {
        validateCreateCommentDto(createCommentDto);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Schedule not found with id: " + scheduleId)
                );

        // comment 가 10개 이상이면 throw err
        Integer commentCnt = commentRepository.countAllByScheduleId(schedule.getId());
        if (commentCnt > 10)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글을 더 이상 달 수 없습니다");

        Comment comment = commentMapper.toEntity(createCommentDto);
        comment.setSchedule(schedule);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentDto(savedComment);
    }

    private void validateCreateCommentDto(CreateCommentDto dto) {
        if (dto.getContent() == null || dto.getContent().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용은 필수입니다");

        if (dto.getCreatedBy() == null || dto.getCreatedBy().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 작성자는 필수입니다");

        if (dto.getContent().length() > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용은 100자 이하로 작성해야 합니다");
    }

}
