package com.siteshkumar.bms.TestData;

import com.siteshkumar.bms.Entity.PostEntity;
import com.siteshkumar.bms.Entity.UserEntity;

public class TestDataFactory {

    public static UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");
        user.setRoles("ROLE_USER");
        return user;
    }

    public static PostEntity createPost(UserEntity user) {
        PostEntity post = new PostEntity();
        post.setTitle("Test title");
        post.setContent("Test content");
        post.setAuthor(user);
        return post;
    }
}

