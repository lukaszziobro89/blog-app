package com.lukaszziobro.blogapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private ObjectMapper objectMapper;

    public PostDto createPost(PostDto postDto) {
        Post post = objectMapper.convertValue(postDto, Post.class);
        Post saved = postRepository.save(post);
        return objectMapper.convertValue(saved, PostDto.class);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(c -> objectMapper.convertValue(c, PostDto.class))
                .collect(Collectors.toList());
    }

    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return objectMapper.convertValue(post, PostDto.class);
    }
}
