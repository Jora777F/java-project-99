package hexlet.code.service;

import hexlet.code.dto.label.LabelRequestDto;
import hexlet.code.dto.label.LabelResponseDto;

import java.util.List;

public interface LabelService {
    LabelResponseDto findById(Long id);
    List<LabelResponseDto> findAll();
    LabelResponseDto save(LabelRequestDto labelRequest);
    LabelResponseDto updateById(Long id, LabelRequestDto labelRequest);
    void deleteById(Long id);
}
