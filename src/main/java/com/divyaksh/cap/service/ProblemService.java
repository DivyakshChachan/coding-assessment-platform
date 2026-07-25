package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.request.CreateProblemRequest;
import com.divyaksh.cap.dto.request.UpdateProblemRequest;
import com.divyaksh.cap.dto.response.ProblemResponse;

import java.util.List;

public interface ProblemService {

    ProblemResponse createProblem(CreateProblemRequest request);

    ProblemResponse updateProblem(Long id, UpdateProblemRequest request);

    void deleteProblem(Long id);

    ProblemResponse getProblem(Long id);

    List<ProblemResponse> getAllProblems();

    void publishProblem(Long id);

    void unpublishProblem(Long id);
}