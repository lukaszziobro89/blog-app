package com.lukaszziobro.blogapp.controller;

import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    private PostService postService;

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping // TODO: add sorting either by @SortDefault or other
    public Page<PostDto> getPosts(
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC, sort = "id") Pageable pageable){
        logger.info("retrieving all posts");
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") long id) {
        logger.info("retrieving Post with id: " + id);
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto,
                                              @PathVariable("id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.NO_CONTENT);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    }
}
