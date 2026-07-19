package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.dto.request.MemberRequestDto;
import com.sema.librarymanagment.dto.response.MemberResponseDto;
import com.sema.librarymanagment.dto.response.PageResponseDto;
import com.sema.librarymanagment.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(
        name = "Member",
        description = "Member management operations"
)
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "Create member")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<MemberResponseDto> create(@Valid @RequestBody MemberRequestDto dto) {

        MemberResponseDto response = memberService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Get all members")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Members retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponseDto<MemberResponseDto>> getAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(memberService.getAll(pageable));
    }

    @Operation(summary = "Get member by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(memberService.findById(id));
    }

    @Operation(summary = "Update member")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> update(@PathVariable Long id,
                                                    @Valid @RequestBody MemberRequestDto dto) {

        return ResponseEntity.ok(memberService.update(id, dto));
    }

    @Operation(summary = "Delete member")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        memberService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
