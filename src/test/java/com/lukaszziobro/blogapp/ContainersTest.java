//package com.lukaszziobro.blogapp;
//
//import com.lukaszziobro.blogapp.payload.PostDto;
//import com.lukaszziobro.blogapp.service.PostService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.annotation.DirtiesContext;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Testcontainers
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class ContainersTest extends TestContainersBase{
//
//    @Autowired
//    private  PostService postService;
//
//    @Test
//    @DisplayName("Test first test")
//    @Order(1)
//    public void firstTest() throws InterruptedException {
//        Assertions.assertThat(1).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("Test check init")
//    @Order(2)
//    public void checkInit(){
//        Pageable wholePage = Pageable.unpaged();
//        List<PostDto> postDtoList = postService.getAllPosts(wholePage).stream().toList();
//        Assertions.assertThat(postDtoList.size()).isEqualTo(11);
//    }
//
//}
