package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.request.CreateTestCaseRequest;
import com.divyaksh.cap.dto.request.UpdateTestCaseRequest;
import com.divyaksh.cap.dto.response.TestCaseResponse;
import com.divyaksh.cap.entity.TestCase;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {

    TestCase toEntity(CreateTestCaseRequest request);

    TestCaseResponse toResponse(TestCase testCase);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTestCaseFromRequest(
            UpdateTestCaseRequest request,
            @MappingTarget TestCase testCase
    );
}