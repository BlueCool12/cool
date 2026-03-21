package com.pyomin.cool.controller;

import com.pyomin.cool.dto.request.FeedbackRequest;
import com.pyomin.cool.dto.response.FeedbackCategoryResponse;
import com.pyomin.cool.service.FeedbackService;
import com.pyomin.cool.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/categories")
    public List<FeedbackCategoryResponse> getActiveCategories() {
        return feedbackService.getActiveCategories().stream()
                .map(FeedbackCategoryResponse::from)
                .toList();
    }

    @PostMapping
    public void submitFeedback(
            @Valid @RequestBody FeedbackRequest request,
            HttpServletRequest servletRequest) {

        String clientIp = RequestUtil.getClientIp(servletRequest);
        feedbackService.submitFeedback(request, clientIp);
    }
}
