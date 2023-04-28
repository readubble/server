package com.capstone.server.Repository;

import com.capstone.server.DTO.TbReadDTO;
import com.capstone.server.Domain.TbRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TbReadRepository extends JpaRepository<com.capstone.server.Domain.TbRead, String> {

    @Query("SELECT r FROM TbRead r left outer join Article a on r.tbArticleId = a.id where r.tbUserId= :id and a.difficulty= :difficulty")
    Page<TbRead> findAllByTbUserIdLEFTJOINArticle(String id, String difficulty, Pageable pageable);
}
