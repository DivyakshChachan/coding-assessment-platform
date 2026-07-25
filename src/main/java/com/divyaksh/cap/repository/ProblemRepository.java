package com.divyaksh.cap.repository;

import com.divyaksh.cap.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByTitle(String title);

    boolean existsById(Long id);
}