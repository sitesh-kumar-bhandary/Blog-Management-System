package com.siteshkumar.bms.Mapper;

import com.siteshkumar.bms.DTO.PostResponse;
import com.siteshkumar.bms.Entity.PostEntity;

public class PostMapper {
    
    public static PostResponse entityToDto(PostEntity entity){
        if(entity == null)
            return null;

        Long authorId = entity.getAuthor().getUserId();

        PostResponse dto = new PostResponse();
        dto.setPostId(entity.getPostId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAuthorId(authorId);

        return dto;
    }
}
