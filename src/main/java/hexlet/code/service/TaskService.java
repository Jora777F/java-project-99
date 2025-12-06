package hexlet.code.service;

import hexlet.code.dto.task.TaskFilterParams;
import hexlet.code.dto.task.TaskRequestDto;
import hexlet.code.dto.task.TaskResponseDto;

import java.util.List;

public interface TaskService {
    TaskResponseDto findById(Long id);
    List<TaskResponseDto> findAll(TaskFilterParams taskParams);
    TaskResponseDto save(TaskRequestDto taskRequest);
    TaskResponseDto updateById(Long id, TaskRequestDto taskRequest);
    void deleteById(Long id);
}
