package com.sisterside.ssnewsapi.controller;


import com.sisterside.ssnewsapi.domain.Comment;
import com.sisterside.ssnewsapi.domain.Post;
import com.sisterside.ssnewsapi.domain.PostLike;
import com.sisterside.ssnewsapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService service) {
        this.postService = service;
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(postValidator);
//    }

    @GetMapping
    public ResponseEntity getPosts(@RequestParam(defaultValue = "1", required = false) int page,
                                        @RequestParam(defaultValue = "10", required = false) int numbersPerPage) {
        List<Post> posts = postService.getPosts(page, numbersPerPage);
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createNewPost(@Validated @RequestBody Post post) {
        postService.saveNewPost(post);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{postNumber}")
    public ResponseEntity getPost(@PathVariable String postNumber) {
        return new ResponseEntity(postService.getPost(postNumber), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{postNumber}")
    public ResponseEntity deletePost(@PathVariable String postNumber) {
        postService.deletePost(postNumber);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{postNumber}")
    public ResponseEntity editPost(@Valid @PathVariable String postNumber, @RequestBody Post newPost) {
        postService.editPost(postNumber, newPost);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/{postNumber}/likes")
    public ResponseEntity createNewPostLike(@PathVariable String postNumber, @RequestBody PostLike like) {
        postService.saveNewPostLike(postNumber, like);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{postNumber}/likes/{likeNumber}")
    public ResponseEntity deletePostLike(@PathVariable String postNumber, @PathVariable String likeNumber, @RequestParam String userNumber) {
       postService.deletePostLike(postNumber, likeNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/{postNumber}/comments")
    public ResponseEntity createNewPostComment(@PathVariable String postNumber, @RequestBody Comment newComment) {
       postService.createPostComment(postNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{postNumber}/comments/{commentNumber}")
    public ResponseEntity deletePostComment(@PathVariable String postNumber, @PathVariable String commentNumber) {
        postService.deletePostComment(postNumber, commentNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{postNumber}/comments/{commentNumber}")
    public ResponseEntity updatePostComment(@PathVariable String postNumber, @PathVariable String commentNumber, @RequestBody Comment newComment) {
        postService.updatePostComment(postNumber, commentNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "/{postNumber}/comments/{commentNumber}/likes")
    public ResponseEntity saveNewCommentLike(@PathVariable String postNumber,
                                             @PathVariable String commentNumber, @RequestParam String userNumber) {


//        postService.createPostCommentLike(userNumber, postNumber, commentNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{postNumber}/comments/{commentNumber}/likes/{likeNumber}")
    public ResponseEntity deleteCommentLike(@PathVariable String postNumber,
                                            @PathVariable String commentNumber,
                                            @PathVariable String likeNumber, @RequestParam String userNumber) {

//        postService.deletePostCommentLike(userNumber, postNumber, commentNumber, likeNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "/{postNumber}/comments/{commentNumber}/replies")
    public ResponseEntity createNewPostCommentReply(@PathVariable String postNumber,
                                                    @PathVariable String commentNumber,
                                                    @RequestBody String newComment, @RequestParam String userNumber) {

//        postService.createPostCommentReply(commentNumber, userNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{postNumber}/comments/{commentNumber}/replies/{commentReplyNumber}")
    public ResponseEntity updatePostCommentReply(@PathVariable String postNumber,
                                                 @PathVariable String commentNumber,
                                                 @PathVariable String commentReplyNumber,
                                                 @RequestBody String newComment, @RequestParam String userNumber) {

//        postService.updatePostCommentReply(commentNumber, userNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{postNumber}/comments/{commentNumber}/replies/{commentReplyNumber}")
    public ResponseEntity deletePostCommentReply(@PathVariable String postNumber,
                                                 @PathVariable String commentNumber,
                                                 @PathVariable String commentReplyNumber,
                                                 @RequestBody String newComment, @RequestParam String userNumber) {

//        postService.deletePostCommentReply(commentNumber, userNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "/{postNumber}/comments/{commentNumber}/replies/{commentReplyNumber}/likes")
    public ResponseEntity createPostCommentReplyLike(@PathVariable String postNumber,
                                                     @PathVariable String commentNumber,
                                                     @PathVariable String commentReplyNumber,
                                                     @RequestBody String newComment, @RequestParam String userNumber) {

//        postService.createPostCommentReplyLike(postNumber, commentNumber, commentReplyNumber, userNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{postNumber}/comments/{commentNumber}/replies/{commentReplyNumber}/likes/{likeNumber}")
    public ResponseEntity deletePostCommentReplyLike(@PathVariable String postNumber,
                                                     @PathVariable String commentNumber,
                                                     @PathVariable String commentReplyNumber,
                                                     @PathVariable String likeNumber, @RequestParam String userNumber) {

//        postService.deletePostCommentReplyLike(postNumber, commentNumber, commentReplyNumber, userNumber, likeNumber);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}