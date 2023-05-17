package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.repository.CommentRepository;
import com.lukaszziobro.blogapp.repository.PostRepository;
import com.lukaszziobro.blogapp.utils.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;

    public CommentDto createComment(long postId, CommentDto commentDto){

        Comment comment = commentMapper.mapToComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return commentMapper.mapToCommentDto(newComment);
    }

}
