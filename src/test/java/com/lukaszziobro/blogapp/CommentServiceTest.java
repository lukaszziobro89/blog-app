package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.service.CommentService;
import com.lukaszziobro.blogapp.service.PostService;
import com.lukaszziobro.blogapp.utils.CommentMapper;
import com.lukaszziobro.blogapp.utils.PostMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    @DisplayName("Test create comment")
    @Order(1)
    public void testCreateComment(){

        Post post = postMapper.mapToPost(postService.getPostById(7));
        Assertions.assertThat(post.getComments().size()).isEqualTo(0);

        CommentDto commentDto = CommentDto.builder()
                .name("test comment")
                .email("test_mail@mail.com")
                .body("comment body")
                .build();
        commentService.createComment(7, commentDto);

        Post postWithComment = postMapper.mapToPost(postService.getPostById(7));
        Assertions.assertThat(postWithComment.getComments().size()).isEqualTo(1);
        Comment comment = postWithComment.getComments().stream().toList().get(0);
        Assertions.assertThat(comment.getName()).isEqualTo("test comment");
        Assertions.assertThat(comment.getEmail()).isEqualTo("test_mail@mail.com");
        Assertions.assertThat(comment.getBody()).isEqualTo("comment body");

    }

    @Test
    @DisplayName("Test update comment")
    @Order(2)
    public void testUpdateComment(){
        Post post = postMapper.mapToPost(postService.getPostById(6));
        Assertions.assertThat(post.getComments().size()).isEqualTo(0);

        CommentDto commentDto = CommentDto.builder()
                .name("test comment")
                .email("test_mail@mail.com")
                .body("comment body")
                .build();

        commentService.createComment(6, commentDto);

        Post postWithComment = postMapper.mapToPost(postService.getPostById(6));
        Assertions.assertThat(postWithComment.getComments().size()).isEqualTo(1);

        Comment commentCreated = postWithComment.getComments().stream().toList().get(0);
        Assertions.assertThat(commentCreated.getName()).isEqualTo("test comment");
        Assertions.assertThat(commentCreated.getEmail()).isEqualTo("test_mail@mail.com");
        Assertions.assertThat(commentCreated.getBody()).isEqualTo("comment body");
        Assertions.assertThat(commentCreated.getId()).isEqualTo(1);

        CommentDto updateCommentDto = CommentDto.builder()
                .name("updated comment")
                .email("test_mail@mail.com")
                .body("updated comment body")
                .build();

        Comment updatedComment = commentMapper.mapToComment(commentService.updateComment(6, 1, updateCommentDto));

        Post postWithCommentUpdated = postMapper.mapToPost(postService.getPostById(6));

        Assertions.assertThat(postWithCommentUpdated.getComments().size()).isEqualTo(1);

        Assertions.assertThat(updatedComment.getName()).isEqualTo("updated comment");
        Assertions.assertThat(updatedComment.getEmail()).isEqualTo("test_mail@mail.com");
        Assertions.assertThat(updatedComment.getBody()).isEqualTo("updated comment body");
        Assertions.assertThat(updatedComment.getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("Update comment - comment does not belong to post")
    @Order(3)
    public void testUpdateCommentNotBelongToPost(){
        // TODO
    }

    @Test
    @DisplayName("Test delete comment")
    @Order(1)
    public void testDeleteComment(){
        // TODO
    }

}
