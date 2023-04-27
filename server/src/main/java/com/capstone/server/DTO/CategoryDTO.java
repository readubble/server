package com.capstone.server.DTO;

import com.capstone.server.Domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private int id;
    private String cgNm;

    @Builder
    public CategoryDTO(int id, String cgNm) {
        this.id = id;
        this.cgNm = cgNm;
    }

    public Category toEntity(){
        return Category.builder()
                .id(id)
                .cgNm(cgNm).build();
    }
    public CategoryDTO fromEntity(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .cgNm(category.getCgNm()).build();

    }
}
