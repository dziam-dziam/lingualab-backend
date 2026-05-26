package com.lingualab.api.controller;

import com.lingualab.api.dto.response.ResponseSubmitRequestDto;
import com.lingualab.api.dto.response.SubmissionResponseDto;
import com.lingualab.api.service.ExportService;
import com.lingualab.api.service.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;
    private final ExportService exportService;

    @PostMapping("/public/{publicId}")
    public SubmissionResponseDto submitPublic(
            @PathVariable String publicId,
            @Valid @RequestBody ResponseSubmitRequestDto request
    ) {
        return responseService.submitPublicResponses(publicId, request);
    }

    @GetMapping("/survey/{surveyId}")
    public List<SubmissionResponseDto> getResponses(@PathVariable UUID surveyId) {
        return responseService.getResponses(surveyId);
    }

    @GetMapping("/survey/{surveyId}/export")
    public ResponseEntity<byte[]> exportCsv(@PathVariable UUID surveyId) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=lingualab-results.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(exportService.exportCsv(surveyId));
    }
}
