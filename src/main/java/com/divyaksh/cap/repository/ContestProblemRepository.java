package com.divyaksh.cap.repository;

import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.ContestProblem;
import com.divyaksh.cap.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestProblemRepository
        extends JpaRepository<ContestProblem, Long> {

    List<ContestProblem> findAllByContest(Contest contest);

    Optional<ContestProblem> findByContestAndProblem(
            Contest contest,
            Problem problem
    );

    boolean existsByContestAndProblem(
            Contest contest,
            Problem problem
    );
}