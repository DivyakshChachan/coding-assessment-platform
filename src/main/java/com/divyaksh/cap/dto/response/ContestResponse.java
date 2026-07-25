package com.divyaksh.cap.dto.response;

import com.divyaksh.cap.entity.enums.ContestStatus;
import com.divyaksh.cap.entity.enums.ContestVisibility;

import java.time.LocalDateTime;

public record ContestResponse(

        Long id,

        String title,

        String description,

        LocalDateTime registrationStart,

        LocalDateTime registrationEnd,

        LocalDateTime startTime,

        LocalDateTime endTime,

        ContestVisibility visibility,

        ContestStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {}