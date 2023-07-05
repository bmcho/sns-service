package com.service.sns.controller.response;

import com.service.sns.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private int id;
    private String comment;
    private String userName;
    private int postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deleteAt;

    public static CommentResponse fromComment(Comment model) {
        return new CommentResponse(
                model.getId(),
                model.getComment(),
                model.getUserName(),
                model.getPostId(),
                model.getRegisteredAt(),
                model.getUpdatedAt(),
                model.getDeleteAt()
        );
    }

}
