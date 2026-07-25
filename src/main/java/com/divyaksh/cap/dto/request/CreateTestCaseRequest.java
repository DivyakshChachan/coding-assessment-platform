package com.divyaksh.cap.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTestCaseRequest(

        @NotBlank(message = "Input cannot be blank.")
        String input,

        @NotBlank(message = "Expected output cannot be blank.")
        String expectedOutput,

        boolean sample

) {}