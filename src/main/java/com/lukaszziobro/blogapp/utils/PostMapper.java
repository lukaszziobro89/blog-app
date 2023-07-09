package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.payload.CommentDto;
import com.lukaszziobro.blogapp.payload.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface PostMapper {

    @Mapping(target = "category", ignore = true)
    Post mapToPost(PostDto postDto);

    PostDto mapToPostDto(Post post);

    Set<Comment> mapToComments(Set<CommentDto> commentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "category", ignore = true)
    Post mapToPostById(PostDto postDto, @MappingTarget Post post);

}
