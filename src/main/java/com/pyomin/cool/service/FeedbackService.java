package com.pyomin.cool.service;

import com.pyomin.cool.domain.Feedback;
import com.pyomin.cool.domain.FeedbackCategory;
import com.pyomin.cool.domain.FeedbackCategoryStatus;
import com.pyomin.cool.dto.FeedbackCategoryDto;
import com.pyomin.cool.dto.request.FeedbackRequest;
import com.pyomin.cool.exception.ResourceNotFoundException;
import com.pyomin.cool.repository.FeedbackCategoryRepository;
import com.pyomin.cool.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackCategoryRepository feedbackCategoryRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public List<FeedbackCategoryDto> getActiveCategories() {
        return feedbackCategoryRepository.findAllByStatusOrderBySortOrderAsc(FeedbackCategoryStatus.ACTIVE)
                .stream()
                .map(FeedbackCategoryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void submitFeedback(FeedbackRequest request, String clientIp) {
        FeedbackCategory category = feedbackCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("유효하지 않은 카테고리입니다."));

        Feedback feedback = Feedback.builder()
                .category(category)
                .content(request.getContent())
                .clientIp(clientIp)
                .pageUrl(request.getPageUrl())
                .build();

        feedbackRepository.save(feedback);
    }
}
