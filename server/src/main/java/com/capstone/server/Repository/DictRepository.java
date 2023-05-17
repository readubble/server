package com.capstone.server.Repository;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.Domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictRepository extends JpaRepository<Dict, String> {
    List<DictDTO> findAllByWordNmStartingWith(String wordNm);

    Dict findByTargetCode(int targetCode);

    Dict findByWordNo(int wordNo);
}
