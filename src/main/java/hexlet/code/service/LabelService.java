package hexlet.code.service;

import hexlet.code.dto.label.LabelRequestDto;
import hexlet.code.dto.label.LabelResponseDto;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public LabelResponseDto findById(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Label not found with id " + id));
        return labelMapper.toLabelResponse(label);
    }

    public List<LabelResponseDto> findAll() {
        return labelRepository.findAll()
                .stream()
                .map(labelMapper::toLabelResponse)
                .toList();
    }

    public LabelResponseDto save(LabelRequestDto labelRequest) {
        Label label = labelMapper.toEntity(labelRequest);
        Label savedLabel = labelRepository.save(label);
        return labelMapper.toLabelResponse(savedLabel);
    }

    public LabelResponseDto updateById(Long id, LabelRequestDto labelRequest) {
        Label label = labelRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Label not found with id " + id));
        Label updated = labelMapper.partialUpdate(labelRequest, label);
        Label saved = labelRepository.save(updated);
        return labelMapper.toLabelResponse(saved);
    }

    public void deleteById(Long id) {
        labelRepository.deleteById(id);
    }
}
