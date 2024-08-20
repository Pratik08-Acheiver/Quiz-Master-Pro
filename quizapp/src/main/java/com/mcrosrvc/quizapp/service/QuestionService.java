package com.mcrosrvc.quizapp.service;

import com.mcrosrvc.quizapp.dao.QuestionDao;
import com.mcrosrvc.quizapp.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
