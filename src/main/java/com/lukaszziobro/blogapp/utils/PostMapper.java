package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.payload.PostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post mapToPost(PostDto postDto);
    PostDto mapToPostDto(Post post);
}
