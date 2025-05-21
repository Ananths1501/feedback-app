package com.example.feedback_app.controller;

import com.example.feedback_app.model.Feedback;
import com.example.feedback_app.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    @GetMapping("/")
    public String showFeedbackList(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        model.addAttribute("feedback", new Feedback());
        return "index";
    }
    
    @PostMapping("/feedback")
    public String addFeedback(@Valid @ModelAttribute("feedback") Feedback feedback, 
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
            return "index";
        }
        
        feedbackService.saveFeedback(feedback);
        return "redirect:/";
    }
    
    @GetMapping("/feedback/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Feedback feedback = feedbackService.getFeedbackById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid feedback Id:" + id));
        
        model.addAttribute("feedback", feedback);
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "index";
    }
    
    @PostMapping("/feedback/update/{id}")
    public String updateFeedback(@PathVariable("id") Long id, 
                               @Valid @ModelAttribute("feedback") Feedback feedback,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            feedback.setId(id);
            model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
            return "index";
        }
        
        feedbackService.saveFeedback(feedback);
        return "redirect:/";
    }
    
    @GetMapping("/feedback/delete/{id}")
    public String deleteFeedback(@PathVariable("id") Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/";
    }
    
    @GetMapping("/feedback/search")
    public String searchFeedbacks(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String className,
                                @RequestParam(required = false) String subject,
                                Model model) {
        model.addAttribute("feedbacks", feedbackService.searchFeedbacks(name, className, subject));
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("searchName", name);
        model.addAttribute("searchClassName", className);
        model.addAttribute("searchSubject", subject);
        return "index";
    }
}