package io.github.spartamo1.schedulerapp.domain.comment.dto;

import io.github.spartamo1.schedulerapp.common.dto.AuditingBaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto extends AuditingBaseDto {

    private String content;

    private String createdBy;

}
