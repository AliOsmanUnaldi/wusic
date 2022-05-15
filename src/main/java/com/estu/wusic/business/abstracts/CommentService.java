package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.Comment;

public interface CommentService {
    Result add(CreateCommentRequest createCommentRequest) throws BusinessException;
    DataResult getCommentDtoByCommentId(int id);
    DataResult getCommentsByCommentRecieversId(int id);

    void save(Comment comment);
}
