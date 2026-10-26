package com.ch4_schedule_dev.controller;

import com.ch4_schedule_dev.dto.LoginRequestDto;
import com.ch4_schedule_dev.entity.User;
import com.ch4_schedule_dev.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        // 1) 이메일 검증
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 유저가 없습니다."));
        // 2) 비밀번호 검증
        if (!user.getPassword().equals(requestDto.getPassword())) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
        // 3) 세션 생성 (존재하면 가져오고, 없으면 새로 생성)
        HttpSession session = request.getSession();
        // 4) 세션에 로그인한 유저의 고유 고유 ID(PK) 보관하기
        session.setAttribute("userId", user.getId());
        return ResponseEntity.ok("로그인 성공! 환영합니다, " + user.getUsername() + "님.");
    }

    @GetMapping("/me")
    public ResponseEntity<String> checkLogin(HttpServletRequest request) {
        // false를 넣으면 기존 세션이 없을 때 새로 만들지 않고 null을 반환합니다.
        HttpSession session = request.getSession(false);
        // 세션 자체가 없거나, 로그인 세션 정보("userId")가 없는 경우 미인증 처리
        if (session == null || session.getAttribute("userId") == null) {
            return new ResponseEntity<>("로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);
        }
        // 세션에서 유저 식별자 꺼내기
        Long userId = (Long) session.getAttribute("userId");
        // DB에서 최신 유저 정보 조회
        User loggedInUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return ResponseEntity.ok("현재 인증된 유저: " + loggedInUser.getUsername());
    }

    // 로그아웃 구현 (기존 로그인 세션 무효화)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
