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

import java.util.List;

@RestController
@RequestMapping("/api/")
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

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId,
            @RequestBody @Valid CommentDto commentDto){
        commentDto.setPostId(postId);
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.NO_CONTENT);
    }

//    @DeleteMapping("/{id}")
//    void deleteComment(){}

}
