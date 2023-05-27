package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.payload.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "postId", target = "post.id")
    Comment mapToComment(CommentDto commentDto);

    @Mapping(source = "post.id", target = "postId")
    CommentDto mapToCommentDto(Comment comment);

    List<CommentDto> mapToListCommentDto(Set<Comment> comments);

    @Mapping(target = "post", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comment.id", source = "postId")
    Comment mapToEntity(CommentDto commentDTO, @MappingTarget Comment comment);

}
