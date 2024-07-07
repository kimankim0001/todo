package com.sparta.todo.service;

import com.sparta.todo.config.JwtConfig;
import com.sparta.todo.dto.LoginReqDto;
import com.sparta.todo.dto.SignupReqDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.enums.Auth;
import com.sparta.todo.jwt.JwtProvider;
import com.sparta.todo.repository.UserRepository;
import com.sparta.todo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Value("${admin.secret.key}")
    private String ADMIN_TOKEN;

    public void signUp(SignupReqDto signupReqDto) {
        String username = signupReqDto.getUsername();
        String password = passwordEncoder.encode(signupReqDto.getPassword());
        Auth auth = signupReqDto.getAuthType();

        if (Auth.ADMIN.equals(auth)) {
            if (!ADMIN_TOKEN.equals(signupReqDto.getAuthToken())) {
                throw new IllegalArgumentException(messageSource.getMessage(
                        "invalid.admin.key",
                        null,
                        "Invalid Admin Key",
                        Locale.getDefault()
                ));
            }
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException(messageSource.getMessage(
                    "already.exist.username",
                    null,
                    "Already exist username",
                    Locale.getDefault()
            ));
        }

        User user = new User(username, password, auth);

        userRepository.save(user);
    }

    @Transactional
    public List<String> login(LoginReqDto loginReqDto) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReqDto.getUsername(),
                        loginReqDto.getPassword(),
                        null
                )
        );

        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();

        String accessToken = jwtProvider.createToken(user, JwtConfig.accessTokenTime);
        String refreshToken = jwtProvider.createToken(user, JwtConfig.refreshTokenTime);

        List<String> tokens = new ArrayList<>();
        tokens.add(accessToken);
        tokens.add(refreshToken);

        user.updateRefreshToken(refreshToken);
        return tokens;
    }
}
