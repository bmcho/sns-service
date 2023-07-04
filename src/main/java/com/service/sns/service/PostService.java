package com.service.sns.service;

import com.service.sns.exception.ErrorCode;
import com.service.sns.exception.SnsApplicationException;
import com.service.sns.model.Post;
import com.service.sns.model.entity.LikeEntity;
import com.service.sns.model.entity.PostEntity;
import com.service.sns.model.entity.UserEntity;
import com.service.sns.repository.LikeEntityRepository;
import com.service.sns.repository.PostEntityRepository;
import com.service.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("User %s not founded", userName)));
        // post save
        PostEntity savedPostEntity = postEntityRepository.save(PostEntity.of(title, body, userEntity));
        // return
    }

    public Post modify(String title, String body, String userName, int postId) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("User %s not founded", userName)));

        // post 존재여부
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        // post 권한여부
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("% has no perimssion with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, int postId) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("User %s not founded", userName)));

        // post 존재여부
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        // post 권한여부
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("% has no perimssion with %s", userName, postId));
        }

        postEntityRepository.delete(postEntity);

    }

    public Page<Post> list(Pageable pageable) {
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("User %s not founded", userName)));
        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(int postId, String userName) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("User %s not founded", userName)));
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(x -> {
            throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("%s already like post: %s", userName, postId));
        });

        likeEntityRepository.save(LikeEntity.of(userEntity,postEntity));
    }

    public int likeCount(int postId) {
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
        return likeEntityRepository.countByPost(postEntity);
    }

}
