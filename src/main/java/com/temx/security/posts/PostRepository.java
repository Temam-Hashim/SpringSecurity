package com.temx.security.posts;

import com.temx.security.comments.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts,Long> {
//    @Query("SELECT p FROM Posts p INNER JOIN Comments c ON c.post.id=p.id")
//    List<Posts> findAllWithComments();
}
