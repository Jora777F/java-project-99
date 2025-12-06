package hexlet.code.service;

import hexlet.code.dto.task.status.TaskStatusRequest;
import hexlet.code.dto.task.status.TaskStatusResponse;

import java.util.List;

public interface TaskStatusService {
    TaskStatusResponse findById(Long id);
    List<TaskStatusResponse> findAll();
    TaskStatusResponse updateById(Long id, TaskStatusRequest taskStatusRequest);
    TaskStatusResponse save(TaskStatusRequest request);
    void deleteById(Long id);
}
