package com.capstone.server.Repository;

import com.capstone.server.Domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, String> {
    Optional<Search> findByTbUserIdAndTbDictWordNo(String userId, int tbDictWordNo);
    Boolean existsByTbUserIdAndTbDictWordNo(String userId, int tbDictWordNo);
    Search save(Search search);
}
