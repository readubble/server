package com.capstone.server.Service;

import com.capstone.server.DTO.TbReadDTO;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Repository.TbReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TbReadService {
    private final TbReadRepository tbReadRepository;
    @Autowired
    public TbReadService(TbReadRepository tbReadRepository){
        this.tbReadRepository = tbReadRepository;
    }

    public List<TbRead> getUserReadInfo(String userId, String difficulty, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<TbRead> result = tbReadRepository.findAllByTbUserIdLEFTJOINArticle(userId, difficulty, pageable).toList();
        return result;
    }

}
