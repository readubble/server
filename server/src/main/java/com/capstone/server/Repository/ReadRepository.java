package com.capstone.server.Repository;

import com.capstone.server.Domain.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadRepository extends JpaRepository<Read, String> {
}
