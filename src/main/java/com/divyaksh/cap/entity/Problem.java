package com.divyaksh.cap.entity;

import com.divyaksh.cap.constant.Difficulty;
import com.divyaksh.cap.constant.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 200)
    private String slug;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String inputFormat;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String outputFormat;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String constraints;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "problem_tags",
            joinColumns = @JoinColumn(name = "problem_id")
    )
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable = false)
    private Integer timeLimitMs;

    @Column(nullable = false)
    private Integer memoryLimitMb;

    @Column(nullable = false)
    @Builder.Default
    private boolean published = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(
            mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<TestCase> testCases = new ArrayList<>();

    @Version
    private Long version;

    @OneToMany(
            mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ContestProblem> contestProblems = new ArrayList<>();

    @OneToMany(
            mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Submission> submissions = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}