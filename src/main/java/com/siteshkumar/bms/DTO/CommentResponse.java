package com.siteshkumar.bms.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    
    private Long commentId;
    private String text;
    private LocalDateTime createdAt;
    private Long authorId;
    private Long postId;
}
