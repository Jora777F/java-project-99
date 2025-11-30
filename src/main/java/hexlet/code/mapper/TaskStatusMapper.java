package hexlet.code.mapper;

import hexlet.code.dto.task.status.TaskStatusRequest;
import hexlet.code.dto.task.status.TaskStatusResponse;
import hexlet.code.model.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskStatusMapper {

    TaskStatusResponse toDto(TaskStatus taskStatus);
    TaskStatus toEntity(TaskStatusRequest taskStatusRequest);
    TaskStatus partialUpdate(TaskStatusRequest taskStatusRequest, @MappingTarget TaskStatus taskStatus);
}
