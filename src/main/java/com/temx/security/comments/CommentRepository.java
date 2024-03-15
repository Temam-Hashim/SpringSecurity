package com.temx.security.comments;

import com.temx.security.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByPostId(Long postId);

    List<Comments> findByCustomerId(Long customerId);

}
