package com.capstone.server.Service;

import com.capstone.server.DTO.ReadDTO;
import com.capstone.server.Repository.ReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReadService {
    private final ReadRepository readRepository;
    @Autowired
    public ReadService(ReadRepository readRepository){
        this.readRepository = readRepository;
    }

    public List<ReadDTO> getUserReadInfo(String userId, String difficulty, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<ReadDTO> result = readRepository.findAllBytbUserIdLEFTJOINArticle(userId, difficulty, pageable).toList();
        return result;
    }

}
