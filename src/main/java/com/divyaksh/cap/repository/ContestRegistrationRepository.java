package com.divyaksh.cap.repository;

import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.ContestRegistration;
import com.divyaksh.cap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestRegistrationRepository
        extends JpaRepository<ContestRegistration, Long> {

    boolean existsByContestAndUser(
            Contest contest,
            User user
    );

    Optional<ContestRegistration> findByContestAndUser(
            Contest contest,
            User user
    );

    List<ContestRegistration> findAllByContest(
            Contest contest
    );

    List<ContestRegistration> findAllByUser(
            User user
    );
}