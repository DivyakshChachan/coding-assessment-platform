package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.response.ContestProblemResponse;
import com.divyaksh.cap.entity.ContestProblem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContestProblemMapper {

    @Mapping(target = "problemId", source = "problem.id")
    @Mapping(target = "problemTitle", source = "problem.title")
    @Mapping(target = "problemSlug", source = "problem.slug")
    ContestProblemResponse toResponse(ContestProblem contestProblem);

}