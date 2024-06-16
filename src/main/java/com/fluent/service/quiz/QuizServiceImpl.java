package com.fluent.service.quiz;

import com.fluent.config.PythonServerProperties;
import com.fluent.config.S3Properties;
import com.fluent.dto.FavoriteDTO;
import com.fluent.dto.QuizDTO;
import com.fluent.entity.Quiz;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.QuizRepository;
//import com.fluent.service.S3ServiceImpl;
import com.fluent.service.favorite.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final EntityMapper mapper;
    private final FavoriteService favoriteService;
//    private final S3ServiceImpl s3Service;
    private final PythonServerProperties pythonProps;
    private final S3Properties s3Properties;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, EntityMapper mapper, FavoriteService favoriteService, S3Properties s3Properties, PythonServerProperties pythonProps) {
        this.quizRepository = quizRepository;
        this.mapper = mapper;
        this.favoriteService = favoriteService;
        this.pythonProps = pythonProps;
        this.s3Properties = s3Properties;
    }

//    @Autowired
//    public QuizServiceImpl(QuizRepository quizRepository, EntityMapper mapper, FavoriteService favoriteService, S3ServiceImpl s3Service, S3Properties s3Properties, PythonServerProperties pythonProps) {
//        this.quizRepository = quizRepository;
//        this.mapper = mapper;
//        this.favoriteService = favoriteService;
//        this.s3Service = s3Service;
//        this.pythonProps = pythonProps;
//        this.s3Properties = s3Properties;
//    }

    @Override
    public QuizDTO findRandomQuiz(String email) {
        List<FavoriteDTO> favorites = favoriteService.findFavoritesByEmail(email);
        if (favorites.isEmpty()) {
            return null;
        }
        String favoriteCategory = favorites.get(new Random().nextInt(favorites.size())).getFavorite();
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

    @Override
    public void requestAndStoreQuizzes(String category) {
//        RestTemplate restTemplate = new RestTemplate();
//        String questionUrl = pythonProps.getGenerateUrl() + "?category=" + category;
//        String[] questions = restTemplate.getForObject(questionUrl, String[].class);
//        if(questions == null){
//            System.out.println("문제요청 실패");
//            return;
//        }
//
//        for (String question : questions) {
//            String ttsUrl = pythonProps.getTtsUrl() + "?text=" + question;
//            byte[] audioBytes = restTemplate.getForObject(ttsUrl, byte[].class);
//            if(audioBytes == null){
//                System.out.println("tts요청 실패");
//                return;
//            }
//            try {
//                String fileName = System.currentTimeMillis() + ".mp3";
//                String s3Url = s3Service.uploadAudio(audioBytes, fileName);
//
//                QuizDTO quizDTO = new QuizDTO();
//                quizDTO.setQuestion(question);
//                quizDTO.setFavorite(category);
//                quizDTO.setRefAudio(s3Url);
//                quizDTO.setTier(1L); // Simplify the logic for example
//                quizDTO.setAnswerVec(1L);
//
//                saveQuiz(quizDTO);
//            } catch (Exception e) {
//                e.printStackTrace();  // Handle exceptions appropriately
//            }
//        }
    }
}
