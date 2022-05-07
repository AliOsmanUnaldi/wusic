package com.estu.wusic.business.requests.commentRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    @NotNull
    private int commentsOwnerId;

    @NotNull
    private int commentsRecieverId;

    @NotNull
    @NotBlank
    private String commentText;
}
