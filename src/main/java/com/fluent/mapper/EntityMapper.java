package com.fluent.mapper;

import com.fluent.dto.FavoriteDTO;
import com.fluent.dto.HistoryDTO;
import com.fluent.dto.MemberDTO;
import com.fluent.dto.QuizDTO;
import com.fluent.entity.Favorite;
import com.fluent.entity.History;
import com.fluent.entity.Member;
import com.fluent.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    // Member mappings
    @Mapping(target = "favorites", ignore = true)
    MemberDTO memberToMemberDTO(Member member);
    Member memberDTOToMember(MemberDTO memberDTO);

    // Favorite mappings
    @Mapping(target = "memberId", source = "member.email")
    FavoriteDTO favoriteToFavoriteDTO(Favorite favorite);
    @Mapping(target = "member.email", source = "memberId")
    Favorite favoriteDTOToFavorite(FavoriteDTO favoriteDTO);

    // History mappings
    @Mapping(target = "memberId", source = "member.email")
    @Mapping(target = "quizId", source = "quiz.quizId")
    HistoryDTO historyToHistoryDTO(History history);
    @Mapping(target = "member", source = "memberId", qualifiedByName = "toMember")
    @Mapping(target = "quiz", source = "quizId", qualifiedByName = "toQuiz")
    History historyDTOToHistory(HistoryDTO historyDTO);

    // Quiz mappings
    QuizDTO quizToQuizDTO(Quiz quiz);
    @Mapping(target = "quizId", source = "quizId")
    Quiz quizDTOToQuiz(QuizDTO quizDTO);

    @Named("toMember")
    default Member toMember(String email) {
        if (email == null) {
            return null;
        }
        Member member = new Member();
        member.setEmail(email);
        return member;
    }

    @Named("toQuiz")
    default Quiz toQuiz(Long quizId) {
        if (quizId == null) {
            return null;
        }
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        return quiz;
    }
}