package com.gdsc.blog.comment.repository;

import com.gdsc.blog.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByArticleIdx(Long articleIdx);
}
