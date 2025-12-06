package hexlet.code.service;

import hexlet.code.dto.user.UserRequestDto;
import hexlet.code.dto.user.UserResponseDto;
import hexlet.code.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserResponseDto findById(Long id);
    List<UserResponseDto> findAll();
    UserResponseDto save(UserRequestDto userRequestDto);
    UserResponseDto updateById(Long id, UserUpdateDto userUpdateDto);
    void deleteById(Long id);
}
