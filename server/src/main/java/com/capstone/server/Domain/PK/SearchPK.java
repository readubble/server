package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchPK implements Serializable {
    private String UserId;
    private String DictWordNo;
}
