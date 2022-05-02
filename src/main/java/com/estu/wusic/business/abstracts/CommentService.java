package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.dtos.commentDtos.CommentByIdDto;
import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;

public interface CommentService {
    Result add(CreateCommentRequest createCommentRequest) throws BusinessException;
    DataResult getCommentDtoByCommentId(int id);
    DataResult getCommentsByCommentRecieversId(int id);

}
