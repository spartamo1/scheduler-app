package io.github.spartamo1.schedulerapp.domain.schedule.service;

import io.github.spartamo1.schedulerapp.domain.schedule.dto.CreateScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.ScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.mapper.ScheduleMapper;
import io.github.spartamo1.schedulerapp.entity.Schedule;
import io.github.spartamo1.schedulerapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

    private List<ScheduleDto> getAll() {
        return scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt")).stream()
                .map(scheduleMapper::toScheduleDto)
                .toList();
    }

    public List<ScheduleDto> getAllByCondition(String createdBy) {
        if (createdBy == null || createdBy.isBlank())
            return getAll();

        List<Schedule> schedules = scheduleRepository.findAllByCreatedBy(createdBy, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return schedules.stream()
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

    @Transactional
    public ScheduleDto update(Integer id, CreateScheduleDto createScheduleDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        if (!schedule.getPassword().equals(createScheduleDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 스케줄을 수정할 권한이 없습니다.");
        }

        schedule.setTitle(createScheduleDto.getTitle());
//        schedule.setContent(createScheduleDto.getContent());
        schedule.setCreatedBy(createScheduleDto.getCreatedBy());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleDto(savedSchedule);
    }

    @Transactional
    public void deleteById(Integer id, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 스케줄을 수정할 권한이 없습니다.");
        }

        scheduleRepository.deleteById(id);
    }

}
