package io.github.spartamo1.schedulerapp.domain.schedule.dto;

import io.github.spartamo1.schedulerapp.common.dto.AuditingBaseDto;
import io.github.spartamo1.schedulerapp.domain.comment.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDto extends AuditingBaseDto {

    private String title;

    private String content;

    private String createdBy;

    private List<CommentDto> comments;

}
