package io.github.spartamo1.schedulerapp.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentDto {

    private String content;

    private String createdBy;

    private String password;

}
