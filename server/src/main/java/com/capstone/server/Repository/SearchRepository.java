package com.capstone.server.Repository;

import com.capstone.server.Domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, String> {
    Search findByTbUserIdAndTbDictWordNo(String userId, int tbDictWordNo);
    Boolean existsByTbUserIdAndTbDictWordNo(String userId, int tbDictWordNo);
    Search save(Search search);
}
