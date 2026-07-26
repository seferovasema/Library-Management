package com.sema.librarymanagment.mapper;

import com.sema.librarymanagment.dto.request.RegisterRequestDto;
import com.sema.librarymanagment.dto.response.UserResponseDto;
import com.sema.librarymanagment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequestDto request);

    UserResponseDto toDto(User user);
}
