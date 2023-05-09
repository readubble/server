package com.capstone.server.Repository;

import com.capstone.server.Domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    List<Quiz> findAllByTbArticleId(int tbArticleId);
}
