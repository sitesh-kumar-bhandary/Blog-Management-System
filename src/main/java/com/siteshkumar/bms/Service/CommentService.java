package com.siteshkumar.bms.Service;

import java.util.List;
import com.siteshkumar.bms.DTO.CommentResponse;
import com.siteshkumar.bms.DTO.CommentRequest;

public interface CommentService {
    
    public CommentResponse addCommentInPost(Long postId, CommentRequest dto);
    public List<CommentResponse> getAllCommentsOfAPosts(Long postId);
    public void deleteCommentOfAPost(Long commentId);
}
