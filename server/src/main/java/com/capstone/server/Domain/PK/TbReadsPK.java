package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbReadsPK implements Serializable {
    private int tbArticleId;
    private String tbUserId;
}
