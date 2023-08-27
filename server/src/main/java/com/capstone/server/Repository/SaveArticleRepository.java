package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveArticle;
import com.capstone.server.Domain.SaveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveArticleRepository extends JpaRepository<SaveArticle, String> {

    Optional<SaveArticle> findByTbUserIdAndAndTbArticleId(String tbUserId, int tbArticleId);

    List<SaveArticle> findAllByTbUserId(String tbUserId);
    List<SaveArticle> findAllByTbUserIdAndAndCgNo(String tbUserId, int cgNo);

    Boolean existsByTbUserIdAndTbArticleId(String tbUserId, int tbArticleId);
}
