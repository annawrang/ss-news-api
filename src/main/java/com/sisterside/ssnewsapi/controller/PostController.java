package com.sisterside.ssnewsapi.controller;

import com.sisterside.ssnewsapi.config.CustomAuthenticationManager;
import com.sisterside.ssnewsapi.resource.CommentResource;
import com.sisterside.ssnewsapi.resource.LikeResource;
import com.sisterside.ssnewsapi.resource.PostResource;
import com.sisterside.ssnewsapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity getPosts(@RequestParam(defaultValue = "1", required = false) int page,
                                   @RequestParam(defaultValue = "10", required = false) int numbersPerPage) {
        List<PostResource> posts = postService.getPosts(page, numbersPerPage);
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createNewPost(@Validated @RequestBody PostResource post) {
        post = postService.saveNewPost(post);
        return new ResponseEntity(post, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{postNumber}")
    public ResponseEntity getPost(@PathVariable String postNumber) {
        return new ResponseEntity(postService.getPostByPostNumber(postNumber), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{postNumber}")
    public ResponseEntity deletePost(@PathVariable String postNumber) {
        postService.deletePost(postNumber);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{postNumber}")
    public ResponseEntity editPost(@Valid @PathVariable String postNumber, @RequestBody PostResource newPost) {
        newPost = postService.editPost(postNumber, newPost);
        return new ResponseEntity(newPost, HttpStatus.OK);
    }

    @PostMapping(value = "/{postNumber}/likes")
    public ResponseEntity createNewPostLike(@PathVariable String postNumber, @RequestBody LikeResource like) {
        like = postService.saveNewPostLike(postNumber, like);
        return new ResponseEntity(like, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{postNumber}/likes/{likeNumber}")
    public ResponseEntity deletePostLike(@PathVariable String postNumber, @PathVariable String likeNumber, @RequestParam String userNumber) {
        postService.deletePostLike(postNumber, likeNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/{postNumber}/comments")
    public ResponseEntity createNewPostComment(@PathVariable String postNumber, @RequestBody CommentResource newComment) {
        newComment = postService.createPostComment(postNumber, newComment);
        return new ResponseEntity(newComment, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{postNumber}/comments/{commentNumber}")
    public ResponseEntity deletePostComment(@PathVariable String postNumber, @PathVariable String commentNumber) {
        postService.deletePostComment(postNumber, commentNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{postNumber}/comments/{commentNumber}")
    public ResponseEntity updatePostComment(@PathVariable String postNumber, @PathVariable String commentNumber, @RequestBody CommentResource newComment) {
        newComment = postService.updatePostComment(postNumber, commentNumber, newComment);
        return new ResponseEntity(newComment, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{postNumber}/comments/{commentNumber}/likes")
    public ResponseEntity saveNewCommentLike(@PathVariable String postNumber,
                                             @PathVariable String commentNumber, @RequestBody LikeResource like) {
        like = postService.createPostCommentLike(postNumber, commentNumber, like);
        return new ResponseEntity(like, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{postNumber}/comments/{commentNumber}/likes/{likeNumber}")
    public ResponseEntity deleteCommentLike(@PathVariable String postNumber,
                                            @PathVariable String commentNumber,
                                            @PathVariable String likeNumber) {
        postService.deletePostCommentLike(postNumber, commentNumber, likeNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
