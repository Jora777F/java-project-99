package hexlet.code.service.impl;

import hexlet.code.dto.task.status.TaskStatusRequest;
import hexlet.code.dto.task.status.TaskStatusResponse;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusMapper taskStatusMapper;

    public TaskStatusResponse findById(Long id) {
        TaskStatus taskStatus = taskStatusRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task status not found with id: " + id));
        return taskStatusMapper.toDto(taskStatus);
    }

    public List<TaskStatusResponse> findAll() {
        return taskStatusRepository.findAll()
                .stream()
                .map(taskStatusMapper::toDto)
                .toList();
    }

    public TaskStatusResponse updateById(Long id, TaskStatusRequest taskStatusRequest) {
        TaskStatus taskStatus = taskStatusRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task status not found with id: " + id));
        TaskStatus updated = taskStatusMapper.partialUpdate(taskStatusRequest, taskStatus);
        TaskStatus saved = taskStatusRepository.save(updated);
        return taskStatusMapper.toDto(saved);
    }

    public TaskStatusResponse save(TaskStatusRequest request) {
        TaskStatus taskStatus = taskStatusMapper.toEntity(request);
        TaskStatus saved = taskStatusRepository.save(taskStatus);
        return taskStatusMapper.toDto(saved);
    }

    public void deleteById(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
