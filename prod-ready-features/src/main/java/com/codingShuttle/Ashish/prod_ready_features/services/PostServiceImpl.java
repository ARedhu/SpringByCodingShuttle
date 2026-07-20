package com.codingShuttle.Ashish.prod_ready_features.services;

import com.codingShuttle.Ashish.prod_ready_features.dto.PostDTO;
import com.codingShuttle.Ashish.prod_ready_features.entities.PostEntity;
import com.codingShuttle.Ashish.prod_ready_features.exceptions.ResourceNotFoundException;
import com.codingShuttle.Ashish.prod_ready_features.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // It itself write the constructor for all the required variables like final variable here.
public class PostServiceImpl implements PostService{

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
