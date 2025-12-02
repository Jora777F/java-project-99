package hexlet.code.mapper;

import hexlet.code.dto.label.LabelRequestDto;
import hexlet.code.dto.label.LabelResponseDto;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LabelMapper {

    LabelResponseDto toLabelResponse(Label label);
    Label toEntity(LabelRequestDto labelRequest);
    Label partialUpdate(LabelRequestDto labelRequest, @MappingTarget Label label);
}
