package com.siteshkumar.bms.Service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import com.siteshkumar.bms.DTO.PostRequest;
import com.siteshkumar.bms.DTO.PostUpdateRequest;
import com.siteshkumar.bms.Entity.PostEntity;
import com.siteshkumar.bms.Entity.UserEntity;
import com.siteshkumar.bms.Repository.PostRepository;
import com.siteshkumar.bms.Repository.UserRepository;
import com.siteshkumar.bms.Security.AuthUtils;
import com.siteshkumar.bms.Security.CustomUserDetails;
import com.siteshkumar.bms.TestData.TestDataFactory;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

        @Mock
        private PostRepository postRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private AuthUtils authUtils;

        @InjectMocks
        private PostServiceImpl postService;

        @Test
        void shouldCreatePostSuccessfully() {
                UserEntity user = TestDataFactory.createUser();
                CustomUserDetails userDetails = new CustomUserDetails(user);

                when(authUtils.getCurrentLoggedInUser()).thenReturn(userDetails);
                when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
                when(postRepository.save(any(PostEntity.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                PostRequest request = new PostRequest("Title", "Content");

                var response = postService.createPost(request);

                assertEquals("Title", response.getTitle());
                assertEquals("Content", response.getContent());
        }

        @Test
        void shouldThrowAccessDeniedWhenUpdatingOtherUsersPost() {
                UserEntity author = TestDataFactory.createUser();
                PostEntity post = TestDataFactory.createPost(author);
                post.setPostId(10L);

                when(postRepository.findById(10L)).thenReturn(Optional.of(post));

                UserEntity otherUser = TestDataFactory.createUser();
                otherUser.setUserId(2L);
                CustomUserDetails otherUserDetails = new CustomUserDetails(otherUser);

                when(authUtils.getCurrentLoggedInUser()).thenReturn(otherUserDetails);

                PostUpdateRequest updateRequest = new PostUpdateRequest("New Title", "New Content");

                assertThrows(AccessDeniedException.class,
                                () -> postService.updatePost(10L, updateRequest));
        }
}
