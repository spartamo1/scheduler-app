package io.github.spartamo1.schedulerapp.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteScheduleDto {

    @NotBlank
    @Size(min = 1, max = 100)
    private String password;

}
