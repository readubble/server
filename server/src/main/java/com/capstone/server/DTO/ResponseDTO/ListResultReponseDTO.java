package com.capstone.server.DTO.ResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResultReponseDTO {
    private int code;
    private String message;
    private List data;

    @Builder
    public ListResultReponseDTO(int code, String message, List data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
