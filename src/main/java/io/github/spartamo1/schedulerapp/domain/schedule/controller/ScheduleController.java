package io.github.spartamo1.schedulerapp.domain.schedule.controller;

import io.github.spartamo1.schedulerapp.domain.schedule.dto.CreateScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.DeleteScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.dto.ScheduleDto;
import io.github.spartamo1.schedulerapp.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleDto> getAll(
            @RequestParam(required = false) String createdBy
    ) {
        return scheduleService.getAllByCondition(createdBy);
    }

    @GetMapping("/{id}")
    public ScheduleDto get(@PathVariable Integer id) {
        return scheduleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> create(
            @RequestBody @Valid CreateScheduleDto createScheduleDto
    ) {
        ScheduleDto scheduleDto = scheduleService.create(createScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleDto);
    }

    @PutMapping("/{id}")
    public ScheduleDto modify(
        @PathVariable Integer id,
        @RequestBody @Valid CreateScheduleDto createScheduleDto
    ) {
        return scheduleService.update(id, createScheduleDto);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id,
            @RequestBody @Valid DeleteScheduleDto deleteScheduleDto
    ) {
        scheduleService.deleteById(id, deleteScheduleDto.getPassword());
    }

}
