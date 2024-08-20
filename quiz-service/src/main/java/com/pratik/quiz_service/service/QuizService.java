package com.pratik.quiz_service.service;


import com.pratik.quiz_service.dao.QuizDao;
import com.pratik.quiz_service.entities.ClientResponse;
import com.pratik.quiz_service.entities.QuestionWrapper;
import com.pratik.quiz_service.entities.Quiz;
import com.pratik.quiz_service.feign.QuizInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questionIdList = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIdList);
        quizDao.save(quiz);

        List<Quiz> quizList = quizDao.findAll();
        quizList.forEach(System.out::println);
        return new ResponseEntity<>("Successfully created quiz for category "+category, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {

       Quiz quiz = quizDao.findById(id).get();
       List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionWrapperList = quizInterface.getQuestionsById(questionIds);

       return questionWrapperList;
    }

    public ResponseEntity<Integer> getCalculateScore(List<ClientResponse> responses) {
        ResponseEntity<Integer> userscore = quizInterface.getScore(responses);

      return userscore;
    }
}
