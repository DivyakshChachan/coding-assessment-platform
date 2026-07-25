package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.response.ContestProblemResponse;

import java.util.List;

public interface ContestProblemService {

    ContestProblemResponse addProblem(Long contestId, Long problemId);

    List<ContestProblemResponse> getContestProblems(Long contestId);

    void removeProblem(Long contestId,
                       Long problemId);
}