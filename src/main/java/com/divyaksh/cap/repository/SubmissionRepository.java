package com.divyaksh.cap.repository;

import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.Problem;
import com.divyaksh.cap.entity.Submission;
import com.divyaksh.cap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

    List<Submission> findAllByCandidate(User candidate);

    List<Submission> findAllByContest(Contest contest);

    List<Submission> findAllByProblem(Problem problem);

    List<Submission> findAllByCandidateAndProblem(
            User candidate,
            Problem problem
    );
    long countByCandidateAndContestAndProblem(
            User candidate,
            Contest contest,
            Problem problem
    );
    List<Submission> findAllByContestAndCandidate(
            Contest contest,
            User candidate
    );
}