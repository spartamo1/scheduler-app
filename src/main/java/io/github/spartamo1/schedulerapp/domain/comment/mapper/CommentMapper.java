package io.github.spartamo1.schedulerapp.domain.comment.mapper;

import io.github.spartamo1.schedulerapp.domain.comment.dto.CommentDto;
import io.github.spartamo1.schedulerapp.domain.comment.dto.CreateCommentDto;
import io.github.spartamo1.schedulerapp.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);

    Comment toEntity(CreateCommentDto createCommentDto);

}
