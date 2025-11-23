package com.siteshkumar.bms.Mapper;

import com.siteshkumar.bms.DTO.CommentResponse;
import com.siteshkumar.bms.Entity.CommentEntity;

public class CommentMapper {
    
    public static CommentResponse entityToDto(CommentEntity entity){
        if(entity == null)
            return null;
            
        return new CommentResponse(
            entity.getCommentId(),
            entity.getText(),
            entity.getCreatedAt(),
            entity.getAuthor().getUserId(),
            entity.getPost().getPostId()
        );
    }
}
