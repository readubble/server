package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveWordRepository extends JpaRepository<SaveWord, String> {

    Optional<SaveWord> findByTbUserIdAndTargetCode(String tbUserId, int targetCode);
    Optional<SaveWord> findByTbUserIdAndTargetCodeAndWordNmAndWordMean(String tbUserId, int targetCode, String wordNm, String wordMean);

    List<SaveWord> findAllByTbUserId(String tbUserId);

    Boolean existsByTbUserIdAndTargetCode(String tbUserId, int targetCode);
}


