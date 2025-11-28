package hexlet.code.service;

import hexlet.code.dto.user.UserRequestDto;
import hexlet.code.dto.user.UserResponseDto;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDto save(UserRequestDto userRequestDto) {
        int hashCode = userRequestDto.getPassword().hashCode();
        userRequestDto.setPassword(String.valueOf(hashCode));
        User user = userMapper.toEntity(userRequestDto);
        user.setCreatedAt(Instant.now());
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }

    public UserResponseDto updateById(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));

        if (userRequestDto.getPassword() == null) {
            String encodedPassword = String.valueOf(userRequestDto.getPassword().hashCode());
            userRequestDto.setPassword(encodedPassword);
        }

        User updated = userMapper.partialUpdate(userRequestDto, user);
        User saved = userRepository.save(updated);
        return userMapper.toDto(saved);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
