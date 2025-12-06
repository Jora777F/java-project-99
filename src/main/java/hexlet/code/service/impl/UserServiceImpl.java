package hexlet.code.service.impl;

import hexlet.code.dto.user.UserRequestDto;
import hexlet.code.dto.user.UserResponseDto;
import hexlet.code.dto.user.UserUpdateDto;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encodedPassword);
        User user = userMapper.toEntity(userRequestDto);
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }

    public UserResponseDto updateById(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));

        if (userUpdateDto.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(userUpdateDto.getPassword());
            userUpdateDto.setPassword(encodedPassword);
        }

        User updated = userMapper.partialUpdate(userUpdateDto, user);
        User saved = userRepository.save(updated);
        return userMapper.toDto(saved);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
