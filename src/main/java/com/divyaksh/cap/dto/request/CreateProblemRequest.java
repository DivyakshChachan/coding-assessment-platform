package com.divyaksh.cap.dto.request;

import com.divyaksh.cap.constant.Difficulty;
import com.divyaksh.cap.constant.Tag;
import jakarta.validation.constraints.*;

import java.util.Set;

public record CreateProblemRequest(

        @NotBlank
        @Size(max = 200)
        String title,

        @NotBlank
        String description,

        @NotBlank
        String inputFormat,

        @NotBlank
        String outputFormat,

        @NotBlank
        String constraints,

        @NotNull
        Difficulty difficulty,

        Set<Tag> tags,

        @NotNull
        @Positive
        Integer timeLimitMs,

        @NotNull
        @Positive
        Integer memoryLimitMb
) {}