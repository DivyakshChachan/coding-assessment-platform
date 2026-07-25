package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.response.ContestRegistrationResponse;

import java.util.List;

public interface ContestRegistrationService {

    ContestRegistrationResponse register(Long contestId);

    void unregister(Long contestId);

    List<ContestRegistrationResponse> getMyRegistrations();

}