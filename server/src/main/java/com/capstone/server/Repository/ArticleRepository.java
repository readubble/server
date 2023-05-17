package com.capstone.server.Repository;

import com.capstone.server.Domain.Article;
import com.capstone.server.Interface.ArticleInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    Article findById(int id);

    @Query("select a.id as id, a.atcTitle as atcTitle, a.atcWriter as atcWriter, a.atcPhotoIn as atcPhotoIn, a.difficulty as difficulty, a.genre as genre from Article a left outer join TbRead t on t.tbUserId=:id and a.id = t.tbArticleId where t.tbArticleId is NULL and a.cgId = :cgId")
    Page<ArticleInterface> findByArticleLEFTJOINTbRead(String id, int cgId, Pageable pageable);
}
