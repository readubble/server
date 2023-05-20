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

    public List<TbReadInterface> getUserReadInfo(String userId, String difficulty, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<TbReadInterface> result = tbReadRepository.findAllByTbUserIdLEFTJOINArticle(userId, difficulty, pageable).toList();

        return result;
    }

    public int ReadArticleInfo(String userId, String difficulty){
        int result = tbReadRepository.countAllBySolveFlIsAndTbUserIdJoinArticle("Y", userId, difficulty);
        return result;
    }

    // 이 코드는 ProblemRequestDTO와 id값을 인자로 받아서 TbRead 테이블에 데이터를 저장하고, 그에 따른 QuizAnswer 데이터도 저장하는 함수
    public void save(ProblemRequestDTO problemRequestDTO, int id){
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
        tbReadRepository.save(tbRead); // TbRead 데이터 저장: tbRead 객체를 이용해 tbReadRepository에서 데이터를 저장합니다.
        // QuizAnswer 데이터 저장: quizAnswer 객체를 이용해 quizAnswerRepository에서 데이터를 저장합니다. 이 과정은 문제 수 만큼 반복됩니다.
        for(int i=0; i<problemRequestDTO.getQuizChoice().size(); i++){
            QuizAnswer quizAnswer = QuizAnswer.builder()
                    .tbUserId(problemRequestDTO.getUserId())// QuizAnswer 객체 생성: problemRequestDTO에서 필요한 값을 가져와서 QuizAnswer 객체를 생성합니다.
                    .tbArticleId(id)
                    .tbQuizNo(problemRequestDTO.getQuizId().get(i))
                    .quizInp(problemRequestDTO.getQuizChoice().get(i))
                    .correctFl(problemRequestDTO.getQuizResult().get(i)).build();
            quizAnswerRepository.save(quizAnswer);
        }

    }

    public JSONObject getResult(int problemId, String userId){
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

    // updateSaveFl 함수는 userId와 problemId를 인자로 받아서, tbReadRepository를 통해 해당 userId와 problemId를 가진 데이터를 찾습니다.
    public void updateSaveFl(String userId, int problemId){
        TbRead tbReadData = tbReadRepository.findByTbUserIdAndTbArticleId(userId, problemId);
        if (tbReadData.getSaveFl() == "Y") { // 데이터가 존재하는 경우,
            tbReadData.setSaveFl("N"); // setSaveFl() 메소드를 이용해 save_fl 값을 "Y"로 업데이트한 뒤,
        } else {
            tbReadData.setSaveFl("Y");
        }
        tbReadRepository.save(tbReadData); // tbReadRepository를 통해 변경 내용을 저장합니다.
    }

}
