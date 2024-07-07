package com.sparta.todo.controller;

import com.sparta.todo.dto.CommonResDto;
import com.sparta.todo.dto.SignupReqDto;
import com.sparta.todo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/signup")
    public ResponseEntity<CommonResDto<Void>> signup(@Valid @RequestBody SignupReqDto signupReqDto) {

        authService.signUp(signupReqDto);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "회원가입에 성공하였습니다!", null));
    }


}
