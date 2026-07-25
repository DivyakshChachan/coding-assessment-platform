package com.divyaksh.cap.repository;

import com.divyaksh.cap.entity.Problem;
import com.divyaksh.cap.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    List<TestCase> findByProblem(Problem problem);

    List<TestCase> findByProblemAndSampleTrue(Problem problem);

}