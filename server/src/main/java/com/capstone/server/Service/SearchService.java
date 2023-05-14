package com.capstone.server.Service;

import com.capstone.server.DTO.SearchDTO;
import com.capstone.server.Domain.Search;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateSaveFl(String userId, int wordNo){
        Search searchData = searchRepository.findByTbUserIdAndTbDictWordNo(userId, wordNo);
        if (searchData.getSaveFl() == "Y") { // 데이터가 존재하는 경우,
            searchData.setSaveFl("N"); // setSaveFl() 메소드를 이용해 save_fl 값을 "Y"로 업데이트한 뒤,
        } else {
            searchData.setSaveFl("Y");
        }
        searchRepository.save(searchData);
    }
}
