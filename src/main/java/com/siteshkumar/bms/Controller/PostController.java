package com.siteshkumar.bms.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.bms.DTO.PostRequest;
import com.siteshkumar.bms.DTO.PostResponse;
import com.siteshkumar.bms.DTO.PostUpdateRequest;
import com.siteshkumar.bms.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    
    private final PostService postService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest dto){
        PostResponse createdPost = postService.createPost(dto);
        return ResponseEntity.status(201).body(createdPost);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequest dto){
        // System.out.println("CONTROLLER HIT");
        // System.out.println("***********************************");
        PostResponse updatedPost = postService.updatePost(postId, dto);
        return ResponseEntity.status(200).body(updatedPost);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted successfully!!!");
    }

    @GetMapping("/public/all")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/public/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId){
        PostResponse foundPost = postService.getPostById(postId);
        return ResponseEntity.ok(foundPost);
    }
}
