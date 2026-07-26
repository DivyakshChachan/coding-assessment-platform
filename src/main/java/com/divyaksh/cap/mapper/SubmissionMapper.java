package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.response.SubmissionResponse;
import com.divyaksh.cap.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    @Mapping(target = "contestId", source = "contest.id")
    @Mapping(target = "problemId", source = "problem.id")
    SubmissionResponse toResponse(Submission submission);

}