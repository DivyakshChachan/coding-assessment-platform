package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.request.CreateProblemRequest;
import com.divyaksh.cap.dto.request.UpdateProblemRequest;
import com.divyaksh.cap.dto.response.ProblemResponse;
import com.divyaksh.cap.entity.Problem;
import com.divyaksh.cap.entity.User;
import com.divyaksh.cap.exception.DuplicateResourceException;
import com.divyaksh.cap.exception.ResourceNotFoundException;
import com.divyaksh.cap.mapper.ProblemMapper;
import com.divyaksh.cap.repository.ProblemRepository;
import com.divyaksh.cap.repository.UserRepository;
import com.divyaksh.cap.security.CustomUserDetails;
import com.divyaksh.cap.service.ProblemService;
import com.divyaksh.cap.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final ProblemMapper problemMapper;

    @Override
    public ProblemResponse createProblem(CreateProblemRequest request) {

        if (problemRepository.existsByTitle(request.title())) {
            throw new DuplicateResourceException("Problem title already exists.");
        }

        String slug = generateSlug(request.title());

        if (problemRepository.existsBySlug(slug)) {
            throw new DuplicateResourceException("Problem slug already exists.");
        }

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        User creator = userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        Problem problem = problemMapper.toEntity(request);

        problem.setSlug(slug);
        problem.setCreatedBy(creator);
        problem.setPublished(false);

        Problem savedProblem = problemRepository.save(problem);

        return problemMapper.toResponse(savedProblem);
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemResponse getProblem(Long id) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        return problemMapper.toResponse(problem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProblemResponse> getAllProblems() {

        return problemRepository.findAll()
                .stream()
                .map(problemMapper::toResponse)
                .toList();
    }

    @Override
    public ProblemResponse updateProblem(Long id,
                                         UpdateProblemRequest request) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        if (!problem.getTitle().equals(request.title())
                && problemRepository.existsByTitle(request.title())) {

            throw new DuplicateResourceException(
                    "Problem title already exists.");
        }

        String slug = generateSlug(request.title());

        if (!problem.getSlug().equals(slug)
                && problemRepository.existsBySlug(slug)) {

            throw new DuplicateResourceException(
                    "Problem slug already exists.");
        }

        problemMapper.updateProblemFromRequest(request, problem);

        problem.setSlug(slug);

        Problem updatedProblem = problemRepository.save(problem);

        return problemMapper.toResponse(updatedProblem);
    }

    @Override
    public void deleteProblem(Long id) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        problemRepository.delete(problem);
    }

    @Override
    public void publishProblem(Long id) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        problem.setPublished(true);
    }

    @Override
    public void unpublishProblem(Long id) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Problem not found."));

        problem.setPublished(false);
    }

    private String generateSlug(String title) {

        return title.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}