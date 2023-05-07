package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.repository.PostRepository;
import com.lukaszziobro.blogapp.utils.PostMapper;
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
    private PostMapper postMapper;

    public PostDto createPost(PostDto postDto) {
        Post post = postMapper.mapToPost(postDto);
        Post saved = postRepository.save(post);
        return postMapper.mapToPostDto(saved);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(c -> postMapper.mapToPostDto(c))
                .collect(Collectors.toList());
    }

    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return postMapper.mapToPostDto(post);
    }

    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postMapper.mapToPostById(postDto, id);
        Post postSaved = postRepository.save(post);
        return postMapper.mapToPostDto(postSaved);
    }

    public void deletePostById(long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
