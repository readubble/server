package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveArticleRepository extends JpaRepository<SaveArticle, String> {
}
