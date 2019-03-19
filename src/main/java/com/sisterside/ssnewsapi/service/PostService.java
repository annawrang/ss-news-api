package com.sisterside.ssnewsapi.service;

import com.sisterside.ssnewsapi.resource.LikeResource;
import com.sisterside.ssnewsapi.resource.CommentResource;
import com.sisterside.ssnewsapi.resource.PostResource;
import com.sisterside.ssnewsapi.model.Post;
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

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // TODO find a non deprecated PageRequest
    public List<PostResource> getPosts(int page, int numbersPerPage) {
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable paging = new PageRequest(0, 10);
        Page posts = postRepository.findAll(paging);
        return posts.getContent();
    }

    public PostResource saveNewPost(PostResource post) {
        post.generateCreationTime();
        post.setPostNumber(UUID.randomUUID().toString());
        post.setComments(new ArrayList<>());
        post.setLikes(new ArrayList<>());

        postRepository.save(convert(post));
        return post;
    }

    public LikeResource saveNewPostLike(String postNumber, LikeResource like) {
        Post post = getPostDTO(postNumber);
        like.setLikeNumber(UUID.randomUUID().toString());
        post.getLikes().add(like);
        post = postRepository.save(post);
        return like;
    }


    public void deletePostLike(String postNumber, String likeNumber) {
        Post post = getPostDTO(postNumber);
        LikeResource like = getPostLike(post, likeNumber);
        post.getLikes().remove(like);
        postRepository.save(post);
    }


    public PostResource editPost(String postNumber, PostResource newPost) {
        Post post = getPostDTO(postNumber);
        post.setEdited(true);
        post.setText(newPost.getText());
        postRepository.save(post);
        return convert(post);
    }


    public CommentResource createPostComment(String postNumber, CommentResource newComment) {
        Post post = getPostDTO(postNumber);
        newComment.setCommentNumber(UUID.randomUUID().toString());
        newComment.generateCreationTime();
        newComment.setLikes(new ArrayList<>());
        newComment.setCommentReplies(new ArrayList<>());
        post.getComments().add(newComment);
        post = postRepository.save(post);
        return newComment;
    }


    public void deletePost(String postNumber) {
        Post post = getPostDTO(postNumber);
        postRepository.delete(post);
    }

    public PostResource getPostByPostNumber(String postNumber) {
        return convert(getPostDTO(postNumber));
    }

    public CommentResource createPostCommentReply(String postNumber, String commentNumber, CommentResource newComment) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        newComment.setLikes(new ArrayList<>());
        newComment.setCommentNumber(UUID.randomUUID().toString());
        comment.getCommentReplies().add(newComment);
        postRepository.save(post);
        return newComment;
    }

    public LikeResource createPostCommentLike(String postNumber, String commentNumber, LikeResource like) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        like.setLikeNumber(UUID.randomUUID().toString());
        comment.getLikes().add(like);
        postRepository.save(post);
        return like;
    }

    public void deletePostCommentLike(String postNumber, String commentNumber, String commentLikeNumber) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        LikeResource like = getCommentLike(comment, commentLikeNumber);
        comment.getLikes().remove(like);
        postRepository.save(post);
    }

    public void deletePostComment(String postNumber, String commentNumber) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        post.getComments().remove(comment);
        postRepository.save(post);
    }

    public CommentResource updatePostComment(String postNumber, String commentNumber, CommentResource newComment) {
        Post post = getPostDTO(postNumber);
        CommentResource oldComment = getComment(post, commentNumber);
        oldComment.setText(newComment.getText());
        postRepository.save(post);
        return oldComment;
    }

    public CommentResource updatePostCommentReply(String postNumber, String commentNumber, String commentReplyNumber, CommentResource newComment) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        CommentResource commentReply = getCommentReply(comment, commentReplyNumber);
        commentReply.setText(newComment.getText());
        postRepository.save(post);
        return commentReply;
    }

    public void deletePostCommentReply(String postNumber, String commentNumber, String commentReplyNumber) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        CommentResource commentReply = getCommentReply(comment, commentReplyNumber);
        comment.getCommentReplies().remove(commentReply);
        postRepository.save(post);
    }

    public LikeResource createPostCommentReplyLike(String postNumber, String commentNumber, String commentReplyNumber, LikeResource like) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        CommentResource commentReply = getCommentReply(comment, commentReplyNumber);
        like.setLikeNumber(UUID.randomUUID().toString());
        commentReply.getLikes().add(like);
        postRepository.save(post);
        return like;
    }

    public void deletePostCommentReplyLike(String postNumber, String commentNumber, String commentReplyNumber, String likeNumber) {
        Post post = getPostDTO(postNumber);
        CommentResource comment = getComment(post, commentNumber);
        CommentResource commentReply = getCommentReply(comment, commentReplyNumber);
        LikeResource like = getReplyLike(commentReply, likeNumber);

        commentReply.getLikes().remove(like);
        postRepository.save(post);
    }

    private Post getPostDTO(String postNumber) {
        return postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
    }

    private LikeResource getReplyLike(CommentResource commentReply, String likeNumber) {
        return commentReply.getLikes().stream()
                .filter(l -> l.getLikeNumber().equals(likeNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private CommentResource getCommentReply(CommentResource comment, String commentReplyNumber) {
        return comment.getCommentReplies().stream()
                .filter(cr -> cr.getCommentNumber().equals(commentReplyNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private CommentResource getComment(Post post, String commentNumber) {
        return post.getComments().stream()
                .filter(c -> c.getCommentNumber().equals(commentNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private LikeResource getCommentLike(CommentResource comment, String commentLikeNumber) {
        return comment.getLikes().stream()
                .filter(l -> l.getLikeNumber().equals(commentLikeNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private LikeResource getPostLike(Post post, String likeNumber) {
        return post.getLikes().stream()
                .filter(l -> l.getLikeNumber().equals(likeNumber))
                .findAny().orElseThrow(NotFoundException::new);
    }

    private Post convert(final PostResource resource) {
        return new Post()
                .setId(UUID.randomUUID())
                .setComments(resource.getComments())
                .setDate(resource.getDate())
                .setEdited(resource.isEdited())
                .setLikes(resource.getLikes())
                .setPostNumber(resource.getPostNumber())
                .setText(resource.getText())
                .setUser(resource.getUser());
    }

    private PostResource convert(final Post post) {
        return new PostResource()
                .setComments(post.getComments())
                .setDate(post.getDate())
                .setEdited(post.isEdited())
                .setLikes(post.getLikes())
                .setPostNumber(post.getPostNumber())
                .setText(post.getText())
                .setUser(post.getUser());
    }
}
