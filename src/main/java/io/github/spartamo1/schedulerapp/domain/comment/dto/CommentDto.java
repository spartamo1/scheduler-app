package io.github.spartamo1.schedulerapp.domain.comment.dto;

import io.github.spartamo1.schedulerapp.common.dto.AuditingBaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto extends AuditingBaseDto {

    @NotBlank
    @Size(min = 1, max = 10000)
    private String content;

    @NotBlank
    @Size(min = 1, max = 100)
    private String createdBy;

}
