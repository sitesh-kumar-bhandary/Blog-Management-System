package com.siteshkumar.bms.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
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
import com.siteshkumar.bms.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    
    @Override
    public CommentResponse addCommentInPost(Long postId, CommentRequest dto){
        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        UserEntity user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found!!!"));

        CommentEntity comment = new CommentEntity();
        comment.setText(dto.getText());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return CommentMapper.entityToDto(comment);
    }

    @Override
    public List<CommentResponse> getAllCommentsOfAPosts(Long postId){
        PostEntity post = postRepository.findById(postId)
                        .orElseThrow(() -> new RuntimeException("Post not found!!!"));

        List<CommentEntity> comments = post.getComments();

        return comments.stream().map(CommentMapper::entityToDto).toList();
    }

    @Override
    public void deleteCommentOfAPost(Long commentId){
        CommentEntity existingComment = commentRepository.findById(commentId)
                                    .orElseThrow(() -> new RuntimeException("Comment not found!!!"));

        commentRepository.delete(existingComment);
    }
}
