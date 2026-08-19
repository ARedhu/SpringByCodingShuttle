package com.codingShuttle.Ashish.SpringSecurity.controllers;

import com.codingShuttle.Ashish.SpringSecurity.dto.PostDTO;
import com.codingShuttle.Ashish.SpringSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"}) // This is a spring security method that helps us to implement authorization by allowing specific roles to access this function. We can use these annotations anywhere we like. But preferred to use in the controllers only. But this is a very basic method where we can just pass the roles and permissions only.
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
 //   @PreAuthorize("hasRole('USER') OR hasAuthority('POST_VIEW')") // @PreAuthorize is more advance than @Secured.
   @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)") // First call this method before going with the controller.
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @PutMapping("/{postId}")
    public PostDTO updatePost(@RequestBody PostDTO inputPost, @PathVariable Long postId){
        return postService.updatePost(inputPost, postId);
    }
}

//@PreAuthorize("@postSecurity.isOwnerOfPost(#postId)") : Let's understand this.
//We have to pass the bean and it can be passed using "@" symbol. Then the bean name must be same as the file name but the first letter must be small
//Then, we can pass the variable inside of function using symbol "#".

