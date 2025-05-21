package com.example.feedback_app.service;

import com.example.feedback_app.model.Feedback;
import com.example.feedback_app.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
    
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }
    
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }
    
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
    
    public List<Feedback> searchFeedbacks(String name, String className, String subject) {
        return feedbackRepository.findByFilters(name, className, subject);
    }
}