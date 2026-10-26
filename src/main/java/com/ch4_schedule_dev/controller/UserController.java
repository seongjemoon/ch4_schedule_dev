package com.ch4_schedule_dev.controller;

import com.ch4_schedule_dev.dto.UserRequestDto;
import com.ch4_schedule_dev.dto.UserResponseDto;
import com.ch4_schedule_dev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 1. 생성자 주입
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 2. 유저 생성 (회원가입) -> POST http://localhost:8080/api/users
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 3. 유저 전체 조회 -> GET http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> responseDtos = userService.getAllUsers();
        return ResponseEntity.ok(responseDtos);
    }

    // 4. 유저 단건 조회 -> GET http://localhost:8080/api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto responseDto = userService.getUser(id);
        return ResponseEntity.ok(responseDto);
    }

    // 5. 유저 정보 수정 -> PUT http://localhost:8080/api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.updateUser(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 6. 유저 삭제 -> DELETE http://localhost:8080/api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("유저가 성공적으로 삭제되었습니다. ID: " + id);
    }
}
