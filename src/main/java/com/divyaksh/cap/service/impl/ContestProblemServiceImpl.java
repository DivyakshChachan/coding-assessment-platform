package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.response.ContestProblemResponse;
import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.ContestProblem;
import com.divyaksh.cap.entity.Problem;
import com.divyaksh.cap.exception.DuplicateResourceException;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.mapper.ContestProblemMapper;
import com.divyaksh.cap.repository.ContestProblemRepository;
import com.divyaksh.cap.repository.ContestRepository;
import com.divyaksh.cap.repository.ProblemRepository;
import com.divyaksh.cap.service.ContestProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContestProblemServiceImpl
        implements ContestProblemService {

    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final ContestProblemRepository contestProblemRepository;
    private final ContestProblemMapper contestProblemMapper;

    @Override
    public ContestProblemResponse addProblem(Long contestId, Long problemId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        if (contestProblemRepository.existsByContestAndProblem(contest, problem)) {
            throw new DuplicateResourceException(
                    "Problem already added to contest.");
        }

        ContestProblem contestProblem = ContestProblem.builder()
                .contest(contest)
                .problem(problem)
                .build();

        ContestProblem saved =
                contestProblemRepository.save(contestProblem);

        return contestProblemMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContestProblemResponse> getContestProblems(
            Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        return contestProblemRepository.findAllByContest(contest)
                .stream()
                .map(contestProblemMapper::toResponse)
                .toList();
    }

    @Override
    public void removeProblem(Long contestId,
                              Long problemId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        ContestProblem contestProblem =
                contestProblemRepository.findByContestAndProblem(
                                contest,
                                problem)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Problem is not part of this contest."));

        contestProblemRepository.delete(contestProblem);
    }
}