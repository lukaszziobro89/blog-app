package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("Test init data loaded correctly - count")
    @Order(1)
    public void testInitDataLoadedCount(){
        Pageable page_0 = PageRequest.of(0,10);
        Pageable page_1 = PageRequest.of(1,10);
        Page<PostDto> postDtoList0 = postService.getAllPosts(page_0);
        Page<PostDto> postDtoList1 = postService.getAllPosts(page_1);
        Assertions.assertThat(postDtoList0.getSize()).isEqualTo(10);
        Assertions.assertThat(postDtoList1.getSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test init data loaded correctly - sample")
    @Order(2)
    public void testInitDataLoadedSample(){
        Pageable page_0 = PageRequest.of(0,10);
        List<PostDto> postDtoList = postService.getAllPosts(page_0).stream().filter(
                post -> post.getId().equals(3L)).toList();
        Assertions.assertThat(postDtoList.size()).isEqualTo(1);
        PostDto post = postDtoList.get(0);
        Assertions.assertThat(post.getId()).isEqualTo(3L);
        Assertions.assertThat(post.getTitle()).isEqualTo("title 3");
        Assertions.assertThat(post.getContent()).isEqualTo("content 3");
        Assertions.assertThat(post.getDescription()).isEqualTo("description 3");
    }

    @Test
    @DisplayName("Test create new post")
    @Order(3)
    public void testCreatePost(){

        PostDto postDto = PostDto.builder()
                .title("title_1")
                .description("description_1")
                .content("content_1")
                .build();

        PostDto savedPost = postService.createPost(postDto);

        Assertions.assertThat(savedPost).isNotNull();
        Assertions.assertThat(savedPost.getTitle()).isEqualTo("title_1");
        Assertions.assertThat(savedPost.getDescription()).isEqualTo("description_1");
        Assertions.assertThat(savedPost.getContent()).isEqualTo("content_1");
    }

    @Test
    @DisplayName("Test get all posts")
    @Order(4)
    public void testGetAllPosts(){
        Pageable wholePage = Pageable.unpaged();
        List<PostDto> postDtoList = postService.getAllPosts(wholePage).stream().toList();
        Assertions.assertThat(postDtoList.size()).isEqualTo(12);
    }

}
