package com.ch4_schedule_dev.controller;

import com.ch4_schedule_dev.dto.LoginRequestDto;
import com.ch4_schedule_dev.entity.User;
import com.ch4_schedule_dev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        // 1) 로그인 검증 비즈니스 로직은 서비스에 위임
        User user = userService.login(requestDto);

        // 2) 세션 생성 및 유저 ID 보관 (웹/세션 처리는 컨트롤러 담당)
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok("로그인 성공! 환영합니다, " + user.getUsername() + "님.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}