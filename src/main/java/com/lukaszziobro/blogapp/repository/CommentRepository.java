package com.lukaszziobro.blogapp.repository;

import com.lukaszziobro.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEmail(String email);
}
