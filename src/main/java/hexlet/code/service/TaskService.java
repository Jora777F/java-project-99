package hexlet.code.service;

import hexlet.code.dto.task.TaskFilterParams;
import hexlet.code.dto.task.TaskRequestDto;
import hexlet.code.dto.task.TaskResponseDto;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    //TODO: Добавить LabelRepository
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskStatusRepository taskStatusRepository;
    private final UserRepository userRepository;
    private final TaskSpecification taskSpecification;

    public TaskResponseDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with id " + id));
        return taskMapper.toTaskResponse(task);
    }

    public List<TaskResponseDto> findAll(TaskFilterParams taskParams) {
        Specification<Task> specification = taskSpecification.build(taskParams);
        return taskRepository.findAll(specification)
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public TaskResponseDto save(TaskRequestDto taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        setAssociations(taskRequest, task);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }

    public TaskResponseDto updateById(Long id, TaskRequestDto taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        // Updated only basic fields
        Task updated = taskMapper.partialUpdate(taskRequest, task);

        setAssociations(taskRequest, updated);

        Task saved = taskRepository.save(task);
        return taskMapper.toTaskResponse(saved);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    private void setAssociations(TaskRequestDto taskRequest, Task task) {
        TaskStatus taskStatus = null;
        if (taskRequest.getStatus() != null) {
            taskStatus = taskStatusRepository.findBySlug(taskRequest.getStatus())
                    .orElseThrow(() -> new ResourceNotFoundException("Task status not found"));
        }

        User user = null;
        if (taskRequest.getAssigneeId() != null) {
            user = userRepository.findById(taskRequest.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id "
                            + taskRequest.getAssigneeId()));
        }

        //TODO: Исправить замечания с labels
        task.setTaskStatus(taskStatus);
        task.setAssignee(user);
    }
}
