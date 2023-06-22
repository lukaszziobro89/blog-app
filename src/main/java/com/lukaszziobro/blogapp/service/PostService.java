package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Post;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.PostDto;
import com.lukaszziobro.blogapp.repository.PostRepository;
import com.lukaszziobro.blogapp.utils.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<PostDto> getAllPosts(Pageable pageable) {
        List<PostDto> posts = postRepository.findAll(pageable).stream()
                .map(p -> postMapper.mapToPostDto(p)).toList();
        return new PageImpl<>(posts);
    }

    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return postMapper.mapToPostDto(post);
    }

    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Post toBeSaved = postMapper.mapToPostById(postDto, post);
        Post postSaved = postRepository.save(toBeSaved);
        return postMapper.mapToPostDto(postSaved);
    }

    public void deletePostById(long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
