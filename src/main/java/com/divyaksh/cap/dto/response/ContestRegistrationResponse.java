package com.divyaksh.cap.dto.response;

import java.time.LocalDateTime;

public record ContestRegistrationResponse(

        Long id,

        Long contestId,

        String contestTitle,

        LocalDateTime registeredAt

) {}