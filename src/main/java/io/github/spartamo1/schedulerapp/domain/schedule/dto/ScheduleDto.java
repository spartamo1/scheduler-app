package io.github.spartamo1.schedulerapp.domain.schedule.dto;

import io.github.spartamo1.schedulerapp.common.dto.AuditingBaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto extends AuditingBaseDto {

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    @Size(min = 1, max = 10000)
    private String content;

    @NotBlank
    @Size(min = 1, max = 100)
    private String createdBy;

}
