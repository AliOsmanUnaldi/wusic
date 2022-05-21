package com.estu.wusic.business.dtos.commentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListByIdDto {

    private String commentText;

    private String commentOwnerName;
}
