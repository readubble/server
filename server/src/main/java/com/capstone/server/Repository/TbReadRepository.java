package com.capstone.server.Repository;

import com.capstone.server.DTO.TbReadDTO;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Interface.TbReadInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TbReadRepository extends JpaRepository<com.capstone.server.Domain.TbRead, String> {

    TbRead findByTbUserIdAndTbArticleId(String tbUserId, int tbArticleId);
    @Query("SELECT r.tbArticleId As tbArticleId, a.atcTitle AS atcTitle, a.genre AS genre FROM TbRead r left outer join Article a on r.tbArticleId = a.id where r.tbUserId= :id and a.difficulty= :difficulty")
    Page<TbReadInterface> findAllByTbUserIdLEFTJOINArticle(String id, String difficulty, Pageable pageable);

    @Query("SELECT count(q) FROM TbRead q left outer join Article a on q.tbArticleId = a.id where q.solveFl= :correct and a.difficulty = :difficulty and q.tbUserId = :tbUserId")
    int countAllBySolveFlIsAndTbUserIdJoinArticle(String correct, String tbUserId, String difficulty);

}
