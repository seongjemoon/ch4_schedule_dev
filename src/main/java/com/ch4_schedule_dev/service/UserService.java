package com.ch4_schedule_dev.service;

import com.ch4_schedule_dev.dto.LoginRequestDto;
import com.ch4_schedule_dev.dto.UserRequestDto;
import com.ch4_schedule_dev.dto.UserResponseDto;
import com.ch4_schedule_dev.entity.User;
import com.ch4_schedule_dev.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 1. 생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // 2. 로그인 검증
    public User login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 유저가 없습니다."));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user; // 검증이 완료된 유저 객체 반환
    }
    // 3.유저 생성 (회원가입 - 비밀번호 8자 검증 적용)
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (requestDto.getPassword() == null || requestDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8글자 이상이어야 합니다.");
        }

        User user = new User(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    // 4. 유저 전체 조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    // 5. 유저 단건 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));
        return new UserResponseDto(user);
    }

    // 6. 유저 정보 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));

        user.update(requestDto.getUsername(), requestDto.getEmail()); // 변경 감지(Dirty Checking) 작동
        return new UserResponseDto(user);
    }

    // 7. 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));
        userRepository.delete(user);
    }
}
