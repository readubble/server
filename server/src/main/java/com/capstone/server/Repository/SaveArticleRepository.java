package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveArticle;
import com.capstone.server.Domain.SaveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveArticleRepository extends JpaRepository<SaveArticle, String> {

    SaveArticle findByTbUserIdAndAndTbArticleId(String tbUserId, int tbArticleId);

    // tbUserId 필드를 이용하여 해당 사용자가 저장한 모든 단어를 조회하는 메서드입니다.
    // 이 메서드는 tbUserId 매개변수를 통해 조회할 SaveWord 엔티티의 tbUserId 값을 전달받습니다.
    // 이 메서드는 List<SaveWord>를 반환합니다.
    List<SaveArticle> findAllByTbUserId(String tbUserId);

    Boolean existsByUserIdAndTbArticleId(String tbUserId, int tbArticleId);
}
