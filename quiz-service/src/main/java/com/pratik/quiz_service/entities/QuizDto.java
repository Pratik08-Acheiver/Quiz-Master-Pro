package com.pratik.quiz_service.entities;

import lombok.Data;

@Data
public class QuizDto {
    String categoryName;
    Integer numberOfQuestions;
    String title;
}
