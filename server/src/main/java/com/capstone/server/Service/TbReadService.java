package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.ProblemRequestDTO;
import com.capstone.server.Domain.QuizAnswer;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Interface.TbReadInterface;
import com.capstone.server.Repository.QuizAnswerRepository;
import com.capstone.server.Repository.TbReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class TbReadService {
    private final TbReadRepository tbReadRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final ArticleService articleService;
    @Autowired
    public TbReadService(TbReadRepository tbReadRepository, QuizAnswerRepository quizAnswerRepository, ArticleService articleService){
        this.tbReadRepository = tbReadRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.articleService = articleService;
    }

    public List<TbReadInterface> getUserReadHistory(String userId, String difficulty, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<TbReadInterface> result = tbReadRepository.findAllByTbUserIdLEFTJOINArticle(userId, difficulty, pageable).toList();

        return result;
    }

    public int countReadArticles(String userId, String difficulty){
        int result = tbReadRepository.countAllBySolveFlIsAndTbUserIdJoinArticle("Y", userId, difficulty);
        return result;
    }

    public void saveReadHistory(ProblemRequestDTO problemRequestDTO, int id){
        TbRead tbRead = TbRead.builder()
                .tbUserId(problemRequestDTO.getUserId())
                .tbArticleId(id)
                .startTime(problemRequestDTO.getStartTime())
                .finishTime(problemRequestDTO.getFinishTime())
                .totalTime(problemRequestDTO.getTotalTime())
                .saveFl("N")
                .solveFl("Y")
                .inpKwd1(problemRequestDTO.getKeyword().get(0))
                .inpKwd2(problemRequestDTO.getKeyword().get(1))
                .inpKwd3(problemRequestDTO.getKeyword().get(2))
                .inpTopic(problemRequestDTO.getSentence())
                .inpSmr(problemRequestDTO.getSummarization())
                .build();
        tbReadRepository.save(tbRead);
        saveQuizHistory(problemRequestDTO, id);
    }

    private void saveQuizHistory(ProblemRequestDTO problemRequestDTO, int id) {
        for(int i = 0; i< problemRequestDTO.getQuizChoice().size(); i++){
            QuizAnswer quizAnswer = QuizAnswer.builder()
                    .tbUserId(problemRequestDTO.getUserId())
                    .tbArticleId(id)
                    .tbQuizNo(problemRequestDTO.getQuizId().get(i))
                    .quizInp(problemRequestDTO.getQuizChoice().get(i))
                    .correctFl(problemRequestDTO.getQuizResult().get(i)).build();
            quizAnswerRepository.save(quizAnswer);
        }
    }

    public JSONObject getReadResult(int problemId, String userId){
        TbRead result = tbReadRepository.findByTbUserIdAndTbArticleId(userId, problemId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("problem_id", result.getTbArticleId());
        jsonObject.put("time", result.getTotalTime());
        jsonObject.put("keyword", List.of(result.getInpKwd1(), result.getInpKwd2(), result.getInpKwd3()));
        jsonObject.put("sentence", Arrays.asList(result.getInpTopic().split("[|]")));
        jsonObject.put("summarization", result.getInpSmr());
        jsonObject.put("ai-summarization", articleService.getSummarization(problemId));
        jsonObject.put("save_fl", result.getSaveFl());
        return jsonObject;
    }

    public void updateSaveFl(String userId, int problemId){
        TbRead tbReadData = tbReadRepository.findByTbUserIdAndTbArticleId(userId, problemId);
        if (tbReadData.getSaveFl() == "Y") {
            tbReadData.setSaveFl("N");
        } else {
            tbReadData.setSaveFl("Y");
        }
        tbReadRepository.save(tbReadData);
    }

}
