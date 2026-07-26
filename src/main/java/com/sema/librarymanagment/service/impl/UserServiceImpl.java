package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.dto.request.LoginRequestDto;
import com.sema.librarymanagment.dto.request.RegisterRequestDto;
import com.sema.librarymanagment.dto.response.LoginResponseDto;
import com.sema.librarymanagment.dto.response.UserResponseDto;
import com.sema.librarymanagment.enums.Role;
import com.sema.librarymanagment.exception.EmailAlreadyExistsException;
import com.sema.librarymanagment.exception.UserAlreadyExistsException;
import com.sema.librarymanagment.mapper.UserMapper;
import com.sema.librarymanagment.repository.UserRepository;
import com.sema.librarymanagment.security.JwtService;
import com.sema.librarymanagment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        var user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);

        var userSaved = userRepository.save(user);
        return userMapper.toDto(userSaved);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );
        String token = jwtService.generateToken(request.getUsername());
        return new LoginResponseDto(token);
    }
}
