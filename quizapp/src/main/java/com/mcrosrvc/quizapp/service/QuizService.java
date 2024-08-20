package com.mcrosrvc.quizapp.service;

import com.mcrosrvc.quizapp.dao.QuestionDao;
import com.mcrosrvc.quizapp.dao.QuizDao;
import com.mcrosrvc.quizapp.entities.ClientResponse;
import com.mcrosrvc.quizapp.entities.Question;
import com.mcrosrvc.quizapp.entities.QuestionWrapper;
import com.mcrosrvc.quizapp.entities.Quiz;
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
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questionList = questionDao.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);
        quizDao.save(quiz);

        List<Quiz> quizList = quizDao.findAll();
        quizList.forEach(System.out::println);
        return new ResponseEntity<>("Successfully created quiz for category "+category, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {

       Optional<Quiz> quiz = quizDao.findById(id);

       List<Question>  questionsFromDB = quiz.get().getQuestions();
       List<QuestionWrapper> questionWrapperList = new ArrayList<>();

       for(Question q:questionsFromDB){
           QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
           questionWrapperList.add(questionWrapper);
       }

       return new ResponseEntity<>(questionWrapperList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getCalculateScore(Integer id, List<ClientResponse> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionList = quiz.get().getQuestions();
        int i=0,ans=0;

        for(ClientResponse response:responses){
            if(response.getResponse().equals(questionList.get(i).getRightAnswer())){
                ans++;
            }
            i++;
        }
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
}
