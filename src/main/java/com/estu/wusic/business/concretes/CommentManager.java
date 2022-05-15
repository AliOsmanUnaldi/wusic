package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.CommentService;
import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.commentDtos.CommentByIdDto;
import com.estu.wusic.business.dtos.commentDtos.CommentListByIdDto;
import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.core.exceptions.BusinessException;
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
    private UserService userService;
    private RoomService roomService;
    @Autowired
    public CommentManager(CommentDao commentDao, ModelMapperService modelMapperService, UserService userService,
                          RoomService roomService) {
        this.commentDao = commentDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public Result add(CreateCommentRequest createCommentRequest) throws BusinessException {

        if (!this.userService.checkIfUserExists(this.userService.getUserById(createCommentRequest.getCommentsOwnerId()))
        && !this.userService.checkIfUserExists(this.userService.getUserById(createCommentRequest.getCommentsRecieverId()))
        && this.userService.checkIfUserDidLogIn(createCommentRequest.getCommentsOwnerId())
        && this.userService.checkIfUserDidLogIn(createCommentRequest.getCommentsRecieverId())){
             throw new BusinessException("Bir hata oluştu.");
        }

        Comment comment = this.modelMapperService.forRequest().map(createCommentRequest,Comment.class);

        checkIfUserIsInTheRoom(createCommentRequest.getCommentsOwnerId(),comment);
        this.commentDao.save(comment);

        return new SuccessResult("Yorum başarılı bir şekilde eklendi.");
    }

    @Override
    public DataResult getCommentDtoByCommentId(int id) {

        Comment comment = commentDao.getById(id);
        CommentByIdDto commentByIdDto = this.modelMapperService.forDto().map(comment,CommentByIdDto.class);

        return new SuccessDataResult(commentByIdDto,"Yorum başarılı bir şekilde bulundu.");
    }


    @Override
    public DataResult getCommentsByCommentRecieversId(int id) {

        List<Comment> comments = commentDao.getAllByCommentsRecieverId(id);
        List<CommentListByIdDto> result = comments.stream()
                .map(comment -> this.modelMapperService.forDto().map(comment,CommentListByIdDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult(result,"Kullanıcıya yapılan yorumlar başarılı bir şekilde listelendi.");
    }

    @Override
    public void save(Comment comment) {

        this.commentDao.save(comment);
    }

    public boolean checkIfUserIsInTheRoom(int ownerId, Comment comment) throws BusinessException {

        if (this.userService.getUserById(ownerId).getRoomJoined() !=
                this.roomService.getRoomByOwner_OwnerId(comment.getCommentsReciever().getId())){
            throw new BusinessException("Bu odada katılımcı değilsiniz");
        }

        return true;
    }
}
