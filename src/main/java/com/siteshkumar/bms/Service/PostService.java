package com.siteshkumar.bms.Service;

import java.util.List;
import com.siteshkumar.bms.DTO.PostRequest;
import com.siteshkumar.bms.DTO.PostResponse;
import com.siteshkumar.bms.DTO.PostUpdateRequest;

public interface PostService {

    public PostResponse createPost(PostRequest dto);
    public PostResponse updatePost(Long postId, PostUpdateRequest dto);
    public void deletePost(Long postId);
    public List<PostResponse> getAllPosts();
    public PostResponse getPostById(Long postId);
}
