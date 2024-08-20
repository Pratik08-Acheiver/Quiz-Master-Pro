package com.pratik.question_service.service;


import com.pratik.question_service.dao.QuestionDao;
import com.pratik.question_service.entities.ClientResponse;
import com.pratik.question_service.entities.Question;
import com.pratik.question_service.entities.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {

        try{
//           List<Question> questions =   questionDao.findAll();
            questionDao.findAll().forEach(System.out::println);
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK) ;
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST) ;
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try{
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST) ;
        }

    public ResponseEntity<String>  addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
           }catch(Exception e){
            e.printStackTrace();
           }
        return new ResponseEntity<>("Unable to Create Question data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer noOfQues) {
      List<Integer> listOfQuestions =   questionDao.findRandomQuestionsByCategory(categoryName,noOfQues);
      return new ResponseEntity<>(listOfQuestions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds) {
        List<QuestionWrapper> questionWrapperList = new ArrayList<>();
        List<Question> questionList =new ArrayList<>();

        for(Integer i:questionsIds){
            questionList.add(questionDao.findById(i).get());
        }

        for(Question question:questionList){
            QuestionWrapper questionWrapper = new QuestionWrapper();

            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());

            questionWrapperList.add(questionWrapper);
        }

        return new ResponseEntity<>(questionWrapperList,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<ClientResponse> responses) {

        int ans=0;

        for(ClientResponse response:responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                ans++;
            }

        }
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
}
