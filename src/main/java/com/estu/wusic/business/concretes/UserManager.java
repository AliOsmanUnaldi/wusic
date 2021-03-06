package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.CommentService;
import com.estu.wusic.business.abstracts.PointService;
import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.userDtos.LoginResponseDto;
import com.estu.wusic.business.dtos.userDtos.UserByIdDto;
import com.estu.wusic.business.dtos.userDtos.UserListDto;
import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.business.requests.leaveRoomRequest.LeaveRoomRequest;
import com.estu.wusic.business.requests.pointRequests.CreatePointRequest;
import com.estu.wusic.business.requests.userRequests.CreateUserRequest;
import com.estu.wusic.business.requests.userRequests.UpdateUserRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.mapping.ModelMapperService;
import com.estu.wusic.core.utilities.results.*;
import com.estu.wusic.dataAccess.abstracts.UserDao;
import com.estu.wusic.entities.Comment;
import com.estu.wusic.entities.Point;
import com.estu.wusic.entities.Room;
import com.estu.wusic.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {
    private UserDao userDao;
    private ModelMapperService modelMapperService;
    private RoomService roomService;
    private PointService pointService;
    private CommentService commentService;
    public UserManager(UserDao userDao, ModelMapperService modelMapperService,@Lazy RoomService roomService,
                       @Lazy CommentService commentService,@Lazy PointService pointService) {

        this.userDao = userDao;
        this.modelMapperService = modelMapperService;
        this.roomService = roomService;
        this.commentService = commentService;
        this.pointService = pointService;
    }
    @Override
    public DataResult<List<UserListDto>> getAll() {

        List<User> result = userDao.findAll();
        List<UserListDto> response = result.stream()
                .map(user -> this.modelMapperService.forDto().map(user,UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response,"Kullan??c??lar ba??ar??l?? bir ??ekilde listelendi.");
    }
    @Override
    public Result add(CreateUserRequest createUserRequest) throws BusinessException {

        checkIfUserNameIsUnique(createUserRequest.getUserName());
        checkIfPasswordLengthIsNotLessThanFiveChars(createUserRequest.getPassword());

        User user = this.modelMapperService.forRequest().map(createUserRequest,User.class);

        this.userDao.save(user);

        return new SuccessResult("Kullan??c?? ba??ar??l?? bir ??ekilde kaydoldu.");
    }
    @Override
    public Result update(UpdateUserRequest updateUserRequest) {

        User user = this.modelMapperService.forRequest().map(updateUserRequest,User.class);

        this.userDao.save(user);

        return new SuccessResult("Kullan??c?? bilgileri ba??ar??yla g??ncellendi.");
    }
    @Override
    public DataResult<UserByIdDto> getUserByUserId(int id) {

        User user = this.userDao.getById(id);
        UserByIdDto response = this.modelMapperService.forDto().map(user,UserByIdDto.class);

        return new SuccessDataResult<UserByIdDto>(response,"Kullan??c?? ba??ar??l?? bir ??ekilde bulundu.");
    }
    @Override
    public Result deleteUserByUserId(int id) {

        this.userDao.deleteById(id);

        return new SuccessResult("Kullan??c?? ba??ar??yla silindi.");
    }
    @Override
    public DataResult<List<UserListDto>> getAllUsersPaged(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        List<User> result = userDao.findAll(pageable).getContent();
        List<UserListDto> response = result.stream()
                .map(user -> this.modelMapperService.forDto().map(user,UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response,"Kullan??c??lar ba??ar??l?? bir ??ekilde sayfaland??.");
    }
    @Override
    public DataResult<List<UserListDto>> getAllUsersSorted(String ascOrDesc) {

        Sort sort;
        String value = ( ascOrDesc.equals("ASC") || ascOrDesc.equals("DESC")) ? ascOrDesc : "DESC";
        sort = Sort.by(Sort.Direction.valueOf(value));

        List<User> result = userDao.findAll(sort);

        List<UserListDto> response = result.stream()
                .map(user -> this.modelMapperService.forDto().map(user,UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response,"Kullan??c??lar ba??ar??l?? bir ??ekilde s??raland??.");
    }
    @Override
    public DataResult<LoginResponseDto> login(String userName, String password) throws BusinessException {

        checkIfUserIsNotNull(userName);

        User user = this.userDao.getUserByUserName(userName);

        if (!checkIfPasswordIsCorrect(user,password)){

            throw new BusinessException("Hata! ??ifre yanl????!");
        }

        user.setLoogedIn(true);

        int roomId = -1;

        if ( user.getRoomCreated().size() >=1 ){
            roomId = this.roomService.getRoomsByOwner_Id(user.getId()).getData();
        }

        this.userDao.save(user);

        LoginResponseDto loginResponseDto = new LoginResponseDto(user.getId(),roomId);

        return new SuccessDataResult<LoginResponseDto>(loginResponseDto,"Kullan??c?? ba??ar??yla giri?? yapt??.");
    }
    @Override
    public User getUserEntityByUserId(int id) {

        return this.userDao.findById(id).get();
    }
    @Override
    public Result joinIntoRoom(int userId, int roomId) throws BusinessException {

        checkIfUserDidLogIn(userId);
        if (!this.roomService.checkIfRoomExists(roomId)){
            throw new BusinessException("Oda mevcut de??il.");
        }

        Room room = this.roomService.getRoomByRoomId(roomId);
        User user = this.userDao.getById(userId);

        user.setRoomJoined(room);
        List<User> participants = room.getParticipants();
        participants.add(user);

        this.userDao.save(user);
        this.roomService.save(room);

        return new SuccessResult("Kullan??c??  odaya ba??ar??l?? bir ??ekilde giri?? yapt??.");
    }
    @Override
    @Transactional
    public Result leaveFromRoom(LeaveRoomRequest leaveRoomRequest) throws BusinessException {

        User user = this.userDao.getById(leaveRoomRequest.getCreateCommentRequest().getCommentsOwnerId());
        user.setRoomJoined(null);
        this.userDao.save(user);

        CreatePointRequest createPointRequest = leaveRoomRequest.getCreatePointRequest();
        Point point = this.modelMapperService.forRequest().map(createPointRequest,Point.class);
        this.pointService.save(point);

        CreateCommentRequest createCommentRequest = leaveRoomRequest.getCreateCommentRequest();
        Comment comment = this.modelMapperService.forRequest().map(createCommentRequest,Comment.class);
        this.commentService.save(comment);

        int roomId = this.roomService.getRoomsByOwner_Id(createCommentRequest.getCommentsRecieverId()).getData();
        Room room = this.roomService.getRoomByRoomId(roomId);
        room.setAveragePoint(this.pointService.getAvaragePointOfHost(createPointRequest.getPointsRecieverId()).getData());
        this.roomService.save(room);

        if (user.getRoomJoined() != null){
            throw new BusinessException("Odadan ????k??lamad??!");
        }

        return new SuccessResult("Odadan ba??ar??yla ????k??ld??.");
    }
    @Override
    public Result quit(int id) {

        User user = this.userDao.getById(id);
        user.setLoogedIn(false);
        this.userDao.save(user);

        return new SuccessResult("Kullan??c?? ba??ar??yla ????k???? yapt??.");
    }
    @Override
    public boolean checkIfUserExists(User user){

        return this.userDao.existsById(user.getId());
    }
    private boolean checkIfPasswordIsCorrect(User user, String password){

        if (user.getPassword().equals(password)){

          return true;
        }
        return false;
    }
    @Override
    public boolean checkIfUserDidLogIn(int userId) throws BusinessException {

        User user = this.userDao.getById(userId);

        if (!user.isLoogedIn()){

            throw new BusinessException("Bu i??lemi yapmak i??in ??nce uygulamaya giri?? yap??n!");
        }

        return true;
    }
    @Override
    public User getUserById(int id) {

        return this.userDao.getById(id);
    }
    private boolean checkIfPasswordLengthIsNotLessThanFiveChars(String password) throws BusinessException {

        if (password.length()<5){
            throw new BusinessException("??ifre 5 karakterden az olamaz!");
        }

        return true;
    }
    private boolean checkIfUserNameIsUnique(String userName) throws BusinessException {

        if (this.userDao.getUserByUserName(userName) != null){
            throw new BusinessException("Kullan??c?? ad?? daha ??nce al??nm????! L??tfen ba??ka bir kullan??c?? ad?? se??in.");
        }
      return true;
    }
    private boolean checkIfUserIsNotNull(String userName) throws BusinessException {

        if (this.userDao.getUserByUserName(userName) == null){
            throw new BusinessException("Kullan??c?? bulunamad??!");
        }

        return true;
    }
}
