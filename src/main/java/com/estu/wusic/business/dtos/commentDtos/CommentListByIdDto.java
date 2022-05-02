package com.estu.wusic.business.dtos.commentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListByIdDto {

    private int commentId;
    private int commentsOwnerId;
    private int commentsRecieverId;
    private String commentText;
}
