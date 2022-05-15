package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.dtos.userDtos.UserByIdDto;
import com.estu.wusic.business.dtos.userDtos.UserListDto;
import com.estu.wusic.business.requests.leaveRoomRequest.LeaveRoomRequest;
import com.estu.wusic.business.requests.userRequests.CreateUserRequest;
import com.estu.wusic.business.requests.userRequests.UpdateUserRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.User;

import java.util.List;

public interface UserService {

    DataResult<List<UserListDto>> getAll();

    Result add(CreateUserRequest createUserRequest) throws BusinessException;

    Result update(UpdateUserRequest updateUserRequest);

    DataResult<UserByIdDto> getUserByUserId(int id);

    Result deleteUserByUserId(int id);

    DataResult<List<UserListDto>> getAllUsersPaged(int pageNo, int pageSize);

    DataResult<List<UserListDto>> getAllUsersSorted(String ascOrDesc);

    DataResult<Integer> login (String userName, String password) throws BusinessException;

    User getUserEntityByUserId(int id);

    Result joinIntoRoom(int userId, int roomId) throws BusinessException;

    Result leaveFromRoom(LeaveRoomRequest leaveRoomRequest) throws BusinessException;

    Result quit(int id);

    boolean checkIfUserExists(User user);

    boolean checkIfUserDidLogIn(int userId) throws BusinessException;

    User getUserById(int id);

}
