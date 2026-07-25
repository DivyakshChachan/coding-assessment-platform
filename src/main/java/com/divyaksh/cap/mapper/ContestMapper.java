package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.request.CreateContestRequest;
import com.divyaksh.cap.dto.request.UpdateContestRequest;
import com.divyaksh.cap.dto.response.ContestResponse;
import com.divyaksh.cap.entity.Contest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContestMapper {

    Contest toEntity(CreateContestRequest request);

    ContestResponse toResponse(Contest contest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContestFromRequest(
            UpdateContestRequest request,
            @MappingTarget Contest contest
    );
}