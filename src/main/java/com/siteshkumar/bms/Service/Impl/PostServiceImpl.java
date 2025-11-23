package com.siteshkumar.bms.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.siteshkumar.bms.DTO.PostRequest;
import com.siteshkumar.bms.DTO.PostResponse;
import com.siteshkumar.bms.DTO.PostUpdateRequest;
import com.siteshkumar.bms.Entity.PostEntity;
import com.siteshkumar.bms.Entity.UserEntity;
import com.siteshkumar.bms.Mapper.PostMapper;
import com.siteshkumar.bms.Repository.PostRepository;
import com.siteshkumar.bms.Repository.UserRepository;
import com.siteshkumar.bms.Service.PostService;

@Service
public class PostServiceImpl implements PostService{
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostResponse createPost(PostRequest dto){
        UserEntity user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found!!!"));

        PostEntity post = new PostEntity();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setAuthor(user);

        PostEntity saved = postRepository.save(post);
        return PostMapper.entityToDto(saved);
    }

    @Override
    public PostResponse updatePost(Long postId, PostUpdateRequest dto){
        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException(" Post not found!!! "));

        if(dto.getContent() != null && !dto.getContent().isBlank())
            post.setContent(dto.getContent());

        if(dto.getTitle() != null && !dto.getTitle().isBlank())
            post.setTitle(dto.getTitle());

        post.setUpdatedAt(LocalDateTime.now());

        PostEntity saved = postRepository.save(post);
        return PostMapper.entityToDto(saved);
    }

    @Override
    public void deletePost(Long postId){
        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        postRepository.delete(post);
    }

    @Override
    public List<PostResponse> getAllPosts(){
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostResponse> posts = postEntities.stream().map(PostMapper::entityToDto).toList();
        return posts;
    }

    @Override
    public PostResponse getPostById(Long postId){
        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        return PostMapper.entityToDto(post);
    }
}
