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
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    // Member mappings
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
    @Mapping(target = "member.email", source = "memberId")
    @Mapping(target = "quiz.quizId", source = "quizId")
    History historyDTOToHistory(HistoryDTO historyDTO);

    // Quiz mappings
    QuizDTO quizToQuizDTO(Quiz quiz);
    @Mapping(target = "quizId", source = "quizId")
    Quiz quizDTOToQuiz(QuizDTO quizDTO);
}