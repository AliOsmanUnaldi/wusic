package com.estu.wusic.business.requests.commentRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    private int commentsOwnerId;
    private int commentsRecieverId;
    private String commentText;
}
