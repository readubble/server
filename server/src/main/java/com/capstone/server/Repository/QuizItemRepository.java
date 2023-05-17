package com.capstone.server.Repository;

import com.capstone.server.Domain.QuizItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizItemRepository extends JpaRepository<QuizItem, String> {
    List<QuizItem> findAllByTbArticleIdAndTbQuizNo(int tbArticleId, int tbQuizNo);
    List<QuizItem> findAllByTbQuizNo(int tbQuizNo);
}
