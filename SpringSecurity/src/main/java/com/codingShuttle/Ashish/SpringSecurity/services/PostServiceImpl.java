package com.codingShuttle.Ashish.SpringSecurity.services;

import com.codingShuttle.Ashish.SpringSecurity.dto.PostDTO;
import com.codingShuttle.Ashish.SpringSecurity.entities.PostEntity;
import com.codingShuttle.Ashish.SpringSecurity.entities.User;
import com.codingShuttle.Ashish.SpringSecurity.exceptions.ResourceNotFoundException;
import com.codingShuttle.Ashish.SpringSecurity.repositories.PostRepository;
import com.codingShuttle.Ashish.SpringSecurity.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor // It itself write the constructor for all the required variables like final variable here.
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .toList();
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("user {}", user);

        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id="+postId));

        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
        PostEntity olderPost = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id="+postId));
        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost); // Here, we want to merge the changes of incoming data to already existing data.
        PostEntity savedPostEntity = postRepository.save(olderPost);
        return modelMapper.map(savedPostEntity, PostDTO.class);
    }
}
