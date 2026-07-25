package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.request.CreateProblemRequest;
import com.divyaksh.cap.dto.request.UpdateProblemRequest;
import com.divyaksh.cap.dto.response.ProblemResponse;
import com.divyaksh.cap.entity.Problem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

    Problem toEntity(CreateProblemRequest request);


    @Mapping(target = "createdBy", source = "createdBy.email")
    ProblemResponse toResponse(Problem problem);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProblemFromRequest(UpdateProblemRequest request,
                                  @MappingTarget Problem problem);
}