package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.response.ContestRegistrationResponse;
import com.divyaksh.cap.entity.ContestRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContestRegistrationMapper {

    @Mapping(target = "contestId", source = "contest.id")
    @Mapping(target = "contestTitle", source = "contest.title")
    ContestRegistrationResponse toResponse(
            ContestRegistration registration
    );
}
