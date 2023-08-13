package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.BlogAPIException;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.repository.CommentRepository;
import com.lukaszziobro.blogapp.repository.PostRepository;
import com.lukaszziobro.blogapp.utils.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CommentDto> getCommentsByPostId(long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        return commentMapper.mapToListCommentDto(post.getComments());
    }

//    TODO public getCommentById()

    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto){

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));
        Comment commentToUpdate = commentMapper.mapToEntity(commentDto, comment);

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        Comment updatedComment = commentRepository.save(commentToUpdate);

        return commentMapper.mapToCommentDto(updatedComment);
    }

    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment commentToDelete = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!commentToDelete.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.delete(commentToDelete);
    }

    public List<CommentDto> getCommentsByEmail(String email){
        return commentRepository.findByEmail(email)
                .stream().map(commentMapper::mapToCommentDto).collect(Collectors.toList());
    }

}
