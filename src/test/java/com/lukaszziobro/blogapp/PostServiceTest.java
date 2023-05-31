package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.service.PostService;
import com.lukaszziobro.blogapp.utils.PostMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Test
    @DisplayName("Test create new post")
    @Order(1)
    public void testCreatePost(){

        PostDto postDto = PostDto.builder()
                .title("title_12")
                .description("description_12")
                .content("content_12")
                .build();

        PostDto savedPost = postService.createPost(postDto);

        Assertions.assertThat(savedPost).isNotNull();
        Assertions.assertThat(savedPost.getId()).isEqualTo(13);
        Assertions.assertThat(savedPost.getTitle()).isEqualTo("title_12");
        Assertions.assertThat(savedPost.getDescription()).isEqualTo("description_12");
        Assertions.assertThat(savedPost.getContent()).isEqualTo("content_12");
    }

    @Test
    @DisplayName("Test get all posts")
    @Order(2)
    public void testGetAllPosts(){
        Pageable wholePage = Pageable.unpaged();
        List<PostDto> postDtoList = postService.getAllPosts(wholePage).stream().toList();
        Assertions.assertThat(postDtoList.size()).isEqualTo(12);
    }

    @Test
    @DisplayName("Test get post by post id")
    @Order(3)
    public void testGetPostById(){

        PostDto postDto = PostDto.builder()
                .title("title_12")
                .description("description_12")
                .content("content_12")
                .build();

        postService.createPost(postDto);

        Post post = postMapper.mapToPost(postService.getPostById(12L));

        Assertions.assertThat(post.getId()).isEqualTo(12);
        Assertions.assertThat(post.getDescription()).isEqualTo("description_12");
        Assertions.assertThat(post.getTitle()).isEqualTo("title_12");
        Assertions.assertThat(post.getContent()).isEqualTo("content_12");

    }

    @Test
    @DisplayName("Test get post by id throws resource not found exception")
    @Order(4)
    public void testGetPostByIdThrowsResourceNotFoundException(){

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
           postService.getPostById(123L);
        });

        String expectedMessage = "Post not found with id : '123'";
        String actualMessage = exception.getMessage();

        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);

    }

    @Test
    @DisplayName("Test get post by post id")
    @Order(5)
    public void testUpdatePost(){

        Post post = postMapper.mapToPost(postService.getPostById(5));

        Assertions.assertThat(post.getId()).isEqualTo(5);
        Assertions.assertThat(post.getDescription()).isEqualTo("description 5");
        Assertions.assertThat(post.getTitle()).isEqualTo("title 5");
        Assertions.assertThat(post.getContent()).isEqualTo("content 5");

        PostDto postDto = new PostDto(5L, "new title", "new description", "new content", null);
        PostDto updatedPost = postService.updatePost(postDto, 5L);

        Assertions.assertThat(updatedPost.getId()).isEqualTo(5);
        Assertions.assertThat(updatedPost.getTitle()).isEqualTo("new title");
        Assertions.assertThat(updatedPost.getContent()).isEqualTo("new content");
        Assertions.assertThat(updatedPost.getDescription()).isEqualTo("new description");
    }

    @Test
    @DisplayName("Test get post by post id")
    @Order(6)
    public void testDeletePost(){

        Pageable wholePage = Pageable.unpaged();
        List<PostDto> postDtoList = postService.getAllPosts(wholePage).stream().toList();
        Assertions.assertThat(postDtoList.size()).isEqualTo(13);

        postService.deletePostById(11);
        postService.deletePostById(10);

        Pageable allPostAfterDelete = Pageable.unpaged();
        List<PostDto> postDtoListAfterDelete = postService.getAllPosts(allPostAfterDelete).stream().toList();
        Assertions.assertThat(postDtoListAfterDelete.size()).isEqualTo(11);

    }

}
