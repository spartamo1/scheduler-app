package io.github.spartamo1.schedulerapp.domain.schedule.service;

import io.github.spartamo1.schedulerapp.domain.schedule.dto.CreateScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.ScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.mapper.ScheduleMapper;
import io.github.spartamo1.schedulerapp.entity.Schedule;
import io.github.spartamo1.schedulerapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public List<ScheduleDto> getAll() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toScheduleDto)
                .toList();
    }

    public ScheduleDto getById(Integer id) {
        return scheduleRepository.findById(id)
                .map(scheduleMapper::toScheduleDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));
    }

    @Transactional
    public ScheduleDto create(CreateScheduleDto createScheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(createScheduleDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleDto(savedSchedule);
    }

}
