package com.lukaszziobro.blogapp;

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

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("Test init data loaded correctly")
    public void testInitDataLoaded(){
        Pageable page_0 = PageRequest.of(0,10);
        Pageable page_1 = PageRequest.of(1,10);
        Page<PostDto> postList0 = postService.getAllPosts(page_0);
        Page<PostDto> postList1 = postService.getAllPosts(page_1);
        Assertions.assertThat(postList0.getSize()).isEqualTo(10);
        Assertions.assertThat(postList1.getSize()).isEqualTo(2);
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
