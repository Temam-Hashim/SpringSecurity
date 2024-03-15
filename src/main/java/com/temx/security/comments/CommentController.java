package com.temx.security.comments;


import com.temx.security.customers.*;
import com.temx.security.customers.CustomerRepository;
import com.temx.security.exception.DeletedException;
import com.temx.security.exception.NotFoundException;
import com.temx.security.posts.PostRepository;
import com.temx.security.posts.Posts;
import com.temx.security.comments.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final com.temx.security.comments.CommentRepository commentRepository;
    private final CustomerRepository customerRepository;
    private final PostRepository postRepository;


    @PostMapping
    public ResponseEntity<Comments> addComment(@Valid @RequestBody CommentDTO request) {
        Comments savedComment = commentService.addComment(request);
        return ResponseEntity.ok(savedComment);
    }


    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comments> getCommentById(@PathVariable("id") Long id) {
        Optional<Comments> comment = commentService.getCommentById(id);
//        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
//        } else {
//            throw new NotFoundException("No comment found with Id: "+id);
//        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Comments> updateComment(@PathVariable("id") Long id, @RequestBody Comments updatedComment) {
        Comments updated = commentService.updateComment(id, updatedComment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        Comments deletedComment = commentService.deleteComment(id);
        if (deletedComment != null) {
            throw new DeletedException("comment with Id "+id+" deleted");
        } else {
            throw new NotFoundException("Customer Not found");
        }
    }


    @GetMapping("/posts/{postId}")
    public List<Comments> getCommentsByPostId(@PathVariable("postId") long postId){
         List<Comments> comments = commentService.getCommentsByPostId(postId);
         if(comments.isEmpty() || comments==null){
             throw new NotFoundException("Comment with Post ID " + postId + " not found");
         }else{
             return  comments;
         }

    }

    @GetMapping("/customer/{customerId}")
    public List<Comments> getCommentsByCustomerId(@PathVariable("customerId") long customerId){
        List<Comments> comments = commentService.getCommentsByCustomerId(customerId);
        if(comments.isEmpty() || comments==null){
            throw new NotFoundException("Comment with Customer ID " + customerId + " not found");
        }else{
            return  comments;
        }

    }

}
