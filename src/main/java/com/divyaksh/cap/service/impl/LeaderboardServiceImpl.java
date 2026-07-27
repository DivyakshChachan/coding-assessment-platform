package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.response.LeaderboardEntryResponse;
import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.Problem;
import com.divyaksh.cap.entity.Submission;
import com.divyaksh.cap.entity.User;
import com.divyaksh.cap.entity.enums.SubmissionStatus;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.repository.ContestRepository;
import com.divyaksh.cap.repository.SubmissionRepository;
import com.divyaksh.cap.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaderboardServiceImpl implements LeaderboardService {

    private final ContestRepository contestRepository;
    private final SubmissionRepository submissionRepository;

    @Override
    @Cacheable(cacheNames = "leaderboards")
    public List<LeaderboardEntryResponse> getLeaderboard(Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        List<Submission> submissions =
                submissionRepository.findAllByContest(contest);

        Map<User, Map<Problem, Submission>> leaderboardMap = new HashMap<>();

        for (Submission submission : submissions) {

            leaderboardMap
                    .computeIfAbsent(
                            submission.getCandidate(),
                            k -> new HashMap<>())
                    .merge(
                            submission.getProblem(),
                            submission,
                            (oldSubmission, newSubmission) ->
                                    newSubmission.getScore() > oldSubmission.getScore()
                                            ? newSubmission
                                            : oldSubmission
                    );
        }

        List<LeaderboardEntryResponse> leaderboard = leaderboardMap.entrySet()
                .stream()
                .map(entry -> {

                    User user = entry.getKey();

                    Collection<Submission> bestSubmissions =
                            entry.getValue().values();

                    int totalScore = bestSubmissions.stream()
                            .mapToInt(Submission::getScore)
                            .sum();

                    int solvedProblems = (int) bestSubmissions.stream()
                            .filter(s -> s.getStatus() == SubmissionStatus.ACCEPTED)
                            .count();

                    return LeaderboardEntryResponse.builder()
                            .userId(user.getId())
                            .name(user.getUsername())
                            .email(user.getEmail())
                            .totalScore(totalScore)
                            .solvedProblems(solvedProblems)
                            .build();

                })
                .sorted(
                        Comparator.comparingInt(LeaderboardEntryResponse::totalScore)
                                .reversed()
                                .thenComparing(
                                        Comparator.comparingInt(
                                                        LeaderboardEntryResponse::solvedProblems)
                                                .reversed())
                )
                .collect(Collectors.toList());

        List<LeaderboardEntryResponse> rankedLeaderboard = new ArrayList<>();

        for (int i = 0; i < leaderboard.size(); i++) {

            LeaderboardEntryResponse entry = leaderboard.get(i);

            rankedLeaderboard.add(
                    LeaderboardEntryResponse.builder()
                            .rank(i + 1)
                            .userId(entry.userId())
                            .name(entry.name())
                            .email(entry.email())
                            .totalScore(entry.totalScore())
                            .solvedProblems(entry.solvedProblems())
                            .build()
            );
        }

        return rankedLeaderboard;
    }
}