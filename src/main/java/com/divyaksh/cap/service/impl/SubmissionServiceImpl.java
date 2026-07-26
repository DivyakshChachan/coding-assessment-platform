package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.request.SubmitSolutionRequest;
import com.divyaksh.cap.dto.response.SubmissionResponse;
import com.divyaksh.cap.entity.*;
import com.divyaksh.cap.entity.enums.ContestStatus;
import com.divyaksh.cap.entity.enums.SubmissionStatus;
import com.divyaksh.cap.exception.IllegalOperationException;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.mapper.SubmissionMapper;
import com.divyaksh.cap.repository.*;
import com.divyaksh.cap.security.CustomUserDetails;
import com.divyaksh.cap.service.JudgeService;
import com.divyaksh.cap.service.SubmissionService;
import com.divyaksh.cap.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionMapper submissionMapper;
    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final ContestProblemRepository contestProblemRepository;
    private final ContestRegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final JudgeService judgeService;

    @Override
    public SubmissionResponse submit(
            Long contestId,
            Long problemId,
            SubmitSolutionRequest request) {

        User candidate = getCurrentCandidate();

        Contest contest = getContest(contestId);

        Problem problem = getProblem(problemId);

        validateContest(contest);

        validateContestProblem(contest, problem);

        validateRegistration(contest, candidate);

        validateContestWindow(contest);

        int attemptNumber = calculateAttemptNumber(
                candidate,
                contest,
                problem
        );

        Submission submission = Submission.builder()
                .candidate(candidate)
                .contest(contest)
                .problem(problem)
                .language(request.language())
                .sourceCode(request.sourceCode())
                .status(SubmissionStatus.PENDING)
                .score(0)
                .attemptNumber(attemptNumber)
                .build();


        Submission savedSubmission =
                submissionRepository.save(submission);

        judgeService.judge(savedSubmission);

        submissionRepository.flush();

        return submissionMapper.toResponse(savedSubmission);
    }

    @Override
    @Transactional(readOnly = true)
    public SubmissionResponse getSubmission(Long submissionId) {

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Submission not found."
                        ));

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        User user = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."
                        ));

        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isAdmin &&
                !submission.getCandidate().getId().equals(user.getId())) {

            throw new AccessDeniedException(
                    "You are not authorized to view this submission."
            );
        }

        return submissionMapper.toResponse(submission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getMySubmissions() {

        User candidate = getCurrentCandidate();

        return submissionRepository
                .findAllByCandidate(candidate)
                .stream()
                .map(submissionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getContestSubmissions(
            Long contestId) {

        Contest contest = getContest(contestId);

        return submissionRepository
                .findAllByContest(contest)
                .stream()
                .map(submissionMapper::toResponse)
                .toList();
    }
    private User getCurrentCandidate() {

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    private Contest getContest(Long contestId) {

        return contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));
    }

    private Problem getProblem(Long problemId) {

        return problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));
    }

    private void validateContest(Contest contest) {

        if (contest.getStatus() != ContestStatus.PUBLISHED) {
            throw new IllegalOperationException(
                    "Contest is not published."
            );
        }
    }

    private void validateContestProblem(
            Contest contest,
            Problem problem) {

        if (!contestProblemRepository.existsByContestAndProblem(
                contest,
                problem)) {

            throw new IllegalOperationException(
                    "Problem does not belong to this contest."
            );
        }
    }
    private void validateRegistration(
            Contest contest,
            User candidate) {

        if (!registrationRepository.existsByContestAndUser(
                contest,
                candidate)) {

            throw new IllegalOperationException(
                    "You are not registered for this contest."
            );
        }
    }

    private void validateContestWindow(
            Contest contest) {

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(contest.getStartTime())) {
            throw new IllegalOperationException(
                    "Contest has not started yet."
            );
        }

        if (now.isAfter(contest.getEndTime())) {
            throw new IllegalOperationException(
                    "Contest has already ended."
            );
        }
    }

    private int calculateAttemptNumber(
            User candidate,
            Contest contest,
            Problem problem) {

        return (int) submissionRepository
                .countByCandidateAndContestAndProblem(
                        candidate,
                        contest,
                        problem
                ) + 1;
    }
}