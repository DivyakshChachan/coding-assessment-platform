package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.request.CreateTestCaseRequest;
import com.divyaksh.cap.dto.request.UpdateTestCaseRequest;
import com.divyaksh.cap.dto.response.TestCaseResponse;

import java.util.List;

public interface TestCaseService {

    TestCaseResponse createTestCase(Long problemId,
                                    CreateTestCaseRequest request);

    List<TestCaseResponse> getTestCases(Long problemId);

    List<TestCaseResponse> getSampleTestCases(Long problemId);

    TestCaseResponse updateTestCase(Long testCaseId,
                                    UpdateTestCaseRequest request);

    void deleteTestCase(Long testCaseId);

}