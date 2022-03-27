package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.userDtos.UserByIdDto;
import com.estu.wusic.business.dtos.userDtos.UserListDto;
import com.estu.wusic.business.requests.userRequests.CreateUserRequest;
import com.estu.wusic.business.requests.userRequests.UpdateUserRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.mapping.ModelMapperService;
import com.estu.wusic.core.utilities.results.*;
import com.estu.wusic.dataAccess.abstracts.UserDao;
import com.estu.wusic.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private UserDao userDao;
    private ModelMapperService modelMapperService;
    private RoomService roomService;

    public UserManager(UserDao userDao, ModelMapperService modelMapperService,@Lazy RoomService roomService) {

        this.userDao = userDao;
        this.modelMapperService = modelMapperService;
        this.roomService = roomService;
    }

    @Override
    public DataResult<List<UserListDto>> getAll() {

        List<User> result = userDao.findAll();
        List<UserListDto> response = result.stream()
                .map(user -> this.modelMapperService.forDto().map(user,UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response,"Kullanıcılar başarılı bir şekilde listelendi.");
    }

    @Override
    public Result add(CreateUserRequest createUserRequest) {

        User user = this.modelMapperService.forRequest().map(createUserRequest,User.class);

        this.userDao.save(user);

        return new SuccessResult("Kullanıcı başarılı bir şekilde kaydoldu.");
    }

    @Override
    public Result update(UpdateUserRequest updateUserRequest) {

        User user = this.modelMapperService.forRequest().map(updateUserRequest,User.class);

        this.userDao.save(user);

        return new SuccessResult("Kullanıcı bilgileri başarıyla güncellendi.");
    }

    @Override
    public DataResult<UserByIdDto> getUserByUserId(int id) {

        User user = this.userDao.getById(id);
        UserByIdDto response = this.modelMapperService.forDto().map(user,UserByIdDto.class);

        return new SuccessDataResult<UserByIdDto>(response,"Kullanıcı başarılı bir şekilde bulundu.");
    }

    @Override
    public Result deleteUserByUserId(int id) {

        this.userDao.deleteById(id);

        return new SuccessResult("Kullanıcı başarıyla silindi.");
    }

    @Override
    public DataResult<List<UserListDto>> getAllUsersPaged(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        List<User> result = userDao.findAll(pageable).getContent();
        List<UserListDto> response = result.stream()
                .map(user -> this.modelMapperService.forDto().map(user,UserListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response,"Kullanıcılar başarılı bir şekilde sayfalandı.");
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

        return new SuccessDataResult<List<UserListDto>>(response,"Kullanıcılar başarılı bir şekilde sıralandı.");
    }

    @Override
    public DataResult<User> login(String userName, String password) throws BusinessException {

        User user = this.userDao.getUserByUserName(userName);

        if (!(checkIfUserExists(user) && checkIfPasswordIsCorrect(user,password))){

            throw new BusinessException("Kullanıcı adı veya şifre yanlış!");
        }

        return new SuccessDataResult<User>(user,"Kullanıcı başarıyla giriş yaptı.");
    }

    @Override
    public User getUserEntityByUserId(int id) {

        return this.userDao.getById(id);
    }

    private boolean checkIfUserExists(User user){

        return this.userDao.existsById(user.getId());
    }

    private boolean checkIfPasswordIsCorrect(User user, String password){

        if (user.getPassword().equals(password)){

          return true;
        }
        return false;
    }

}
