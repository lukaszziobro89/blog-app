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
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InitDataTest {

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

}
