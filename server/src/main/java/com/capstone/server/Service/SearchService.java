package com.capstone.server.Service;

import com.capstone.server.DTO.SearchDTO;
import com.capstone.server.Domain.Search;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SearchService {
    private final SearchRepository searchRepository;
    @Autowired
    public SearchService(SearchRepository searchRepository){
        this.searchRepository = searchRepository;
    }
    public void save(SearchDTO searchDTO){
        searchRepository.save(searchDTO.toEntity());
    }
}
