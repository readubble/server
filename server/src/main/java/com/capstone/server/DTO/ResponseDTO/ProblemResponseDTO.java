package com.capstone.server.DTO.ResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@Setter
public class ProblemResponseDTO {
    private int code;
    private String message;

    @Getter
    @Setter
    public static class Data{
        private JSONObject problem;
        private List quiz;
        @Builder
        public Data(JSONObject problem, List quiz){
            this.problem = problem;
            this.quiz = quiz;
        }
    }

    private Data data;

    @Builder
    public ProblemResponseDTO(int code, String message, Data data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
