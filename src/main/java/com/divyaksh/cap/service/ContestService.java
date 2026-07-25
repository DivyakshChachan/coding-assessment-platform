package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.request.CreateContestRequest;
import com.divyaksh.cap.dto.request.UpdateContestRequest;
import com.divyaksh.cap.dto.response.ContestResponse;

import java.util.List;

public interface ContestService {

    ContestResponse createContest(CreateContestRequest request);

    ContestResponse getContest(Long contestId);

    List<ContestResponse> getPublishedContests();

    ContestResponse updateContest(Long contestId,
                                  UpdateContestRequest request);

    ContestResponse publishContest(Long contestId);

    ContestResponse cancelContest(Long contestId);

    void deleteContest(Long contestId);
}