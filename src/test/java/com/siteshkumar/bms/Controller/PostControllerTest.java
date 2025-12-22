package com.siteshkumar.bms.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siteshkumar.bms.DTO.PostRequest;
import com.siteshkumar.bms.DTO.PostResponse;
import com.siteshkumar.bms.Security.AuthUtils;
import com.siteshkumar.bms.Service.PostService;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private AuthUtils authUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPostsTest() throws Exception {
        PostResponse response = new PostResponse();
        response.setPostId(1L);
        response.setTitle("Title");
        response.setContent("Content");

        when(postService.getAllPosts()).thenReturn(List.of(response));

        mockMvc.perform(get("/posts/public/all"))
               .andExpect(status().isOk());
    }

    @Test
    void createPostTest() throws Exception {
        PostRequest request = new PostRequest();
        request.setTitle("Title");
        request.setContent("Content");

        PostResponse response = new PostResponse();
        response.setPostId(1L);
        response.setTitle("Title");
        response.setContent("Content");

        when(postService.createPost(any(PostRequest.class))).thenReturn(response);

        mockMvc.perform(post("/posts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
