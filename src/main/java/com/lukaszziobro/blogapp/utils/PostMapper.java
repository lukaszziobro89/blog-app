package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.payload.PostDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface PostMapper {

    Post mapToPost(PostDto postDto);

    Post mapToPostById(PostDto postDto, long postId);

    PostDto mapToPostDto(Post post);

    Set<Comment> mapToComments(Set<CommentDto> commentDto);

}
