package com.sisterside.ssnewsapi;

import com.sisterside.ssnewsapi.domain.Post;
import com.sisterside.ssnewsapi.dto.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConverter implements Converter{
    private ModelMapper modelMapper;

    public ModelMapperConverter() {
        modelMapper = new ModelMapper();
    }

    @Override
    public PostDTO convert(Post post) {
       return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public Post convert(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }
}
