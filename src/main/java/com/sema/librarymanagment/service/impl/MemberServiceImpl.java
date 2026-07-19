package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.dto.request.MemberRequestDto;
import com.sema.librarymanagment.dto.response.MemberResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.entity.Member;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.MemberMapper;
import com.sema.librarymanagment.repository.MemberRepository;
import com.sema.librarymanagment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public MemberResponseDto create(MemberRequestDto dto) {
        Member member = memberMapper.toEntity(dto);

        Member savedMember = memberRepository.save(member);

        return memberMapper.toDto(savedMember);
    }

    @Override
    public PageResponseDto<MemberResponseDto> getAll(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);

        Page<MemberResponseDto> dtoPage = members.map(memberMapper::toDto);
        return PageResponseDto.of(dtoPage);
    }


    @Override
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        return memberMapper.toDto(member);
    }

    @Override
    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        member.setFullName(dto.getFullName());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());

        Member updatedMember = memberRepository.save(member);

        return memberMapper.toDto(updatedMember);
    }

    @Override
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        memberRepository.delete(member);
    }
}
