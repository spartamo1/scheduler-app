package io.github.spartamo1.schedulerapp.domain.schedule.mapper;

import io.github.spartamo1.schedulerapp.domain.schedule.dto.CreateScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.ScheduleDto;
import io.github.spartamo1.schedulerapp.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ScheduleMapper {

    ScheduleDto toScheduleDto(Schedule schedule);

    Schedule toEntity(CreateScheduleDto createScheduleDto);

}
