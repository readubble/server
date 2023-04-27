package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveWordRepository extends JpaRepository<SaveWord, String> {
}
