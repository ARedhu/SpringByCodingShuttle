package com.codingShuttle.Ashish.SpringSecurity.services;

import com.codingShuttle.Ashish.SpringSecurity.dto.PostDTO;
import java.util.List;

// @Service // Remember we can't use it here. Because we can't create bean/object of interfaces.
public interface PostService {

    List<PostDTO> getAllPosts();
    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);

}
