package com.sema.librarymanagment.serviceImpl;

import com.sema.librarymanagment.dto.request.MemberRequestDto;
import com.sema.librarymanagment.dto.response.MemberResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.entity.Member;
import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.mapper.MemberMapper;
import com.sema.librarymanagment.repository.MemberRepository;
import com.sema.librarymanagment.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void create_ShouldReturnMemberResponseDto() {
        MemberRequestDto request = new MemberRequestDto(
                "Sema Seferova",
                "sema@gmail.com",
                "+994501234567");

        Member member = new Member(1L,
                "Sema Seferova",
                "sema@gmail.com",
                "+994501234567");

        MemberResponseDto response = new MemberResponseDto(
                1L,
                "Sema Seferova",
                "sema@gmail.com",
                "+994501234567",
                null);

        when(memberMapper.toEntity(request)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);
        when(memberMapper.toDto(member)).thenReturn(response);

        MemberResponseDto result = memberService.create(request);

        assertEquals("Sema Seferova", result.getFullName());

        verify(memberRepository).save(member);
        verify(memberMapper).toDto(member);
    }

    @Test
    void findById_ShouldReturnMemberResponseDto() {
        Long id = 1L;
        Member member = new Member
                (1L,
                        "Sema Seferova",
                        "sema@gmail.com",
                        "+994501234567");
        MemberResponseDto response = new MemberResponseDto(
                1L,
                "Sema Seferova",
                "sema@gmail.com",
                "+994501234567",
                null);

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        when(memberMapper.toDto(member)).thenReturn(response);

        MemberResponseDto result = memberService.findById(id);

        assertEquals("Sema Seferova", result.getFullName());

        verify(memberRepository).findById(id);
        verify(memberMapper).toDto(member);
    }

    @Test
    void findById_ShouldThrowException_WhenMemberNotFound() {
        Long id = 99L;

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> memberService.findById(id));

        verify(memberRepository).findById(id);
        verifyNoInteractions(memberMapper);
    }

    @Test
    void update_ShouldReturnUpdatedMember() {
        Long id = 1L;
        MemberRequestDto request = new MemberRequestDto(
                "Leyla Veliyeva",
                "leyla@gmail.com",
                "+994501111111");

        Member member = new Member(1L,
                "Old Name",
                "old@gmail.com",
                "+994500000000");

        MemberResponseDto response = new MemberResponseDto(
                1L,
                "Leyla Veliyeva",
                "leyla@gmail.com",
                "+994501111111",
                null);

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        when(memberRepository.save(member)).thenReturn(member);
        when(memberMapper.toDto(member)).thenReturn(response);

        MemberResponseDto result = memberService.update(id, request);


        assertEquals("Leyla Veliyeva", member.getFullName());
        assertEquals("leyla@gmail.com", member.getEmail());
        assertEquals("+994501111111", member.getPhone());

        assertEquals("Leyla Veliyeva", result.getFullName());

        verify(memberRepository).save(member);
        verify(memberMapper).toDto(member);
    }

    @Test
    void update_ShouldThrowException_WhenMemberNotFound() {
        Long id = 99L;
        MemberRequestDto request = new MemberRequestDto(
                "Leyla Veliyeva", "leyla@gmail.com", "+994501111111");

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> memberService.update(id, request));

        verify(memberRepository, never()).save(any());
        verifyNoInteractions(memberMapper);
    }

    @Test
    void delete_ShouldDeleteMember() {
        Long id = 1L;
        Member member = new Member();
        member.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        memberService.delete(id);

        verify(memberRepository).findById(id);
        verify(memberRepository).delete(member);
    }

    @Test
    void delete_ShouldThrowException_WhenMemberNotFound() {
        Long id = 99L;

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> memberService.delete(id));

        verify(memberRepository, never()).delete(any());
    }

    @Test
    void getAll_ShouldReturnPageResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Member member =
                new Member(1L,
                        "Sema Seferova",
                        "sema@gmail.com",
                        "+994501234567");
        MemberResponseDto response = new MemberResponseDto(
                1L,
                "Sema Seferova",
                "sema@gmail.com",
                "+994501234567",
                null);

        Page<Member> page = new PageImpl<>(List.of(member));

        when(memberRepository.findAll(pageable)).thenReturn(page);
        when(memberMapper.toDto(member)).thenReturn(response);

        PageResponseDto<MemberResponseDto> result = memberService.getAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Sema Seferova", result.getContent().get(0).getFullName());

        verify(memberRepository).findAll(pageable);
        verify(memberMapper).toDto(member);
    }
}