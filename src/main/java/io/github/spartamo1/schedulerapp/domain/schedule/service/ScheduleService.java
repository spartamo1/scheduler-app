package io.github.spartamo1.schedulerapp.domain.schedule.service;

import io.github.spartamo1.schedulerapp.domain.comment.dto.CommentDto;
import io.github.spartamo1.schedulerapp.domain.comment.service.CommentService;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.CreateScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.ScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.UpdateScheduleDto;
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
    private final CommentService commentService;

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
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        List<CommentDto> comments = commentService.getAllComments(id);
        ScheduleDto scheduleDto = scheduleMapper.toScheduleDto(schedule);
        scheduleDto.setComments(comments);
        return scheduleDto;
    }

    @Transactional
    public ScheduleDto create(CreateScheduleDto createScheduleDto) {
        validateCreateScheduleDto(createScheduleDto);

        Schedule schedule = scheduleMapper.toEntity(createScheduleDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleDto(savedSchedule);
    }

    @Transactional
    public ScheduleDto update(Integer id, UpdateScheduleDto updateScheduleDto) {
        validateUpdateScheduleDto(updateScheduleDto);

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        if (!schedule.getPassword().equals(updateScheduleDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 스케줄을 수정할 권한이 없습니다.");
        }

        if (validProperty(updateScheduleDto.getTitle()))
            schedule.setTitle(updateScheduleDto.getTitle());
        if (validProperty(updateScheduleDto.getCreatedBy()))
            schedule.setCreatedBy(updateScheduleDto.getCreatedBy());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleDto(savedSchedule);
    }

    @Transactional
    public void deleteById(Integer id, String password) {
        if (!validProperty(password))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력 값입니다.");

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id: " + id));

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 스케줄을 수정할 권한이 없습니다.");
        }

        scheduleRepository.deleteById(id);
    }

    private boolean validProperty(String value) {
        return value != null && !value.isBlank();
    }

    private void validateCreateScheduleDto(CreateScheduleDto dto) {
        if (!validProperty(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력 값입니다.");

        if (!validProperty(dto.getTitle()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수 입력 값입니다.");

        if (!validProperty(dto.getContent()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수 입력 값입니다.");

        if (!validProperty(dto.getCreatedBy()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자는 필수 입력 값입니다.");

        if (dto.getTitle().length() > 30)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 30자 이하로 입력해야 합니다.");

        if (dto.getContent().length() > 200)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 200자 이하로 입력해야 합니다.");
    }

    private void validateUpdateScheduleDto(UpdateScheduleDto dto) {
        if (!validProperty(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력 값입니다.");

        if (!validProperty(dto.getTitle()) && !validProperty(dto.getCreatedBy()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 작성자 중 하나는 필수 입력 값입니다.");
    }

}
