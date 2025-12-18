package com.siteshkumar.bms.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.siteshkumar.bms.DTO.CommentResponse;
import com.siteshkumar.bms.DTO.CommentRequest;
import com.siteshkumar.bms.Entity.CommentEntity;
import com.siteshkumar.bms.Entity.PostEntity;
import com.siteshkumar.bms.Entity.UserEntity;
import com.siteshkumar.bms.Mapper.CommentMapper;
import com.siteshkumar.bms.Repository.CommentRepository;
import com.siteshkumar.bms.Repository.PostRepository;
import com.siteshkumar.bms.Repository.UserRepository;
import com.siteshkumar.bms.Security.AuthUtils;
import com.siteshkumar.bms.Security.CustomUserDetails;
import com.siteshkumar.bms.Service.CommentService;
import com.siteshkumar.bms.Service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthUtils authUtils;
    private final EmailService emailService;
    
    @Override
    public CommentResponse addCommentInPost(Long postId, CommentRequest dto){
        log.info("Adding comment in a post with postid: {}", postId);

        CustomUserDetails currentUser = authUtils.getCurrentLoggedInUser();

        UserEntity user = userRepository.findById(currentUser.getId())
                        .orElseThrow(() -> new RuntimeException("User not found!!!"));

        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        CommentEntity comment = new CommentEntity();
        comment.setText(dto.getText());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setPost(post);

        commentRepository.save(comment);

        log.info("Comment added successfully on a post with postId: {}", postId);

        // Send email to post creater
        emailService.sendEmail(
                            post.getAuthor().getEmail(), 
                            "New comment on your post: "+post.getTitle(),
                            "Original Post: "+post.getContent() + "\nComment: "+comment.getText() + "\nCommented by: " + currentUser.getEmail()
                        );

        return CommentMapper.entityToDto(comment);
    }

    @Override
    public List<CommentResponse> getAllCommentsOfAPosts(Long postId){
        log.info("Fetching all comments on a post: {}", postId);

        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        List<CommentEntity> comments = post.getComments();

        log.info("Fetched {} comments of a post: {}", comments.size(), postId);

        return comments.stream().map(CommentMapper::entityToDto).toList();
    }

    @Override
    public void deleteCommentOfAPost(Long commentId){
        log.info("Deleting comment with id: ", commentId);

        CommentEntity existingComment = commentRepository.findById(commentId)
                                    .orElseThrow(() -> new RuntimeException("Comment not found!!!"));

                                            CustomUserDetails currentUser = authUtils.getCurrentLoggedInUser();
        boolean isOwner = existingComment.getAuthor().getUserId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin)
            throw new AccessDeniedException("You are not allowed to delete this comment");

        commentRepository.delete(existingComment);

        log.info("Comment deleted Successfully!!!");
    }
}
