package com.temx.security.comments;

import com.temx.security.customers.Customer;
import com.temx.security.customers.CustomerRepository;
import com.temx.security.exception.NotFoundException;
import com.temx.security.exception.RequestBodyRequired;
import com.temx.security.posts.PostRepository;
import com.temx.security.posts.Posts;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CustomerRepository customerRepository;


    private final static Logger LOGGER = LoggerFactory.getLogger(CommentService.class);



//    public Comments createComment(Comments comment) {
//        if (comment == null) {
//            throw new RequestBodyRequired("Comment object is required");
//        }
//
//        Customer customer = comment.getCustomer();
//        Posts post = comment.getPost();
//
//        if (customer == null || post == null) {
//            throw new RequestBodyRequired("Customer and Post objects are required");
//        }
//
//        Long customerId = customer.getId();  // Change to getCustomerId
//        Long postId = post.getId();  // Change to getId
//
//        if (customerId == null || postId == null) {
//            throw new RequestBodyRequired("Customer and Post IDs are required");
//        }
//
//        // Load the associated customer and post based on the provided IDs
//        Customer loadedCustomer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new NotFoundException("No customer found with ID: " + customerId));
//        Posts loadedPost = postRepository.findById(postId)
//                .orElseThrow(() -> new NotFoundException("No post found with ID: " + postId));
//
//        // Ensure that both customer and post exist
//        if (loadedCustomer != null && loadedPost != null) {
//            comment.setCustomer(loadedCustomer);
//            comment.setPost(loadedPost);
//            return commentRepository.save(comment);
//        } else {
//            throw new RequestBodyRequired("Customer and Post not found");
//        }
//
//    }


    @Transactional
    public Comments addComment(CommentDTO request) {
        // Retrieve customer and post entities
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + request.getCustomerId()));
        Posts post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + request.getPostId()));

        // Create a new Comment entity
        Comments comment = new Comments();
        comment.setText(request.getText());
        comment.setCustomer(customer);
        comment.setPost(post);

        // Save the comment
        return commentRepository.save(comment);
    }
    public List<CommentDTO> getAllComments() {
        List<Comments> comments = commentRepository.findAll();
        List<CommentDTO> commentDetailsList = new ArrayList<>();

        for (Comments comment : comments) {
            CommentDTO commentDetails = new CommentDTO(comment.getId(), comment.getPost().getId(), comment.getCustomer().getId(), comment.getText(),comment.getCreatedAt());
            commentDetailsList.add(commentDetails);
        }
        return commentDetailsList;
    }

    public Optional<Comments> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comments updateComment(Long id, Comments updatedComment) {
        Optional<Comments> existingComment = commentRepository.findById(id);

        if (existingComment.isPresent()) {
            Comments commentToUpdate = existingComment.get();
            if(updatedComment.getText()!=null && !updatedComment.getText().isEmpty()){
                commentToUpdate.setText(updatedComment.getText());
            }
            if(updatedComment.getPost()!=null){
                commentToUpdate.setPost(updatedComment.getPost());
            }
            if(updatedComment.getCustomer()!=null){
                commentToUpdate.setCustomer(updatedComment.getCustomer());
            }

            commentToUpdate.setId(id);
            return commentRepository.save(commentToUpdate);
        } else {
            throw new NotFoundException("Comment with ID " + id + " not found");
        }
    }



    public Comments deleteComment(Long id) {
        Optional<Comments> existingComment = commentRepository.findById(id);

        if (existingComment.isPresent()) {
            commentRepository.deleteById(id);
            return existingComment.get();
        } else {
            throw new NotFoundException("Comment with ID " + id + " not found");
        }
    }


    public List<Comments> getCommentsByPostId(Long postId) {
      return commentRepository.findByPostId(postId);
    }

    public List<Comments> getCommentsByCustomerId(Long postId) {
       return commentRepository.findByCustomerId(postId);
    }

}
