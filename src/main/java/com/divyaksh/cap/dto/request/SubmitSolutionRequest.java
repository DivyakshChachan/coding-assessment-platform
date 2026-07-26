package com.divyaksh.cap.dto.request;

import com.divyaksh.cap.entity.enums.ProgrammingLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmitSolutionRequest(

        @NotNull(message = "Programming language is required.")
        ProgrammingLanguage language,

        @NotBlank(message = "Source code cannot be empty.")
        String sourceCode

) {}