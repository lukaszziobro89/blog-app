package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("Test init data loaded correctly - count")
    public void testInitDataLoadedCount(){
        Pageable page_0 = PageRequest.of(0,10);
        Pageable page_1 = PageRequest.of(1,10);
        Page<PostDto> postList0 = postService.getAllPosts(page_0);
        Page<PostDto> postList1 = postService.getAllPosts(page_1);
        Assertions.assertThat(postList0.getSize()).isEqualTo(10);
        Assertions.assertThat(postList1.getSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test init data loaded correctly - sample")
    public void testInitDataLoadedSample(){
        Pageable page_0 = PageRequest.of(0,10);
        List<PostDto> postList = postService.getAllPosts(page_0).stream().filter(
                post -> post.getId().equals(3L)).toList();
        Assertions.assertThat(postList.size()).isEqualTo(1);
        PostDto post = postList.get(0);
        Assertions.assertThat(post.getId()).isEqualTo(3L);
        Assertions.assertThat(post.getTitle()).isEqualTo("title 3");
        Assertions.assertThat(post.getContent()).isEqualTo("content 3");
        Assertions.assertThat(post.getDescription()).isEqualTo("description 3");
    }

    @Test
    @DisplayName("Test create new post")
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

}
