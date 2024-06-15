package com.fluent.service.quiz;

import com.fluent.dto.FavoriteDTO;
import com.fluent.dto.QuizDTO;
import com.fluent.entity.Quiz;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.QuizRepository;
import com.fluent.service.favorite.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final EntityMapper mapper;
    private final FavoriteService favoriteService;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, EntityMapper mapper, FavoriteService favoriteService) {
        this.quizRepository = quizRepository;
        this.mapper = mapper;
        this.favoriteService = favoriteService;
    }

    @Override
    public QuizDTO findRandomQuiz(String email) {
        // 사용자의 선호 카테고리 리스트를 가져옵니다.
        List<FavoriteDTO> favorites = favoriteService.findFavoritesByEmail(email);
        if (favorites.isEmpty()) {
            return null;  // 선호도 정보가 없는 경우, null을 반환합니다.
        }

        // 사용자가 선호하는 카테고리 중 하나를 무작위로 선택합니다.
        String favoriteCategory = favorites.get(new Random().nextInt(favorites.size())).getFavorite();

        // 선호 카테고리와 일치하며 히스토리에 없는 퀴즈를 찾습니다.
        List<Quiz> availableQuizzes = quizRepository.findAvailableQuizzes(email, favoriteCategory);
        if (!availableQuizzes.isEmpty()) {
            Quiz selectedQuiz = availableQuizzes.get(new Random().nextInt(availableQuizzes.size()));
            return EntityMapper.INSTANCE.quizToQuizDTO(selectedQuiz);
        }
        return null;
    }

    @Override
    public QuizDTO saveQuiz(QuizDTO quizDTO) {
        Quiz quiz = mapper.quizDTOToQuiz(quizDTO);
        Quiz savedQuiz = quizRepository.save(quiz);
        return mapper.quizToQuizDTO(savedQuiz);
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public QuizDTO findQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                             .map(EntityMapper.INSTANCE::quizToQuizDTO)
                             .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }
}