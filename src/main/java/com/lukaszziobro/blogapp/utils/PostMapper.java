package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.payload.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(ignore = true, target = "id")
    Post mapToPost(PostDto postDto);

    @Mapping(source = "postId", target = "id")
    Post mapToPostById(PostDto postDto, long postId);

    PostDto mapToPostDto(Post post);

}
