package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.CommentService;
import com.estu.wusic.business.dtos.commentDtos.CommentByIdDto;
import com.estu.wusic.business.dtos.commentDtos.CommentListByIdDto;
import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.core.utilities.mapping.ModelMapperService;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.core.utilities.results.SuccessDataResult;
import com.estu.wusic.core.utilities.results.SuccessResult;
import com.estu.wusic.dataAccess.abstracts.CommentDao;
import com.estu.wusic.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentManager implements CommentService {

    private CommentDao commentDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public CommentManager(CommentDao commentDao, ModelMapperService modelMapperService) {
        this.commentDao = commentDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateCommentRequest createCommentRequest) {

        Comment comment = this.modelMapperService.forRequest().map(createCommentRequest,Comment.class);
        comment.setCommentText(createCommentRequest.getCommentText());
        System.out.println(createCommentRequest.getCommentText());

        this.commentDao.save(comment);

        return new SuccessResult("Yorum başarılı bir şekilde eklendi.");
    }

    @Override
    public DataResult getCommentDtoByCommentId(int id) {

        Comment comment = commentDao.getById(id);
        System.out.println(comment.getCommentText());
        CommentByIdDto commentByIdDto = this.modelMapperService.forDto().map(comment,CommentByIdDto.class);
        commentByIdDto.setCommentText(comment.getCommentText());

        return new SuccessDataResult(commentByIdDto,"Yorum başarılı bir şekilde bulundu.");
    }


    @Override
    public DataResult getCommentsByCommentRecieversId(int id) {

        List<Comment> comments = commentDao.getAllByCommentsRecieverId_Id(id);
        List<CommentListByIdDto> result = comments.stream()
                .map(comment -> this.modelMapperService.forDto().map(comment,CommentListByIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult(result,"Kullanıcıya yapılan yorumlar başarılı bir şekilde listelendi.");
    }
}
