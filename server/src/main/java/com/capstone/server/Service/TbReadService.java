package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.ProblemRequestDTO;
import com.capstone.server.DTO.TbReadDTO;
import com.capstone.server.Domain.Quiz;
import com.capstone.server.Domain.QuizAnswer;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Interface.TbReadInterface;
import com.capstone.server.Repository.QuizAnswerRepository;
import com.capstone.server.Repository.QuizRepository;
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
    private final QuizAnswerRepository quizAnswerRepository;
    @Autowired
    public TbReadService(TbReadRepository tbReadRepository, QuizAnswerRepository quizAnswerRepository){
        this.tbReadRepository = tbReadRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public List<TbReadInterface> getUserReadInfo(String userId, String difficulty, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<TbReadInterface> result = tbReadRepository.findAllByTbUserIdLEFTJOINArticle(userId, difficulty, pageable).toList();

        return result;
    }

    public void save(ProblemRequestDTO problemRequestDTO, int id){
        TbRead tbRead = TbRead.builder()
                .tbUserId(problemRequestDTO.getUserId())
                .tbArticleId(id)
                .startTime(problemRequestDTO.getStartTime())
                .finishTime(problemRequestDTO.getFinishTime())
                .totalTime(problemRequestDTO.getTotalTime())
                .saveFl("")
                .solveFl("Y")
                .inpKwd1(problemRequestDTO.getKeyword().get(0))
                .inpKwd2(problemRequestDTO.getKeyword().get(1))
                .inpKwd3(problemRequestDTO.getKeyword().get(2))
                .inpTopic(problemRequestDTO.getSentence())
                .inpSmr(problemRequestDTO.getSummarization())
                .build();
        tbReadRepository.save(tbRead);
        for(int i=0; i<problemRequestDTO.getQuizChoice().size(); i++){
            QuizAnswer quizAnswer = QuizAnswer.builder()
                    .tbQuizTbArticleId(id)
                    .tbQuizQuizNo(problemRequestDTO.getQuizId().get(i))
                    .quizInp(problemRequestDTO.getQuizChoice().get(i))
                    .correctFl(problemRequestDTO.getQuizResult().get(i)).build();
            quizAnswerRepository.save(quizAnswer);
        }

    }

}
