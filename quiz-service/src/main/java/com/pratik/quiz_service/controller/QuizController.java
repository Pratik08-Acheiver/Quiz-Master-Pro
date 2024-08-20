package com.pratik.quiz_service.controller;

import com.pratik.quiz_service.entities.ClientResponse;
import com.pratik.quiz_service.entities.QuestionWrapper;
import com.pratik.quiz_service.entities.QuizDto;
import com.pratik.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("/createquiz")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumberOfQuestions(),quizDto.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer id){
       return quizService.getQuizById(id);
    }

    @PostMapping("/submit")
    public ResponseEntity<Integer> getScore(@RequestBody List<ClientResponse> responses){
        return quizService.getCalculateScore(responses);
    }

}
