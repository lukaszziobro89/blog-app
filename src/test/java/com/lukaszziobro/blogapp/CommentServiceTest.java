package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.BlogAPIException;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.service.CommentService;
import com.lukaszziobro.blogapp.service.PostService;
import com.lukaszziobro.blogapp.utils.CommentMapper;
import com.lukaszziobro.blogapp.utils.PostMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
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
        Assertions.assertThat(commentCreated.getId()).isEqualTo(2);
        Assertions.assertThat(commentCreated.getPost().getId()).isEqualTo(6);

        CommentDto updateCommentDto = CommentDto.builder()
                .name("updated comment")
                .email("test_mail@mail.com")
                .body("updated comment body")
                .build();

        Comment updatedComment = commentMapper.mapToComment(commentService.updateComment(6, 2, updateCommentDto));

        Post postWithCommentUpdated = postMapper.mapToPost(postService.getPostById(6));

        Assertions.assertThat(postWithCommentUpdated.getComments().size()).isEqualTo(1);

        Assertions.assertThat(updatedComment.getName()).isEqualTo("updated comment");
        Assertions.assertThat(updatedComment.getEmail()).isEqualTo("test_mail@mail.com");
        Assertions.assertThat(updatedComment.getBody()).isEqualTo("updated comment body");
        Assertions.assertThat(commentCreated.getId()).isEqualTo(2);
        Assertions.assertThat(commentCreated.getPost().getId()).isEqualTo(6);

    }

    @Test
    @DisplayName("Update comment - comment does not belong to post")
    @Order(3)
    public void testUpdateCommentNotBelongToPost(){
        Post post = postMapper.mapToPost(postService.getPostById(9));
        Assertions.assertThat(post.getComments().size()).isEqualTo(0);

        CommentDto commentDto = CommentDto.builder()
                .name("test comment 1")
                .email("test_mail1@mail.com")
                .body("comment body 1")
                .build();

        Comment comment = commentMapper.mapToComment(commentService.createComment(8, commentDto));
        Assertions.assertThat(comment.getPost().getId()).isEqualTo(8);

        CommentDto updateCommentDto = CommentDto.builder()
                .name("updated comment")
                .email("test_mail@mail.com")
                .body("updated comment body")
                .build();


        Exception exception = assertThrows(BlogAPIException.class, () -> {
            commentService.updateComment(9, comment.getId(), updateCommentDto);
        });

        String expectedMessage = "Comment does not belong to post";
        String actualMessage = exception.getMessage();

        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("Test delete comment")
    @Order(4)
    public void testDeleteComment(){

        Post post = postMapper.mapToPost(postService.getPostById(5));
        Assertions.assertThat(post.getComments().size()).isEqualTo(0);

        CommentDto commentDto = CommentDto.builder()
                .name("test comment 1")
                .email("test_mail1@mail.com")
                .body("comment body 1")
                .build();

        CommentDto commentDto2 = CommentDto.builder()
                .name("test comment 2")
                .email("test_mail2@mail.com")
                .body("comment body 2")
                .build();

        Comment comment1 = commentMapper.mapToComment(commentService.createComment(5, commentDto));
        Comment comment2 = commentMapper.mapToComment(commentService.createComment(5, commentDto2));

        Post postWithComments = postMapper.mapToPost(postService.getPostById(5));

        Assertions.assertThat(postWithComments.getComments().size()).isEqualTo(2);

        commentService.deleteComment(5, comment1.getId());

        Post postAfterCommentsDelete = postMapper.mapToPost(postService.getPostById(5));

        Assertions.assertThat(postAfterCommentsDelete.getComments().size()).isEqualTo(1);
        Assertions.assertThat(postAfterCommentsDelete
                .getComments().stream().toList().get(0).getId()).isEqualTo(comment2.getId());

    }

    @Test
    @DisplayName("")
    @Order(5)
    public void getCommentByCommentId(){

    }

    @Test
    @DisplayName("")
    @Order(6)
    public void getAllComments(){

    }

}
