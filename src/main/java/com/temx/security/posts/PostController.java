package com.temx.security.posts;

import com.temx.security.exception.DeletedException;
import com.temx.security.exception.NotFoundException;
import com.temx.security.upload.UploadImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;
    private final UploadImageService uploadImageService;


    @GetMapping
    public List<Posts> getPosts(){
        return postService.getPosts();
    }


    @GetMapping(path = "{postId}")
    public Optional<Posts> getPost(@PathVariable("postId") long postId){
        return postService.getPost(postId);
    }

    @PostMapping()
    public ResponseEntity<Posts> createPost(@RequestParam("title") String title,
                                            @RequestParam("description") String description,
                                            @RequestParam("tags") List<String> tags,
                                            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        CreatePostRequest request = new CreatePostRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setTags(tags);
        request.setImageFile(imageFile);

        Posts post = postService.createPost(request);
        return ResponseEntity.ok(post);
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
