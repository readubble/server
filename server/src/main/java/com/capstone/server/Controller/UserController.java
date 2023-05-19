package com.capstone.server.Controller;

import com.capstone.server.DTO.TbReadDTO;
import com.capstone.server.DTO.RequestDTO.JoinRequestDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResultResponseDTO;
import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.DTO.UserDTO;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Etc.JsonRequestWrapper;
import com.capstone.server.Interface.TbReadInterface;
import com.capstone.server.JWT.JwtProperties;
import com.capstone.server.Service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final QuizAnswerService quizAnswerService;
    private final TbReadService tbReadService;
    private final WordQuizAnswerService wordQuizAnswerService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService, QuizAnswerService quizAnswerService, TbReadService tbReadService, WordQuizAnswerService wordQuizAnswerService){
        this.userService = userService;
        this.tokenService = tokenService;
        this.quizAnswerService = quizAnswerService;
        this.tbReadService = tbReadService;
        this.wordQuizAnswerService = wordQuizAnswerService;
    }

    @PostMapping("/users")
    public ResponseDTO join(@RequestBody JoinRequestDTO joinRequestDTO){
        userService.join(joinRequestDTO);

        return ResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()).build();
    }

    @PostMapping("/users/authorize")
    public void login(JsonRequestWrapper request, HttpServletResponse response) throws IOException{
        String userId = response.getHeader("id");
        TokenDTO dto = TokenDTO.builder()
                .userId(userId)
                .token(response.getHeader(JwtProperties.REFRESH_HEADER_STRING))
                .status(true).build();
        tokenService.TokenSave(dto);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", response.getHeader(JwtProperties.ACCESS_HEADER_STRING));
        jsonObject.put("refresh_token", response.getHeader(JwtProperties.REFRESH_HEADER_STRING));

        ObjectMapper om = new ObjectMapper();
        ResultResponseDTO responseDTO = ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(jsonObject).build();

        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String result = om.writeValueAsString(responseDTO);
        response.getWriter().write(result);
    }

    @GetMapping("/users/authorize/auto")
    public ResultResponseDTO autoLogin(@RequestHeader("Authorization") String Authorization){
        String userId = tokenService.getUsername(Authorization.replace(JwtProperties.TOKEN_PREFIX, ""));
        userService.AutoLogin(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", userId);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(jsonObject).build();
    }

    @PostMapping("/users/authorize/token")
    public ResultResponseDTO tokenRenew(@RequestHeader("Authorization") String Authorization, @RequestBody JSONObject jsonObject, HttpServletResponse response, HttpServletRequest request){
        String userId = tokenService.getUsername(Authorization.replaceFirst("Bearer ",""));
        String RefreshToken = jsonObject.get("refresh_token").toString();
        String accessToken = tokenService.TokenRenew(RefreshToken, userId);
        JSONObject result = new JSONObject();
        result.put("access_token", accessToken);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result).build();
    }

    @PostMapping("/users/logout")
    public ResponseDTO logout(@RequestHeader("Authorization") String Authorization, @RequestBody JSONObject jsonObject){
        String userId = jsonObject.get("user_id").toString();
        tokenService.TokenUpdate(userId, Authorization);
        return ResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()).build();
    }

    @GetMapping("/users/{id}")
    public ResultResponseDTO userInfo(@PathVariable("id") String userId){
        UserDTO userDTO = userService.getUserInfo(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", userDTO.getUserNm());
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        jsonObject.put("date", format.format(userDTO.getJoinDt()));
        jsonObject.put("level", userDTO.getUserLevel());
        jsonObject.put("exp", userDTO.getUserExp());
        jsonObject.put("profile", userDTO.getUserPhotoIn());
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(jsonObject).build();
    }

    @GetMapping("/users/{id}/statistics")
    public ListResultReponseDTO userStatisticsInfo(@PathVariable("id") String userId){
        String[] result_difficulty = {"하", "중", "상"};

        List result = new ArrayList();
        int num_result;

        for(int i=0; i<3; i++) {
            JSONObject jsonObject = new JSONObject();
            num_result = tbReadService.ReadArticleInfo(userId, result_difficulty[i]);
            jsonObject.put("level", result_difficulty[i]);
            jsonObject.put("num", num_result);
            result.add(jsonObject);
        }

        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result).build();
    }

    @GetMapping("/users/{id}/problem")
    public ListResultReponseDTO userProblemInfo(@PathVariable("id") String userId, @RequestParam(name="level", required=true) String difficulty, @RequestParam(name="page", required = false, defaultValue = "0") int page, @RequestParam(name="size", required = false, defaultValue = "5") int size){
        List<TbReadInterface> result = tbReadService.getUserReadInfo(userId, difficulty, page, size);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result).build();
    }

    @GetMapping("/users/{id}/quiz")
    public ResultResponseDTO userQuizInformation(@PathVariable("id") String userId){
        String result = wordQuizAnswerService.wordQuizInfo(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(jsonObject).build();
    }

    @PostMapping("/users/{id}/profile")
    public ResultResponseDTO profile(@PathVariable("id") String id, @RequestPart(value="file") MultipartFile file){
        String url = userService.uploadFile(id, file);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", url);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(jsonObject).build();

    }
}
