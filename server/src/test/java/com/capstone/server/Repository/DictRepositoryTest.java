package com.capstone.server.Repository;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.Domain.Dict;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class DictRepositoryTest {
    @Autowired
    DictRepository dictRepository;

    static DictDTO dict1;
    static DictDTO dict2;
    @BeforeAll
    static void setup(){
        dict1 = new DictDTO(1, "사과", "살이 연하고 달며 물이 많은 참외", "사과를 먹다");
        dict2 = new DictDTO(2, "사과", "조선 시대에, 오위에 둔 정육품의 군직. 현직에 종사하고 있찌 않은 문관, 무관 및 음관이 맡았다.", "");
    }
    @Test
    void findAllByWordNm_test(){
        dictRepository.save(dict1.toEntity());
        dictRepository.save(dict2.toEntity());
        List<DictDTO> result = dictRepository.findAllByWordNm("사과");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getWordNo()).isEqualTo(1);
        assertThat(result.get(1).getWordNo()).isEqualTo(2);
    }

}