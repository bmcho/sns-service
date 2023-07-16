package com.service.sns.controller;

import com.service.sns.controller.request.PostCommentRequest;
import com.service.sns.controller.request.PostCreateRequest;
import com.service.sns.controller.request.PostModifyRequest;
import com.service.sns.controller.response.CommentResponse;
import com.service.sns.controller.response.PostResponse;
import com.service.sns.controller.response.Response;
import com.service.sns.model.Post;
import com.service.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable int postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable int postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return Response.success();
    }

    @GetMapping()
    public Response<Page<PostResponse>> list(Pageable pageable, Authentication authentication) {
        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> myList(Pageable pageable, Authentication authentication) {
        return Response.success(postService.my(authentication.getName(), pageable).map(PostResponse::fromPost));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable int postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Long> likeCount(@PathVariable int postId, Authentication authentication) {
        return Response.success(postService.likeCount(postId));
    }

    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable int postId, @RequestBody PostCommentRequest postCommentRequest, Authentication authentication) {
        postService.comment(postId, authentication.getName(), postCommentRequest.getComment());
        return Response.success();
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> comment(@PathVariable int postId, Pageable pageable, Authentication authentication) {
        return Response.success(postService.getComments(postId, pageable).map(CommentResponse::fromComment));
    }

}
