package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Comment;
import com.lukaszziobro.blogapp.payload.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(ignore = true, target = "post")
    Comment mapToComment(CommentDto commentDto);

    CommentDto mapToCommentDto(Comment comment);
}
