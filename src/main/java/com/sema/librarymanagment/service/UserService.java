package com.sema.librarymanagment.service;

import com.sema.librarymanagment.dto.request.LoginRequestDto;
import com.sema.librarymanagment.dto.request.RegisterRequestDto;
import com.sema.librarymanagment.dto.response.LoginResponseDto;
import com.sema.librarymanagment.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto request);
}
