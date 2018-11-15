package com.sisterside.ssnewsapi.service;

import com.sisterside.ssnewsapi.ModelMapperConverter;
import com.sisterside.ssnewsapi.domain.Comment;
import com.sisterside.ssnewsapi.domain.Like;
import com.sisterside.ssnewsapi.domain.Post;
import com.sisterside.ssnewsapi.dto.PostDTO;
import com.sisterside.ssnewsapi.exception.NotFoundException;
import com.sisterside.ssnewsapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapperConverter converter;

    @Autowired
    public PostService(PostRepository postRepository,
                       ModelMapperConverter converter) {
        this.postRepository = postRepository;
        this.converter = converter;
    }

    // Problem with paging and converting to Post..
    public List<Post> getPosts(int page, int numbersPerPage) {
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable paging = new PageRequest(0, 10);
        Page posts = postRepository.findAll(paging);
        return posts.getContent();
    }


    public Post saveNewPost(Post post) {
        post.generateCreationTime();
        post.setPostNumber(UUID.randomUUID().toString());
        post.setComments(new ArrayList<>());
        post.setLikes(new ArrayList<>());

        postRepository.save(converter.convert(post));
        return post;
    }

    public Like saveNewPostLike(String postNumber, Like like) {
        PostDTO post = getPostDTO(postNumber);
        like.setLikeNumber(UUID.randomUUID().toString());
        post.getLikes().add(like);
        post = postRepository.save(post);
        return like;
    }


    public void deletePostLike(String postNumber, String likeNumber) {
        PostDTO post = getPostDTO(postNumber);
        Like like = getPostLike(post, likeNumber);
        post.getLikes().remove(like);
        postRepository.save(post);
    }


    public Post editPost(String postNumber, Post newPost) {
        PostDTO post = getPostDTO(postNumber);
        post.setEdited(true);
        post.setText(newPost.getText());
        postRepository.save(post);
        return converter.convert(post);
    }


    public Comment createPostComment(String postNumber, Comment newComment) {
        PostDTO post = getPostDTO(postNumber);
        newComment.setCommentNumber(UUID.randomUUID().toString());
        newComment.generateCreationTime();
        newComment.setLikes(new ArrayList<>());
        newComment.setCommentReplies(new ArrayList<>());
        post.getComments().add(newComment);
        post = postRepository.save(post);
        return newComment;
    }


    public void deletePost(String postNumber) {
        PostDTO post = getPostDTO(postNumber);
        postRepository.delete(post);
    }


    public Post getPostByPostNumber(String postNumber) {
        return converter.convert(getPostDTO(postNumber));
    }

    public Comment createPostCommentReply(String postNumber, String commentNumber, Comment newComment) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        newComment.setLikes(new ArrayList<>());
        newComment.setCommentNumber(UUID.randomUUID().toString());
        comment.getCommentReplies().add(newComment);
        postRepository.save(post);
        return newComment;
    }


    public Like createPostCommentLike(String postNumber, String commentNumber, Like like) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        like.setLikeNumber(UUID.randomUUID().toString());
        comment.getLikes().add(like);
        postRepository.save(post);
        return like;
    }

    public void deletePostCommentLike(String postNumber, String commentNumber, String commentLikeNumber) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        Like like = getCommentLike(comment, commentLikeNumber);
        comment.getLikes().remove(like);
        postRepository.save(post);
    }

    public void deletePostComment(String postNumber, String commentNumber) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        post.getComments().remove(comment);
        postRepository.save(post);
    }

    public Comment updatePostComment(String postNumber, String commentNumber, Comment newComment) {
        PostDTO post = getPostDTO(postNumber);
        Comment oldComment = getComment(post, commentNumber);
        oldComment.setText(newComment.getText());
        postRepository.save(post);
        return oldComment;
    }

    public Comment updatePostCommentReply(String postNumber, String commentNumber, String commentReplyNumber, Comment newComment) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        Comment commentReply = getCommentReply(comment, commentReplyNumber);
        commentReply.setText(newComment.getText());
        postRepository.save(post);
        return commentReply;
    }

    public void deletePostCommentReply(String postNumber, String commentNumber, String commentReplyNumber) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        Comment commentReply = getCommentReply(comment, commentReplyNumber);
        comment.getCommentReplies().remove(commentReply);
        postRepository.save(post);
    }

    public Like createPostCommentReplyLike(String postNumber, String commentNumber, String commentReplyNumber, Like like) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        Comment commentReply = getCommentReply(comment, commentReplyNumber);
        like.setLikeNumber(UUID.randomUUID().toString());
        commentReply.getLikes().add(like);
        postRepository.save(post);
        return like;
    }

    public void deletePostCommentReplyLike(String postNumber, String commentNumber, String commentReplyNumber, String likeNumber) {
        PostDTO post = getPostDTO(postNumber);
        Comment comment = getComment(post, commentNumber);
        Comment commentReply = getCommentReply(comment, commentReplyNumber);
        Like like = getReplyLike(commentReply, likeNumber);

        commentReply.getLikes().remove(like);
        postRepository.save(post);
    }

    private PostDTO getPostDTO(String postNumber) {
        return postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
    }

    private Like getReplyLike(Comment commentReply, String likeNumber) {
        return commentReply.getLikes().stream()
                .filter(l -> l.getLikeNumber().equals(likeNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private Comment getCommentReply(Comment comment, String commentReplyNumber) {
        return comment.getCommentReplies().stream()
                .filter(cr -> cr.getCommentNumber().equals(commentReplyNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private Comment getComment(PostDTO post, String commentNumber) {
        return post.getComments().stream()
                .filter(c -> c.getCommentNumber().equals(commentNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private Like getCommentLike(Comment comment, String commentLikeNumber) {
        return comment.getLikes().stream()
                .filter(l -> l.getLikeNumber().equals(commentLikeNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private Like getPostLike(PostDTO post, String likeNumber) {
        return post.getLikes().stream()
                .filter(l -> l.getLikeNumber().equals(likeNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }
}
