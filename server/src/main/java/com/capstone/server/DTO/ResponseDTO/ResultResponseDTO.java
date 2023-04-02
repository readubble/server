package com.capstone.server.DTO.ResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class ResultResponseDTO {
    private int code;
    private String message;
    private JSONObject data;

    @Builder
    public ResultResponseDTO(int code, String message, JSONObject data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
