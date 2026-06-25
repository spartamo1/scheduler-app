package io.github.spartamo1.schedulerapp.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AuditingBaseDto {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
