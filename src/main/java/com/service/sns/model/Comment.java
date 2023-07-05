package com.service.sns.model;

import com.service.sns.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Comment {

    private int id;
    private String comment;
    private String userName;
    private int postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deleteAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeleteAt()
        );
    }


}
