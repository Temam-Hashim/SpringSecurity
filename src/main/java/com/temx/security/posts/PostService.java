package com.temx.security.posts;

import com.temx.security.exception.NotFoundException;
import com.temx.security.exception.RequestBodyRequired;
import com.temx.security.user.User;
import com.temx.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public List<Posts> getPosts(){
        if(postRepository.findAll().isEmpty()){
            throw new NotFoundException("Posts is Empty");
        }
        return postRepository.findAll();
    }

    public Optional<Posts> getPost(long postId){
        Optional<Posts> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new NotFoundException("No post found with Id:"+postId);
        }

        return post;
    }

    public Posts createPost(Posts post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No User found with ID: " + email));
        System.out.println(user);

        post.setUser(user);

        return postRepository.save(post);
    }

    public Posts updatePost(long postId,Posts updatedPost) {
        Optional<Posts> originalPost = postRepository.findById(postId);

        if (originalPost.isPresent()) {
            Posts toBeUpdated = originalPost.get();

            if (!updatedPost.getTitle().isEmpty() && updatedPost.getTitle() != null) {
                toBeUpdated.setTitle(updatedPost.getTitle());
            }
            if (!updatedPost.getDescription().isEmpty() && updatedPost.getDescription() != null) {
                toBeUpdated.setDescription(updatedPost.getDescription());
            }
            if (!updatedPost.getTags().isEmpty() && updatedPost.getTags() != null) {
                toBeUpdated.setTags(updatedPost.getTags());
            }
            if (!updatedPost.getImg().isEmpty() && updatedPost.getImg() != null) {
                toBeUpdated.setImg(updatedPost.getImg());
            }

            return postRepository.save(toBeUpdated);
        }
        else {
            // Handle the case where the post does not exist
            throw new NotFoundException("Post with Id "+postId+" not found");
        }

    }

    // Update a post by ID
//    public Posts updatePost(long id, Posts updatedPost) {
//        Optional<Posts> existingPost = postRepository.findById(id);
//
//        if (existingPost.isPresent()) {
//            updatedPost.setId(id); // Set the ID of the updated post
//            return postRepository.save(updatedPost);
//        } else {
//            throw new NotFoundException("No Post found with ID: " + id);
//        }
//    }
    // Delete a post by ID
    public Posts deletePost(long id) {
        Optional<Posts> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            postRepository.deleteById(id);
            return existingPost.get();
        } else {
            throw new NotFoundException("No Post found with ID: " + id);
        }
    }
}
