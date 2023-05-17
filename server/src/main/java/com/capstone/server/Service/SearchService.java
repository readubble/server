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

    public void updateSaveFl(String userId, int wordNo){
        Optional<Search> searchData = searchRepository.findByTbUserIdAndTbDictWordNo(userId, wordNo);
        if(searchData.isPresent()) {
            if (searchData.get().getSaveFl() == "Y") { // 데이터가 존재하는 경우,
                searchData.get().setSaveFl("N"); // setSaveFl() 메소드를 이용해 save_fl 값을 "Y"로 업데이트한 뒤,
            } else {
                searchData.get().setSaveFl("Y");
            }
            searchRepository.save(searchData.get());
        }
    }
}
