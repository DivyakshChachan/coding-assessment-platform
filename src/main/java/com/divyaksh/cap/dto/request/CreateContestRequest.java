package com.divyaksh.cap.dto.request;

import com.divyaksh.cap.entity.enums.ContestVisibility;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateContestRequest(

        @NotBlank(message = "Title cannot be blank.")
        String title,

        String description,

        @NotNull(message = "Registration start is required.")
        LocalDateTime registrationStart,

        @NotNull(message = "Registration end is required.")
        @Future(message = "Registration end must be in the future.")
        LocalDateTime registrationEnd,

        @NotNull(message = "Start time is required.")
        LocalDateTime startTime,

        @NotNull(message = "End time is required.")
        LocalDateTime endTime,

        ContestVisibility visibility

) {}