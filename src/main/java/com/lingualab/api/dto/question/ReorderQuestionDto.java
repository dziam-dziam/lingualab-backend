package com.lingualab.api.dto.question;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReorderQuestionDto {
    @NotNull
    private UUID questionId;

    @NotNull
    private Integer displayOrder;
}
