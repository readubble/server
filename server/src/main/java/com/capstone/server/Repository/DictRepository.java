package com.capstone.server.Repository;

import com.capstone.server.Domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictRepository extends JpaRepository<Dict, String> {
}
