package com.fluent.repository;

import com.fluent.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q WHERE q.favorite = :favorite AND q.quizId NOT IN " +
            "(SELECT h.quiz.quizId FROM History h WHERE h.member.email = :email)")
    List<Quiz> findAvailableQuizzes(@Param("email") String email, @Param("favorite") String favorite);
}