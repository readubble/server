package com.capstone.server.Repository;

import com.capstone.server.Domain.WordQuizItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordQuizItemRepository extends JpaRepository<WordQuizItem, String> {
    List<WordQuizItem> findAllByTbWordQuizQuizNo(int tbWordQuizQuizNo);
}
