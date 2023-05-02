package com.capstone.server.Repository;

import com.capstone.server.Domain.WordQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordQuizAnswerRepository extends JpaRepository<WordQuizAnswer, String> {
    Optional<WordQuizAnswer> findByTbUserIDAndTbQuizQuizNo(String tbUserId, int tbQuizQuizNo);

}
