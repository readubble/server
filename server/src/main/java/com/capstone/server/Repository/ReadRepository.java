package com.capstone.server.Repository;

import com.capstone.server.DTO.ReadDTO;
import com.capstone.server.Domain.Read;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadRepository extends JpaRepository<Read, String> {

    @Query("SELECT r FROM Read r left outer join Article a on r.tbArticleId = a.id where r.tbUserId= :id and a.difficulty= :d")
    Page<ReadDTO> findAllBytbUserIdLEFTJOINArticle(String id, String difficulty, Pageable pageable);
}
