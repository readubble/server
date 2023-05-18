package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveWordRepository extends JpaRepository<SaveWord, String> {

    // findByTbUserIdAndWordNm은 tbUserId와 wordNm 필드를 이용해 저장된 단어를 조회하는 메서드?
    Optional<SaveWord> findByTbUserIdAndTargetCode(String tbUserId, int targetCode);

    // tbUserId 필드를 이용하여 해당 사용자가 저장한 모든 단어를 조회하는 메서드입니다.
    // 이 메서드는 tbUserId 매개변수를 통해 조회할 SaveWord 엔티티의 tbUserId 값을 전달받습니다.
    // 이 메서드는 List<SaveWord>를 반환합니다.
    List<SaveWord> findAllByTbUserId(String tbUserId);

    Boolean existsByTbUserIdAndTargetCode(String tbUserId, int targetCode);
}


