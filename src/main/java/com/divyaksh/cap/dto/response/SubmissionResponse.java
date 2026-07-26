package com.divyaksh.cap.dto.response;

import com.divyaksh.cap.entity.enums.ProgrammingLanguage;
import com.divyaksh.cap.entity.enums.SubmissionStatus;

import java.time.LocalDateTime;

public record SubmissionResponse(

        Long id,

        Long contestId,

        Long problemId,

        ProgrammingLanguage language,

        SubmissionStatus status,

        Integer score,

        Integer attemptNumber,

        LocalDateTime submittedAt

) {}