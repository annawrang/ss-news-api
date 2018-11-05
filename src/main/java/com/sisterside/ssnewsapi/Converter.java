package com.sisterside.ssnewsapi;

import com.sisterside.ssnewsapi.domain.Post;
import com.sisterside.ssnewsapi.dto.PostDTO;

public interface Converter {
    PostDTO convert(Post post);

    Post convert(PostDTO postDTO);
}
