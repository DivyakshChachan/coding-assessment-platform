package com.divyaksh.cap.dto.request;

import com.divyaksh.cap.entity.enums.ContestVisibility;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UpdateContestRequest(

        @NotBlank(message = "Title cannot be blank.")
        String title,

        String description,

        LocalDateTime registrationStart,

        LocalDateTime registrationEnd,

        LocalDateTime startTime,

        LocalDateTime endTime,

        ContestVisibility visibility

) {}
