package com.capstone.server.Service;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.Domain.Dict;
import com.capstone.server.Domain.Search;
import com.capstone.server.Repository.DictRepository;
import com.capstone.server.Repository.SearchRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class DictServiceTest {
    @Mock
    DictRepository dictRepository;
    @Mock
    SearchRepository searchRepository;
    @InjectMocks
    DictService dictService;

    static DictDTO dict1;
    static DictDTO dict2;
    @BeforeAll
    static void setup(){
        dict1 = new DictDTO(1, "사과", "살이 연하고 달며 물이 많은 참외", "사과를 먹다");
        dict2 = new DictDTO(2, "사과", "조선 시대에, 오위에 둔 정육품의 군직. 현직에 종사하고 있찌 않은 문관, 무관 및 음관이 맡았다.", "");
    }

    @Test
    void getDictInfoDB_test(){
        when(dictRepository.findAllByWordNm("사과"))
                .thenReturn(List.of(dict1, dict2));
        List<DictDTO> result = dictService.getDictInfoDB("사과", "test123");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getWordNo()).isEqualTo(1);
        verify(searchRepository, times(2)).save(any(Search.class));
    }

    @Test
    void getDictInfo_test(){

    }

}