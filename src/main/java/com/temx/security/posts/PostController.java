package com.temx.security.posts;

import com.temx.security.exception.DeletedException;
import com.temx.security.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;


    @GetMapping
    public List<Posts> getPosts(){
        return postService.getPosts();
    }


    @GetMapping(path = "{postId}")
    public Optional<Posts> getPost(@PathVariable("postId") long postId){
        return postService.getPost(postId);
    }

    @PostMapping
    public ResponseEntity<Posts> createPost(@Valid @RequestBody Posts post){
        Posts createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping(path = "{postId}")
    public Posts updatePost(@RequestBody Posts posts, @PathVariable("postId") long postId){
        return postService.updatePost(postId, posts);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") long postId) {
        Posts  deletedPost = postService.deletePost(postId);
        if(deletedPost!=null){
            throw new DeletedException("Post with Id "+postId+" deleted Successfully");
        }else{
            throw new NotFoundException("No post found with Id "+postId);
        }
    }
}
