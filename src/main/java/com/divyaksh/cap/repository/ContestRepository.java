package com.divyaksh.cap.repository;

import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.enums.ContestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findAllByStatus(ContestStatus status);

}