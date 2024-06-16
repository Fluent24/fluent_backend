//package com.fluent.scheduler;
//
//import com.fluent.config.PythonServerProperties;
//import com.fluent.service.quiz.QuizService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableScheduling
//public class QuizScheduler {
//
//    private final QuizService quizService;
//
//    @Autowired
//    public QuizScheduler(QuizService quizService, PythonServerProperties pythonServerProperties) {
//        this.quizService = quizService;
//    }
//
//    @Scheduled(fixedDelay = 3600000) // 매시간마다 실행
//    public void requestAndStoreQuizzes() {
//        String[] categories = new String[]{"history", "game", "love", "travel"};
//        for(String category : categories){
//            quizService.requestAndStoreQuizzes(category);
//        }
//
//    }
//}
