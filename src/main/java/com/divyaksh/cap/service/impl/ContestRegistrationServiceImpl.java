package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.response.ContestRegistrationResponse;
import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.ContestRegistration;
import com.divyaksh.cap.entity.User;
import com.divyaksh.cap.exception.DuplicateResourceException;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.mapper.ContestRegistrationMapper;
import com.divyaksh.cap.repository.ContestRegistrationRepository;
import com.divyaksh.cap.repository.ContestRepository;
import com.divyaksh.cap.repository.UserRepository;
import com.divyaksh.cap.security.CustomUserDetails;
import com.divyaksh.cap.service.ContestRegistrationService;
import com.divyaksh.cap.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import com.divyaksh.cap.entity.enums.ContestStatus;
import com.divyaksh.cap.exception.IllegalOperationException;

@Service
@RequiredArgsConstructor
@Transactional
public class ContestRegistrationServiceImpl
        implements ContestRegistrationService {

    private final ContestRepository contestRepository;
    private final ContestRegistrationRepository registrationRepository;
    private final ContestRegistrationMapper registrationMapper;
    private final UserRepository userRepository;

    @Override
    public ContestRegistrationResponse register(Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        if (contest.getStatus() != ContestStatus.PUBLISHED) {
            throw new IllegalOperationException(
                    "Contest is not open for registration."
            );
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(contest.getRegistrationStart())
                || now.isAfter(contest.getRegistrationEnd())) {
            throw new IllegalOperationException(
                    "Registration window is closed."
            );
        }
        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        User user = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        if (registrationRepository.existsByContestAndUser(contest, user)) {
            throw new DuplicateResourceException(
                    "Already registered.");
        }

        ContestRegistration registration = ContestRegistration.builder()
                .contest(contest)
                .user(user)
                .build();

        return registrationMapper.toResponse(
                registrationRepository.save(registration)
        );
    }

    @Override
    public void unregister(Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        User user = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        ContestRegistration registration =
                registrationRepository.findByContestAndUser(contest, user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Registration not found."));

        registrationRepository.delete(registration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContestRegistrationResponse> getMyRegistrations() {

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        User user = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        return registrationRepository.findAllByUser(user)
                .stream()
                .map(registrationMapper::toResponse)
                .toList();
    }
}