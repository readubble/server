package com.capstone.server.Repository;

import com.capstone.server.Domain.WordQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordQuizRepository extends JpaRepository<WordQuiz, String> {
    List<WordQuiz> findTop3By();
}
