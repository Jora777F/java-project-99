package hexlet.code.mapper;

import hexlet.code.dto.task.TaskRequestDto;
import hexlet.code.dto.task.TaskResponseDto;
import hexlet.code.model.Task;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    //TODO: Исправить замечания, связанные с labels

    @Mapping(source = "content", target = "description")
    @Mapping(source = "title", target = "name")
    @Mapping(target = "taskStatus", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Task toTask(TaskRequestDto taskRequestDto);

    @InheritConfiguration(name = "toTask")
    Task partialUpdate(TaskRequestDto taskRequest, @MappingTarget Task task);

    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskResponseDto toTaskResponse(Task task);

}
