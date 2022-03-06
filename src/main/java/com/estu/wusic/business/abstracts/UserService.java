package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.dtos.UserByIdDto;
import com.estu.wusic.business.dtos.UserListDto;
import com.estu.wusic.business.requests.CreateUserRequest;
import com.estu.wusic.business.requests.UpdateUserRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;

import java.util.List;

public interface UserService {
    DataResult<List<UserListDto>> getAll();

    Result add(CreateUserRequest createUserRequest);

    Result update(UpdateUserRequest updateUserRequest);

    DataResult<UserByIdDto> getUserByUserId(int id);

    Result deleteUserByUserId(int id);

    DataResult<List<UserListDto>> getAllUsersPaged(int pageNo, int pageSize);

    DataResult<List<UserListDto>> getAllUsersSorted(String ascOrDesc);

}
