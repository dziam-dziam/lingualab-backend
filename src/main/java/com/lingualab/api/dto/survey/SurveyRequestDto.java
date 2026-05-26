package com.lingualab.api.dto.survey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequestDto {
    @NotBlank
    @Size(max = 180)
    private String title;

    @Size(max = 2000)
    private String description;
}
