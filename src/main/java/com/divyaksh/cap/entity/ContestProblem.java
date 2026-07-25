package com.divyaksh.cap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "contest_problems",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"contest_id", "problem_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContestProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
}