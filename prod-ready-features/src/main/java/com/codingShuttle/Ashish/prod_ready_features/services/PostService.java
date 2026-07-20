package com.codingShuttle.Ashish.prod_ready_features.services;

import com.codingShuttle.Ashish.prod_ready_features.dto.PostDTO;
import java.util.List;

// @Service // Remember we can't use it here. Because we can't create bean/object of interfaces.
public interface PostService {

    List<PostDTO> getAllPosts();
    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
