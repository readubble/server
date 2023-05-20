package com.capstone.server.Repository;

import com.capstone.server.Domain.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, String> {

    int countAllByCorrectFlIsAndTbUserId(String correctFl, String TbUserId);

    @Query("SELECT count(q) FROM QuizAnswer q left outer join Article a on q.tbArticleId = a.id where q.correctFl= :correct and a.difficulty = :difficulty and q.tbUserId = :tbUserId")
    int countAllByCorrectFlIsAndTbUserIdJoinArticle(String correct, String tbUserId, String difficulty);
}
