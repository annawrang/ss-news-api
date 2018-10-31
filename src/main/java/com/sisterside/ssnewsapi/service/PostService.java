package com.sisterside.ssnewsapi.service;

import com.sisterside.ssnewsapi.domain.Comment;
import com.sisterside.ssnewsapi.domain.Post;
import com.sisterside.ssnewsapi.domain.PostLike;
import com.sisterside.ssnewsapi.domain.User;
import com.sisterside.ssnewsapi.exception.NotFoundException;
import com.sisterside.ssnewsapi.repository.PostRepository;
import com.sisterside.ssnewsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLogic postLogic;
    private final UserLogic userLogic;

    @Autowired
    public PostService(PostRepository postRepository, PostLogic postLogic,
                       UserLogic userLogic, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postLogic = postLogic;
        this.userLogic = userLogic;
        this.userRepository = userRepository;
    }

    public List<Post> getPosts(int page, int numbersPerPage) {
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable paging = new PageRequest(0, 10);
        Page posts = postRepository.findAll(paging);
        return posts.getContent();
    }


    public Post saveNewPost(String userNumber, Post post) {
        User user = userRepository.findByUserNumber(userNumber).orElseThrow(NotFoundException::new);
        post = postLogic.setPostCreationTime(post);
        post.setPostNumber(UUID.randomUUID().toString());
        post.setUser(user);
        post = postRepository.save(post);
        return post;
    }

    public Post saveNewPostLike(String postNumber, String userNumber) {
        User user = userRepository.findByUserNumber(userNumber).orElseThrow(NotFoundException::new);
        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        PostLike newLike = new PostLike(user, UUID.randomUUID().toString());
        post.getLikes().add(newLike);
        post = postRepository.save(post);
        return post;
    }


    public void deletePostLike(String postNumber, String likeNumber) {
        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        PostLike like = post.getLikes().stream().filter(l -> l.getPostLikeNumber().equals(likeNumber)).findAny().orElseThrow(NotFoundException::new);
        post.getLikes().remove(like);
        post = postRepository.save(post);
    }


    public Post editPost(String postNumber, String userNumber, Post newPost) {
        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        post.setEdited(true);
        post.setText(newPost.getText());
        postRepository.save(post);
        return post;
    }


    public Comment createPostComment(String postNumber, String userNumber, Comment newComment) {
        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        post.getComments().add(newComment);
        post = postRepository.save(post);
        return newComment;
    }


    public void deletePost(String postNumber) {
        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        postRepository.delete(post);
    }


    public Post getPost(String postNumber) {
        return postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
    }

    //    public void createPostCommentReply(String commentNumber, String userNumber, String newComment) {
//        CommentReplyDTO reply = postLogic.createPostCommentReply(commentNumber, userNumber, newComment);
//        commentReplyRepository.save(reply);
//    }
//
//
//    public void createPostCommentLike(String userNumber, String postNumber, String commentNumber) {
//        userLogic.validateUserExists(userNumber);
//        postLogic.validatePostExists(postNumber);
//        postLogic.validateCommentExists(commentNumber);
//        if (!postLogic.doesCommentLikeExists(commentNumber, userNumber)) {
//            CommentLikeDTO newLike = postLogic.createCommentLike(userNumber, commentNumber);
//            commentLikeRepository.save(newLike);
//        }
//    }
//
//    public void deletePostCommentLike(String userNumber, String postNumber, String commentNumber, String commentLikeNumber) {
//        userLogic.validateUserExists(userNumber);
//        postLogic.validatePostExists(postNumber);
//        postLogic.validateCommentExists(commentNumber);
//        if (postLogic.doesCommentLikeExists(commentNumber, userNumber)) {
//            deleteCommentLike(commentNumber, userNumber);
//        }
//    }
//
//    private void deleteCommentLike(String commentNumber, String userNumber) {
//        CommentLikeDTO like = postLogic.validateCommentLikeExists(commentNumber, userNumber);
//        commentLikeRepository.delete(like);
//    }
//
    public void deletePostComment(String postNumber, String commentNumber) {

        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        Comment comment = post.getComments().stream().filter(c -> c.getCommentNumber().equals(commentNumber)).findAny().orElseThrow(NotFoundException::new);
        post.getComments().remove(comment);
        post = postRepository.save(post);
    }

    public void updatePostComment(String postNumber, String commentNumber, Comment newComment) {
        Post post = postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
        Comment oldComment = post.getComments().stream()
                .filter(c -> c.getCommentNumber().equals(commentNumber))
                .findAny().orElseThrow(NotFoundException::new);
        post.getComments().remove(oldComment);
        post.getComments().add(newComment);
        post = postRepository.save(post);
    }
//
//    private void deleteReplyLike(String replyNumber, String userNumber) {
//        CommentReplyLikeDTO like = replyLikeRepository.findByCommentReplyNumber(replyNumber).get();
//        replyLikeRepository.delete(like);
//    }
//
//    public void updatePostCommentReply(String commentNumber, String userNumber, String newComment) {
//    }
//
//    public void deletePostCommentReply(String commentNumber, String userNumber, String newComment) {
//    }
//
//    public void createPostCommentReplyLike(String postNumber, String commentNumber, String commentReplyNumber, String userNumber) {
//    }
//
//    public void deletePostCommentReplyLike(String postNumber, String commentNumber, String commentReplyNumber, String userNumber, String likeNumber) {
//    }
}
