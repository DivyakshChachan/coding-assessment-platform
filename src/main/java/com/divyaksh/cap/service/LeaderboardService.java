package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.response.LeaderboardEntryResponse;

import java.util.List;

public interface LeaderboardService {

    List<LeaderboardEntryResponse> getLeaderboard(Long contestId);

}