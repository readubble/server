package com.capstone.server.Service;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.Domain.Dict;
import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Repository.DictRepository;
import com.capstone.server.Repository.SaveWordRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class SaveWordServiceTest {

    @Mock
    SaveWordRepository saveWordRepository;

    @Mock
    DictRepository dictRepository;

    @InjectMocks
    SaveWordService saveWordService;

    static DictDTO dict1;
    static DictDTO dict2;
    @BeforeAll
    static void setup(){
        dict1 = new DictDTO(1, "사과", 1,"살이 연하고 달며 물이 많은 참외");
        dict2 = new DictDTO(2, "사과", 1,"조선 시대에, 오위에 둔 정육품의 군직. 현직에 종사하고 있찌 않은 문관, 무관 및 음관이 맡았다.");
    }

    @Test
    void saveWord() {
        // given
        Dict dict = new Dict(1,"사과","과일", 123);
        when(dictRepository.findByTargetCode(anyInt())).thenReturn(dict);

        when(saveWordRepository.existsByTbUserIdAndTargetCode(anyString(), anyInt())).thenReturn(true);

        // when
        saveWordService.bookmarkWord("test123", 123,"","");

        // then
        verify(dictRepository, times(1)).findByTargetCode(anyInt());
        verify(saveWordRepository, times(1)).existsByTbUserIdAndTargetCode(anyString(), anyInt());
        verify(saveWordRepository, times(1)).save(any(SaveWord.class));
    }

    @Test
    void deleteWord() {
        // given
        SaveWord saveWord = new SaveWord(1, "test123",123,"배","");
        when(saveWordRepository.findByTbUserIdAndTargetCode(anyString(), anyInt())).thenReturn(Optional.of(saveWord));

        // when
        saveWordService.deleteBookmarkWord(saveWord);

        // then
        verify(saveWordRepository, times(1)).findByTbUserIdAndTargetCode(anyString(), anyInt());
        verify(saveWordRepository, times(1)).delete(any(SaveWord.class));
    }

    @Test
    void SaveWordList_test(){
        when(saveWordRepository.findAllByTbUserId(anyString()))
                .thenReturn(List.of(SaveWord.builder().targetCode(123).build()));

        List<SaveWord> result = saveWordService.getBookmarkWords("test123");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTargetCode()).isEqualTo(123);
    }
}
