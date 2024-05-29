package com.fluent;

import com.fluent.dto.FavoriteDTO;
import com.fluent.entity.Favorite;
import com.fluent.entity.Member;
import com.fluent.repository.FavoriteRepository;
import com.fluent.repository.MemberRepository;
import com.fluent.service.favorite.FavoriteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@Transactional
public class FavoriteServiceTest {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

//    @BeforeEach
//    public void setup() {
//        // 미리 멤버 데이터를 데이터베이스에 삽입합니다.
//        memberRepository.save(new Member("member1@example.com", "JohnDoe", "https://example.com/path/to/profile1.jpg"));
//        memberRepository.save(new Member("member2@example.com", "JaneDoe", "https://example.com/path/to/profile2.jpg"));
//        memberRepository.save(new Member("member3@example.com", "AliceSmith", "https://example.com/path/to/profile3.jpg"));
//        memberRepository.save(new Member("member4@example.com", "BobJohnson", "https://example.com/path/to/profile4.jpg"));
//        memberRepository.save(new Member("member5@example.com", "CarolWhite", "https://example.com/path/to/profile5.jpg"));
//
//        // 미리 즐겨찾기 데이터를 데이터베이스에 삽입합니다.
//        favoriteRepository.save(new Favorite(null, "여행", memberRepository.findById("member1@example.com").get()));
//        favoriteRepository.save(new Favorite(null, "운동", memberRepository.findById("member1@example.com").get()));
//        favoriteRepository.save(new Favorite(null, "연애", memberRepository.findById("member3@example.com").get()));
//        favoriteRepository.save(new Favorite(null, "오락", memberRepository.findById("member4@example.com").get()));
//        favoriteRepository.save(new Favorite(null, "운동", memberRepository.findById("member5@example.com").get()));
//    }

    @Test
    public void testFindFavoritesByEmail() {
        // 이메일로 즐겨찾기 검색 테스트
        List<FavoriteDTO> favorites = favoriteService.findFavoritesByEmail("member1@example.com");
        assertThat(favorites).hasSize(2);
        assertThat(favorites.get(0).getFavorite()).isEqualTo("여행");
        assertThat(favorites.get(1).getFavorite()).isEqualTo("운동");
    }

    @Test
    public void testSaveAndDeleteFavorite() {
        // 즐겨찾기 저장 테스트
        FavoriteDTO newFavorite = new FavoriteDTO(null, "음악", "member2@example.com");
        FavoriteDTO savedFavorite = favoriteService.saveFavorite(newFavorite);
        assertThat(savedFavorite).isNotNull();
        assertThat(savedFavorite.getFavorite()).isEqualTo("음악");

        // 즐겨찾기 삭제 테스트
        favoriteService.deleteFavorite(savedFavorite.getFavorId());
        List<FavoriteDTO> favoritesAfterDeletion = favoriteService.findFavoritesByEmail("member2@example.com");
        assertThat(favoritesAfterDeletion).isEmpty();
    }
}