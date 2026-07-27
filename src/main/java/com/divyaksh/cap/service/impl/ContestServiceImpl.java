package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.request.CreateContestRequest;
import com.divyaksh.cap.dto.request.UpdateContestRequest;
import com.divyaksh.cap.dto.response.ContestResponse;
import com.divyaksh.cap.entity.Contest;
import com.divyaksh.cap.entity.User;
import com.divyaksh.cap.entity.enums.ContestStatus;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.mapper.ContestMapper;
import com.divyaksh.cap.repository.ContestRepository;
import com.divyaksh.cap.repository.UserRepository;
import com.divyaksh.cap.security.CustomUserDetails;
import com.divyaksh.cap.service.ContestService;
import com.divyaksh.cap.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;
    private final ContestMapper contestMapper;
    private final UserRepository userRepository;

    @Override
    public ContestResponse createContest(CreateContestRequest request) {

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        User creator = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        Contest contest = contestMapper.toEntity(request);

        contest.setCreatedBy(creator);

        Contest savedContest = contestRepository.save(contest);

        return contestMapper.toResponse(savedContest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "contests")
    public ContestResponse getContest(Long contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        return contestMapper.toResponse(contest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContestResponse> getPublishedContests() {
        return contestRepository.findAllByStatus(ContestStatus.PUBLISHED)
                .stream()
                .map(contestMapper::toResponse)
                .toList();
    }

    @Override
    @CachePut(cacheNames = "contests", key = "#contestId")
    @Transactional
    public ContestResponse updateContest(Long contestId,
                                         UpdateContestRequest request) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        contestMapper.updateContestFromRequest(request, contest);

        Contest updatedContest = contestRepository.save(contest);

        return contestMapper.toResponse(updatedContest);
    }

    @Override
    public ContestResponse publishContest(Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        contest.setStatus(ContestStatus.PUBLISHED);

        return contestMapper.toResponse(
                contestRepository.save(contest)
        );
    }

    @Override
    public ContestResponse cancelContest(Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        contest.setStatus(ContestStatus.CANCELLED);

        return contestMapper.toResponse(
                contestRepository.save(contest)
        );
    }

    @Override
    @CacheEvict(cacheNames = "contests", key = "#contestId")
    public void deleteContest(Long contestId) {

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contest not found."));

        contestRepository.delete(contest);
    }
}