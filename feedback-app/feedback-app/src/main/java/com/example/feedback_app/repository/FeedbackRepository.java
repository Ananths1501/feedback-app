package com.example.feedback_app.repository;

import com.example.feedback_app.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByNameContainingIgnoreCase(String name);
    
    List<Feedback> findByClassNameContainingIgnoreCase(String className);
    
    List<Feedback> findBySubjectContainingIgnoreCase(String subject);
    
    @Query("SELECT f FROM Feedback f WHERE " +
           "(:name IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:className IS NULL OR LOWER(f.className) LIKE LOWER(CONCAT('%', :className, '%'))) AND " +
           "(:subject IS NULL OR LOWER(f.subject) LIKE LOWER(CONCAT('%', :subject, '%')))")
    List<Feedback> findByFilters(@Param("name") String name, 
                                @Param("className") String className, 
                                @Param("subject") String subject);
}