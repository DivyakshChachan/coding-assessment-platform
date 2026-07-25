package com.divyaksh.cap.dto.response;

import java.time.LocalDateTime;

public record TestCaseResponse(

        Long id,

        String input,

        String expectedOutput,

        boolean sample,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {}