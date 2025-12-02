package hexlet.code.mapper;

import hexlet.code.dto.task.TaskRequestDto;
import hexlet.code.dto.task.TaskResponseDto;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    @Mapping(source = "content", target = "description")
    @Mapping(source = "title", target = "name")
    @Mapping(target = "taskStatus", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "labels", ignore = true)
    Task toTask(TaskRequestDto taskRequestDto);

    @InheritConfiguration(name = "toTask")
    Task partialUpdate(TaskRequestDto taskRequest, @MappingTarget Task task);

    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(target = "taskLabelIds", expression = "java(labelsToLabelIds(task.getLabels()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    TaskResponseDto toTaskResponse(Task task);

    default Set<Long> labelsToLabelIds(Set<Label> labels) {
        if (labels == null) {
            return Set.of();
        }

        return labels.stream()
                .map(Label::getId)
                .collect(Collectors.toSet());
    }
}
