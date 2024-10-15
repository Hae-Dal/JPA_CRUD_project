package com.sparta.jpa_crud_project.service;

import com.sparta.jpa_crud_project.dto.UserRequestDto;
import com.sparta.jpa_crud_project.dto.UserResponseDto;
import com.sparta.jpa_crud_project.encoder.PasswordEncoder;
import com.sparta.jpa_crud_project.entity.User;
import com.sparta.jpa_crud_project.repository.UserRepository;
import com.sparta.jpa_crud_project.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public String registerUser(UserRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return jwtUtil.generateToken(user.getUserName());
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
