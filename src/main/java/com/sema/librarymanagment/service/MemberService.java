package com.sema.librarymanagment.service;

import com.sema.librarymanagment.dto.request.MemberRequestDto;
import com.sema.librarymanagment.dto.response.MemberResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import org.springframework.data.domain.Pageable;


public interface MemberService {
    MemberResponseDto create(MemberRequestDto dto);

    PageResponseDto<MemberResponseDto> getAll(Pageable pageable);

    MemberResponseDto findById(Long id);

    MemberResponseDto update(Long id, MemberRequestDto dto);

    void delete(Long id);
}
