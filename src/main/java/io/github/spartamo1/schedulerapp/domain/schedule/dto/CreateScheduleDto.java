package io.github.spartamo1.schedulerapp.domain.schedule.dto;

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
public class CreateScheduleDto {

    private String title;

    private String content;

    private String createdBy;

    private String password;

}
