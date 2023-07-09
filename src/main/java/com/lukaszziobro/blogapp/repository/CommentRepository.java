package com.lukaszziobro.blogapp.repository;

import com.lukaszziobro.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
