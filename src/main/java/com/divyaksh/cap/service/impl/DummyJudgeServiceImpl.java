package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.entity.Submission;
import com.divyaksh.cap.entity.enums.SubmissionStatus;
import com.divyaksh.cap.repository.SubmissionRepository;
import com.divyaksh.cap.service.JudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class DummyJudgeServiceImpl implements JudgeService {

    private final SubmissionRepository submissionRepository;

    @Override
    @CacheEvict(cacheNames = "leaderboards")
    @Transactional
    public void judge(Submission submission) {


        submission.setStatus(SubmissionStatus.RUNNING);

        submissionRepository.save(submission);

        // Dummy evaluation
        Random random = new Random();

        int value = random.nextInt(100);

        if (value < 60) {
            submission.setStatus(SubmissionStatus.ACCEPTED);
            submission.setScore(100);
        } else if (value < 80) {
            submission.setStatus(SubmissionStatus.WRONG_ANSWER);
            submission.setScore(0);
        } else if (value < 90) {
            submission.setStatus(SubmissionStatus.TIME_LIMIT_EXCEEDED);
            submission.setScore(0);
        } else {
            submission.setStatus(SubmissionStatus.RUNTIME_ERROR);
            submission.setScore(0);
        }

        submissionRepository.save(submission);
    }
}