package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.service.CommentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("Test create comment")
    @Order(1)
    public void testCreateComment(){
        // TODO
    }

    @Test
    @DisplayName("Test update comment")
    @Order(1)
    public void testUpdateComment(){
        // TODO
    }

    @Test
    @DisplayName("Test delete comment")
    @Order(1)
    public void testDeleteComment(){
        // TODO
    }

}
