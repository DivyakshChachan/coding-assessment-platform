package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.request.CreateTestCaseRequest;
import com.divyaksh.cap.dto.request.UpdateTestCaseRequest;
import com.divyaksh.cap.dto.response.TestCaseResponse;
import com.divyaksh.cap.entity.Problem;
import com.divyaksh.cap.entity.TestCase;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.mapper.TestCaseMapper;
import com.divyaksh.cap.repository.ProblemRepository;
import com.divyaksh.cap.repository.TestCaseRepository;
import com.divyaksh.cap.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;
    private final TestCaseMapper testCaseMapper;

    @Override
    public TestCaseResponse createTestCase(Long problemId,
                                           CreateTestCaseRequest request) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        TestCase testCase = testCaseMapper.toEntity(request);
        testCase.setProblem(problem);

        TestCase savedTestCase = testCaseRepository.save(testCase);

        return testCaseMapper.toResponse(savedTestCase);
    }

    @Override
    public List<TestCaseResponse> getTestCases(Long problemId) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        return testCaseRepository.findByProblem(problem)
                .stream()
                .map(testCaseMapper::toResponse)
                .toList();
    }

    @Override
    public List<TestCaseResponse> getSampleTestCases(Long problemId) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        return testCaseRepository.findByProblemAndSampleTrue(problem)
                .stream()
                .map(testCaseMapper::toResponse)
                .toList();
    }

    @Override
    public TestCaseResponse updateTestCase(Long testCaseId,
                                           UpdateTestCaseRequest request) {

        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Test case not found."));

        testCaseMapper.updateTestCaseFromRequest(request, testCase);

        TestCase updatedTestCase = testCaseRepository.save(testCase);

        return testCaseMapper.toResponse(updatedTestCase);
    }

    @Override
    public void deleteTestCase(Long testCaseId) {

        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Test case not found."));

        testCaseRepository.delete(testCase);
    }
}