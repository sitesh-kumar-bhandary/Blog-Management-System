package com.siteshkumar.bms.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.bms.DTO.CommentResponse;
import com.siteshkumar.bms.DTO.CommentRequest;
import com.siteshkumar.bms.Service.CommentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts/comments")
public class CommentController {
    
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/public/add/{postId}")
    public ResponseEntity<CommentResponse> addCommentInPost(@PathVariable Long postId, @Valid @RequestBody CommentRequest dto){
        CommentResponse comment = commentService.addCommentInPost(postId, dto);
        return ResponseEntity.status(201).body(comment);
    }

    @GetMapping("/public/all/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsOfAPosts(@PathVariable Long postId){
        List<CommentResponse> comments = commentService.getAllCommentsOfAPosts(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteCommentOfAPost(@PathVariable Long commentId){
        commentService.deleteCommentOfAPost(commentId);
        return ResponseEntity.ok("Comment deleted successfully!!!");
    }
}
