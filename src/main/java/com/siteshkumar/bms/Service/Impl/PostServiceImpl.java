package com.siteshkumar.bms.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.siteshkumar.bms.DTO.PostRequest;
import com.siteshkumar.bms.DTO.PostResponse;
import com.siteshkumar.bms.DTO.PostUpdateRequest;
import com.siteshkumar.bms.Entity.PostEntity;
import com.siteshkumar.bms.Entity.UserEntity;
import com.siteshkumar.bms.Mapper.PostMapper;
import com.siteshkumar.bms.Repository.PostRepository;
import com.siteshkumar.bms.Repository.UserRepository;
import com.siteshkumar.bms.Security.AuthUtils;
import com.siteshkumar.bms.Security.CustomUserDetails;
import com.siteshkumar.bms.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthUtils authUtils;

    @Override
    public PostResponse createPost(PostRequest dto){
        log.info("Creating post with title: {}", dto.getTitle());

        CustomUserDetails currentUser = authUtils.getCurrentLoggedInUser();

        UserEntity user = userRepository.findById(currentUser.getId())
                        .orElseThrow(() -> new RuntimeException("User not found!!!"));

        PostEntity post = new PostEntity();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setAuthor(user);

        PostEntity saved = postRepository.save(post);

        log.info("Post created successfully");

        return PostMapper.entityToDto(saved);
    }

    @Override
    public PostResponse updatePost(Long postId, PostUpdateRequest dto){
        log.info("Updating post...");

        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException(" Post not found!!!"));

        CustomUserDetails currentUser = authUtils.getCurrentLoggedInUser();
        
        if(! post.getAuthor().getUserId().equals(currentUser.getId())) {
            log.warn("Unauthorized update attempt by userId={} on postId={}", currentUser.getId(), postId);
            throw new AccessDeniedException("You are not allowed to update this post");
        }
            
        if(dto.getContent() != null && !dto.getContent().isBlank())
            post.setContent(dto.getContent());

        if(dto.getTitle() != null && !dto.getTitle().isBlank())
            post.setTitle(dto.getTitle());

        post.setUpdatedAt(LocalDateTime.now());

        PostEntity saved = postRepository.save(post);

        log.info("Post updated successfully");

        return PostMapper.entityToDto(saved);
    }

    @Override
    public void deletePost(Long postId){
        log.info("Deleting post with id: {}", postId);

        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        CustomUserDetails currentUser = authUtils.getCurrentLoggedInUser();
        boolean isOwner = post.getAuthor().getUserId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin)
            throw new AccessDeniedException("You are not allowed to delete this post");

        postRepository.delete(post);

        log.info("Post deleted successfully");
    }

    @Override
    public List<PostResponse> getAllPosts(){
        log.info("Fetching all the posts...");

        List<PostEntity> postEntities = postRepository.findAll();

        List<PostResponse> posts = postEntities.stream().map(PostMapper::entityToDto).toList();

        log.info("Fetched {} posts from database", posts.size());

        return posts;
    }

    @Override
    public PostResponse getPostById(Long postId){
        log.info("Fetching post by given id: {}", postId);

        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        log.info("This is the post of the given id");

        return PostMapper.entityToDto(post);
    }
}
