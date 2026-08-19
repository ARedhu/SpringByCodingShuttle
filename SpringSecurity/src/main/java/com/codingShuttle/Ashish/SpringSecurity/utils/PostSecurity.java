package com.codingShuttle.Ashish.SpringSecurity.utils;

import com.codingShuttle.Ashish.SpringSecurity.dto.PostDTO;
import com.codingShuttle.Ashish.SpringSecurity.entities.User;
import com.codingShuttle.Ashish.SpringSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}

// Remember we should use a best combination of both "Request Matchers" and "Security Methods" that we just learnt.
// We should use "Request Matchers" to stop unauthorized users to access some REST API's end points. Then we use security methods like @PreAuthorize to enforce specific business roles to access some controllers.
