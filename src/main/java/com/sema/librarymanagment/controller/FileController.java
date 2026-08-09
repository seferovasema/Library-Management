package com.sema.librarymanagment.controller;

import com.sema.librarymanagment.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(
        name = "File",
        description = "File upload and download operations"
)
public class FileController {

    private final FileService fileService;

    @Operation(
            summary = "Upload a file",
            description = "Uploads JPG, JPEG, PNG or WEBP file. Maximum size is 5 MB."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "File uploaded successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid file"
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String fileName = fileService.uploadFile(file);

        return ResponseEntity.ok(
                "File uploaded successfully: " + fileName
        );
    }

    @Operation(
            summary = "Download a file",
            description = "Downloads a previously uploaded file."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "File downloaded successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "File not found"
            )
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable String fileName
    ) throws IOException {

        byte[] data = fileService.downloadFile(fileName);

        ByteArrayResource resource =
                new ByteArrayResource(data);

        String contentType =
                URLConnection.guessContentTypeFromName(fileName);

        if (contentType == null) {
            contentType =
                    MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\""
                )
                .contentLength(data.length)
                .body(resource);
    }
}