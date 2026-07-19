package com.sema.librarymanagment.mapper;

import com.sema.librarymanagment.dto.request.MemberRequestDto;
import com.sema.librarymanagment.dto.response.MemberResponseDto;
import com.sema.librarymanagment.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface MemberMapper {
    MemberResponseDto toDto(Member member);

    Member toEntity(MemberRequestDto dto);
}
