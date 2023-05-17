package com.lukaszziobro.blogapp.controller;

import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.service.CommentService;
import com.lukaszziobro.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    private CommentService commentService;
    private PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody @Valid CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @PutMapping
    void updateComment(){}

    @DeleteMapping("/{id}")
    void deleteComment(){}

}
