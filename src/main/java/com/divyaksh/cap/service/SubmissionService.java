package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.request.SubmitSolutionRequest;
import com.divyaksh.cap.dto.response.SubmissionResponse;

import java.util.List;

public interface SubmissionService {

    SubmissionResponse submit(
            Long contestId,
            Long problemId,
            SubmitSolutionRequest request
    );

    SubmissionResponse getSubmission(Long submissionId);

    List<SubmissionResponse> getMySubmissions();

    List<SubmissionResponse> getContestSubmissions(Long contestId);

}